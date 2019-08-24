/**
 * @author Eugene Lesnov
 */

public class Main {

    public static void main(String[] args) {
        String pathToFile = "";
        int sentenceLength = 0;
        int wordsPerState = 0;

        try {
            pathToFile = args[0];
            sentenceLength = Integer.parseInt(args[1]);
            wordsPerState = Integer.parseInt(args[2]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (!pathToFile.isEmpty() && sentenceLength > 0 && wordsPerState > 0) {
            Text text = new Text();
            text.initText(pathToFile);

            String[] normalizedText = text.getWords();

            MarkovChain markovChain = new MarkovChain(normalizedText, wordsPerState);
            String result = markovChain.compose(sentenceLength);
            text.writeToFile(result);
        } else {
            throw new IllegalArgumentException("Wrong arguments: pathToFile = " + pathToFile
                    + ", sentenceLength: " + sentenceLength + ", wordsPerState = " + wordsPerState);
        }
    }
}
