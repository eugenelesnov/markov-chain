/**
 * @author Eugene Lesnov
 */

public class Main {

    private static final int WORDS_PER_STATE = 3;

    public static void main(String[] args) {
        String pathToFile = "";
        int sentenceLength = 0;

        try {
            pathToFile = args[0];
            sentenceLength = Integer.parseInt(args[1]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (!pathToFile.isEmpty() && sentenceLength > 0) {
            Text text = new Text();
            text.initText(pathToFile);

            String[] normalizedText = text.getWords();

            MarkovChain markovChain = new MarkovChain(normalizedText, WORDS_PER_STATE);
            String result = markovChain.compose(sentenceLength);
            text.writeToFile(result);
        } else {
            throw new IllegalArgumentException("Wrong arguments: pathToFile = " + pathToFile
                    + " sentenceLength: " + sentenceLength);
        }
    }
}
