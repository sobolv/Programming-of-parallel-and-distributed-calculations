package org.sobolv.concurrent;

import java.util.concurrent.CountDownLatch;

public class Future<T> {
    private final CountDownLatch latch;

    public Future() {
        this.latch = new CountDownLatch(1);
    }

    public T getResult() {
        // Try to acquire the lock to return result
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void setResult(T result) {
        this.result = result;
        latch.countDown();
    }

    private T result;
}
