package org.sobolv.concurrent;

import java.util.Queue;

public class WorkerThread extends Thread {
    private final Queue<Task<Integer, Integer>> queue;

    public WorkerThread(Queue<Task<Integer, Integer>> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000); // Simulate long running task
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //Take the task for execution
            Task<Integer, Integer> task = queue.poll();
            if(task == null){
                return;
            }
            Future<Integer> future = task.getFuture();
            Integer result = task.getFunction().apply(task.getArg());
            //Release the lock
            future.setResult(result);

        }
    }
}
