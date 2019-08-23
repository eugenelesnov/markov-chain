/**
 * @author Eugene Lesnov
 */

public class Main {

    private static final int WORDS_PER_STATE = 3;

    public static void main(String[] args) {
        String pathToFile = args[0];
        int sentenceLength = Integer.parseInt(args[1]);


        TextFile textFile = new TextFile();
        textFile.initText(pathToFile);

        String[] normalizedText = textFile.getWords();

        MarkovChain markovChain = new MarkovChain(normalizedText, WORDS_PER_STATE);
        String result = markovChain.compose(sentenceLength);
        textFile.writeToFile(result);
    }
}
