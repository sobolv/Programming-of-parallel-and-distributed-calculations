package org.sobolv.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

public class CustomExecutor {
    private int maxThreads;
    private final Queue<Task<Integer, Integer>> queue;
    private WorkerThread[] threads;

    public CustomExecutor(int maxThreads) {
        this.maxThreads = maxThreads;
        queue = new LinkedBlockingQueue<>();
        threads = new WorkerThread[maxThreads];
        // Init workers in the constructor
        for (int i = 0; i < maxThreads; i++) {
            threads[i] = new WorkerThread(queue);
            threads[i].start();
        }
    }

    /**
     * Executes a function asynchronously
     *
     * @return future-like object
     */
    public Future<Integer> execute(Function<Integer, Integer> function, int arg) {
        // Create task and put it into queue
        Task<Integer, Integer> task = new Task<>(function, arg);
        queue.add(task);
        return task.getFuture();
    }

    /**
     * Creates tasks for every parameter passed
     *
     * @return List of future-like objects, that will hold result
     */
    public List<Future<Integer>> map(Function<Integer, Integer> function, int... args) {
        List<Future<Integer>> futures = new LinkedList<>();
        for (int arg : args) {
            futures.add(execute(function, arg));
        }
        return futures;
    }

    /**
     * Waits for all tasks completion and then stops worker threads
     */
    public void shutdown() {
        for (WorkerThread thread : threads) {
            thread.stop();
        }
    }
}
