package game.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPool {
    private static final List<WorkerThread> workers = new ArrayList<>();
    private static final List<ThreadControlBlock> threadsControl = new ArrayList<>();
    private static final List<TaskQueue> tasksQueues = new ArrayList<>();
    private static final ReentrantLock queueLock = new ReentrantLock();
    private static final Condition condition = queueLock.newCondition();
    private static final ReentrantLock syncLock = new ReentrantLock();
    private static final Condition syncCondition = syncLock.newCondition();
    private static final AtomicInteger activeTasks = new AtomicInteger(0);
    private static final AtomicBoolean stop = new AtomicBoolean(false);

    public static void initialize(int numThreads) {
        stop.set(false);
        activeTasks.set(0);
        for (int i = 0; i < numThreads; i++) {
            threadsControl.add(new ThreadControlBlock());
            tasksQueues.add(new TaskQueue());
            WorkerThread worker = new WorkerThread(threadsControl.get(i), i);
            workers.add(worker);
            worker.start();
        }
    }

    private static TaskQueue findLeastLoadedQueue() {
        TaskQueue minQueue = null;
        int minSize = Integer.MAX_VALUE;

        queueLock.lock();
        try {
            for (TaskQueue queue : tasksQueues) {
                int currentSize = queue.queue.size();
                if (currentSize < minSize) {
                    minSize = currentSize;
                    minQueue = queue;
                }
            }
        } finally {
            queueLock.unlock();
        }

        return minQueue;
    }

    public static <T> Future<T> enqueue(TaskPriority priority, boolean synchronize, Callable<T> task) {
        if (stop.get()) {
            throw new IllegalStateException("ThreadPool is stopped, cannot enqueue tasks.");
        }

        Callable<T> taskWrapper = () -> {
            if (synchronize) {
                syncThisThread(true);
            }
            return task.call();
        };
        FutureTask<T> futureTask = new FutureTask<>(taskWrapper);
        PriorityTask priorityTask = new PriorityTask(futureTask, priority);

        queueLock.lock();
        try {
            TaskQueue minQueue = findLeastLoadedQueue();
            minQueue.queue.offer(priorityTask); // Inserting the PriorityTask into the queue
            activeTasks.incrementAndGet();
            condition.signal();
        } finally {
            queueLock.unlock();
        }

        return futureTask;
    }

    public static void syncThisThread(boolean registerForSync) {
        Thread currentThread = Thread.currentThread();
        threadsControl.forEach(tcb -> {
            if (tcb.threadId == currentThread) {
                tcb.isRegisteredForSync.set(registerForSync);
            }
        });
    }

    public static void synchronizeRegisteredThreads() {
        syncLock.lock();
        try {
            syncCondition.await();
            threadsControl.forEach(tcb -> tcb.hasReachedSyncPoint.set(false));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            syncLock.unlock();
        }
    }

    public static void shutdown() {
        queueLock.lock();
        try {
            stop.set(true);
            condition.signalAll();
        } finally {
            queueLock.unlock();
        }
    }

    private static class WorkerThread extends Thread {
        private final ThreadControlBlock tcb;
        private final int index;

        WorkerThread(ThreadControlBlock tcb, int index) {
            this.tcb = tcb;
            this.index = index;
        }

        @Override
        public void run() {
            tcb.threadId = Thread.currentThread();
            while (!stop.get() || !tasksQueues.get(index).queue.isEmpty()) {
                Runnable task = null;
                queueLock.lock();
                try {
                    while (!stop.get() && tasksQueues.get(index).queue.isEmpty()) {
                        try {
                            condition.await(); // Wait only if the queue is empty and the pool is not stopped
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    if (!tasksQueues.get(index).queue.isEmpty()) {
                        task = tasksQueues.get(index).queue.poll();
                        activeTasks.decrementAndGet();
                    }
                } finally {
                    queueLock.unlock();
                }
                if (task != null) {
                    task.run();
                }
            }
        }
    }


    private static class TaskQueue {
        final PriorityBlockingQueue<PriorityTask> queue = new PriorityBlockingQueue<>();
    }

    private static class TaskWrapper<V> implements Callable<V>, Comparable<TaskWrapper<V>> {
        final TaskPriority priority;
        final Callable<V> task;
        final boolean synchronize;

        TaskWrapper(TaskPriority priority, Callable<V> task, boolean synchronize) {
            this.priority = priority;
            this.task = task;
            this.synchronize = synchronize;
        }

        @Override
        public V call() throws Exception {
            V result = task.call();
            if (synchronize) {
                syncThisThread(true);
            }
            return result;
        }

        // Implementing Comparable to ensure tasks are prioritized correctly in the PriorityBlockingQueue
        @Override
        public int compareTo(TaskWrapper<V> other) {
            // Higher priorities should come first, so we compare in reverse order
            return other.priority.compareTo(this.priority);
        }
    }

    private static class ThreadControlBlock {
        volatile Thread threadId;
        final AtomicBoolean isRegisteredForSync = new AtomicBoolean(false);
        final AtomicBoolean hasReachedSyncPoint = new AtomicBoolean(false);
    }

    public enum TaskPriority {
        CRITICAL, VERY_HIGH, HIGH, NORMAL, LOW, VERY_LOW, BACKGROUND
    }

    private static class PriorityTask implements Runnable, Comparable<PriorityTask> {
        private final FutureTask<?> task;
        private final TaskPriority priority;

        public PriorityTask(FutureTask<?> task, TaskPriority priority) {
            this.task = task;
            this.priority = priority;
        }

        @Override
        public void run() {
            task.run();
        }

        @Override
        public int compareTo(PriorityTask other) {
            return other.priority.compareTo(this.priority); // Reverse order to prioritize higher enums first
        }
    }
}