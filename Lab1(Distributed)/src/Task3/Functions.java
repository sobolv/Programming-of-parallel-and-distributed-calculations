package Task3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Functions {
    public Map<String, Integer> countWords(File file){
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

    public Map<String, Integer> mergeMap(Map<String, Integer> map1, Map<String, Integer> map2){
        BiFunction<Integer, Integer, Integer> bFunc = Integer::sum;
        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
            map1.merge(entry.getKey(), entry.getValue(), bFunc);
        }
        return map1;
    }
}
