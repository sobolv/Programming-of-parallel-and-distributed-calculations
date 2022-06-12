package Task1;

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

public class Task1 {
    private static Map<String, Integer> result = new TreeMap<>();
    public static void main(String[] args) throws IOException {
        long time = currentTimeMillis();
        List<File> files = Files.walk(Paths.get("D:\\КПИ\\3 курс\\Розподілені обчислення\\docs-1000-10000"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        BiFunction<Integer, Integer, Integer> bFunc = Integer::sum;
        for (File f:files) {
            Map<String, Integer> res = countWords(f);
            for (Map.Entry<String, Integer> entry : res.entrySet()) {
                result.merge(entry.getKey(), entry.getValue(), bFunc);
            }
        }
        System.out.println(result.toString());
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
}
