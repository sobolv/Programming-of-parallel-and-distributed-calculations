package Task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toMap;

public class Task2 {
    private static Map<String, Integer> result;
    public static void main(String[] args) throws IOException {
        long time = currentTimeMillis();
        List<File> files = Files.walk(Paths.get("D:\\КПИ\\3 курс\\Розподілені обчислення\\docs-1000-10000"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        result = files.stream().map(Task2::countWords).reduce(Task2::mergeMap).orElse(new TreeMap<>());
        System.out.println(result);
        System.out.println("Time spent: " + (currentTimeMillis()-time) + "ms");

    }
    public static Map<String, Integer> countWords(File file){
        Path path = Paths.get(String.valueOf(file));
        Map<String, Integer> countMap = null;

        try (Stream<String> lineStream = Files.lines(path)) {
            countMap = lineStream.collect(toMap(
                    s -> s,
                    s -> 1,
                    Integer::sum));
        } catch (IOException ignored) {
        }
        return countMap;
    }

    public static Map<String, Integer> mergeMap(Map<String, Integer> map1, Map<String, Integer> map2){
        BiFunction<Integer, Integer, Integer> bFunc = Integer::sum;
        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
            map1.merge(entry.getKey(), entry.getValue(), bFunc);
        }
        return map1;
    }
}
