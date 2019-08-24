import lombok.Data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author Eugene Lesnov
 */

@Data
public class Text {

    private String[] words;

    public void initText(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        String[] normalizedText;
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        normalizedText = contentBuilder.toString()
                .replaceAll("\n", " ")
                .toLowerCase()
                .split(" ");
        this.setWords(normalizedText);
    }

    public void writeToFile(String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("markov_chain_result"));
            writer.write(text);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
