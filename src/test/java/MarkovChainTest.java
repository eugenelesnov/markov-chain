import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;


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

    @Test
    void shouldThrowNullPointerException() {
        // given
        String[] text = null;
        int wordsPerState = 4;

        // then
        assertThrows(NullPointerException.class, () -> new MarkovChain(text, wordsPerState));
    }

    @Test
    void shouldThrowIllegalArgumentException() {
        // given
        String[] text = "test test test".split(" ");
        int wordsPerState = -4;

        // then
        assertThrows(IllegalArgumentException.class, () -> new MarkovChain(text, wordsPerState));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenWordNumberMoreThanText() {
        // given
        String[] text = "test test test".split(" ");
        int wordsPerState = 100;

        // then
        assertThrows(IllegalArgumentException.class, () -> new MarkovChain(text, wordsPerState));
    }

    @Test
    void shouldThrowNullPointerExceptionWhenRandomIsNull() {
        // given
        String[] text = "test test test".split(" ");
        int wordsPerState = 2;
        Random random = null;

        // then
        assertThrows(NullPointerException.class, () -> new MarkovChain(text, wordsPerState, random));
    }
}
