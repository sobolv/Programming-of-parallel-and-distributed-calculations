package org.sobolv.concurrent;

import java.util.function.Function;

public class Task<Targ, TResult>{
    private Function<Integer, Integer> function;
    private Targ arg;

    Future<TResult> future;

    public Task(Function<Integer, Integer> function, Targ arg) {
        this.function = function;
        this.arg = arg;
        this.future = new Future<>();
    }

    public Function<Integer, Integer> getFunction() {
        return function;
    }

    public Targ getArg() {
        return arg;
    }

    public Future<TResult> getFuture() {
        return future;
    }

    public void setFuture(Future<TResult> future) {
        this.future = future;
    }
}
