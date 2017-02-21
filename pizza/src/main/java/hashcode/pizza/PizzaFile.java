package hashcode.pizza;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author @jozolins
 */
public class PizzaFile {
    final String sourceFile;

    public PizzaFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public PizzaConfiguration loadConfig() {
        try {
            List<String> lines = Files.lines(Paths.get(this.sourceFile))
                    .collect(Collectors.toList());

            String configLine = lines.get(0);
            String[] configValues = configLine.split(" ");

            int rows = Integer.parseInt(configValues[0]);
            int columns = Integer.parseInt(configValues[1]);
            int minIngredients = Integer.parseInt(configValues[2]);
            int maxSlice = Integer.parseInt(configValues[3]);

            String[][] values = lines.subList(1, lines.size()).stream()
                    .map(line -> line.split(""))
                    .toArray(size -> new String[size][columns]);


            return new PizzaConfiguration(values, maxSlice, minIngredients);
        } catch (IOException e) {
            throw new RuntimeException("File " + this.sourceFile + " was not found!");
        }
    }
}
