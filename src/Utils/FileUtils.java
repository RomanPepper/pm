package Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {
    public static List<String> readAllLinesFromFile(String path) throws FileNotFoundException {
        FileReader fileReader = new FileReader(path);
        Scanner scanner = new Scanner(fileReader);

        List<String> linesList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            linesList.add(scanner.nextLine());
        }

        return linesList;
    }
}
