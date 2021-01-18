package com.it.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ch
 * @date 2021-1-15
 */
@Slf4j(topic = "c.TestPool")
public class TestPool {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(1, 1000, TimeUnit.MILLISECONDS, 1,
         (queue, task) -> {
             // queue.put(task);
            queue.offer(task,500,TimeUnit.MILLISECONDS);
        });
        for (int i = 0; i < 3; i++) {
            int j = i;
            threadPool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.debug("{}", j);
            });
        }
    }

}

@FunctionalInterface
interface RejectPolicy<T>{
    void reject(BlockingQueue<T> queue, T t);
}

@Slf4j(topic = "c.ThreadPool")
class ThreadPool {
    private BlockingQueue<Runnable> taskQueue;
    private int coreSize;
    private long timeout;
    private HashSet<Worker> works = new HashSet();
    private TimeUnit timeUnit;
    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int capacity,RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(capacity);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task) {
        // 当任务数没有超过coreSize时，直接交给worker对象执行
        // 如果任务数超过coreSize时，加入任务队列暂存
        synchronized (works) {
            if (works.size() < coreSize) {
                Worker worker = new Worker(task);
                log.debug("新增 worker {} ,{}", worker, task);
                works.add(worker);
                worker.start();
            } else {
                // taskQueue.put(task);
                // 1）死等
                // 2）带超时等待
                // 3）让调用者放弃任务执行
                // 4）让调用者抛出异常
                // 5）让调用者自己执行任务
                taskQueue.tryPut(rejectPolicy,task);
            }
        }

    }

    class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        public void run() {
            while (task != null || (task = taskQueue.take()) != null) {
                try {
                    log.debug("正在执行。。。{}", task);
                    task.run();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    task = null;
                }
            }

            synchronized (works) {
                log.debug("worker 被移除 {}", this);
                works.remove(this);
            }
        }
    }
}

@Slf4j
class BlockingQueue<T> {
    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition fullWaitSet = lock.newCondition();
    private final Condition emptyWaitSet = lock.newCondition();
    private Deque<T> queue = new ArrayDeque<>();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (queue.isEmpty()) {
                if (nanos <= 0) {
                    return null;
                }
                try {
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signalAll();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signalAll();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public void put(T task) {

        lock.lock();
        try {
            while (queue.size() == capacity) {
                try {
                    log.debug("等待加入任务队列{}...",task);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列 {}",task);
            queue.addLast(task);
            emptyWaitSet.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public boolean offer(T task,long timeout,TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (queue.size() == capacity) {
                try {
                    if (nanos <= 0) {
                        return false;
                    }
                    log.debug("等待加入任务队列{}...",task);
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列 {}",task);
            queue.addLast(task);
            emptyWaitSet.signalAll();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try{
            if (queue.size() >= capacity) {
                rejectPolicy.reject(this, task);
            } else {
                log.debug("加入任务队列 {}",task);
                queue.addLast(task);
                emptyWaitSet.signalAll();
            }
        }finally{
            lock.unlock();
        }
    }
}
