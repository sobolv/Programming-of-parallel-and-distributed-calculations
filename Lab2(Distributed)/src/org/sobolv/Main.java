package org.sobolv;

import org.sobolv.concurrent.CustomExecutor;
import org.sobolv.concurrent.Future;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CustomExecutor customExecutor = new CustomExecutor(2);

        List<Future<Integer>> futures = customExecutor.map(x -> x * x, 1, 2, 3, 4, 5);
        for (Future<Integer> future : futures) {
            System.out.printf("Future result: %d \n", future.getResult());
            System.out.println(java.time.LocalTime.now());
        }

        customExecutor.shutdown();
    }
}