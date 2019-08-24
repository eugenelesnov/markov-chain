/**
 * @author Eugene Lesnov
 */

public class MarkovChainTest {

    public static void main(String[] args) {
        int sentenceLength = 30;
        int wordsPerState = 1;

        String[] normalizedText = TestUtil.TEXT_EXAMPLE
                .replaceAll("\n", " ")
                .split(" ");

        MarkovChain markovChain = new MarkovChain(normalizedText, wordsPerState);
        String result = markovChain.compose(sentenceLength);
        System.out.println(result);
    }
}
