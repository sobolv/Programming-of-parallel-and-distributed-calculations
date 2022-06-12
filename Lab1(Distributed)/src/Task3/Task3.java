package Task3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toMap;

public class Task3 {
    private static Map<String, Integer> result;
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        long time = currentTimeMillis();
        Functions func = new Functions();
        List<File> files = Files.walk(Paths.get("D:\\КПИ\\3 курс\\Розподілені обчислення\\docs-1000-10000"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        ExecutorService threadPool = Executors.newFixedThreadPool(8);
        List<Future<Map<String, Integer>>> list = new ArrayList<>();
        for (File f : files) {
            list.add(
                    CompletableFuture.supplyAsync(()->func.countWords(f), threadPool)
            );

        }
        List<Map<String, Integer>> futureResult = new ArrayList<>();
        for (Future<Map<String, Integer>> m : list) {
            futureResult.add(m.get());
        }
        result = futureResult.stream().reduce(func::mergeMap).orElse(new TreeMap<>());
        threadPool.shutdown();
        System.out.println(result);
        System.out.println("Time spent: " + (currentTimeMillis()-time) + "ms");
    }
}
