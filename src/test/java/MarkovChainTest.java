public class MarkovChainTest {

    private static final int WORDS_PER_STATE = 4;

    public static void main(String[] args) {
        int sentenceLength = 40;

        String[] normalizedText = TestUtil.TEXT_EXAMPLE
                .replaceAll("\n", " ")
                .split(" ");

        MarkovChain markovChain = new MarkovChain(normalizedText, WORDS_PER_STATE);
        String result = markovChain.compose(sentenceLength);
        System.out.println(result);
    }
}
