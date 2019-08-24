import java.util.*;

/**
 * @author Eugene Lesnov
 */

public final class MarkovChain {

    private final int wordNumber;
    private final String[] words;

    private final Map<List<String>, Map<List<String>, Integer>> map = new HashMap<>();
    private final Map<List<String>, Integer> totalCountMap = new HashMap<>();
    private final List<List<String>> vocabulary = new ArrayList<>();

    private final Random random;

    public MarkovChain(String[] words, int wordNumber, Random random) {
        this.words = Objects.requireNonNull(words, "Word array is null.");
        this.wordNumber = checkPositive(wordNumber);

        if (words.length < wordNumber) {
            throw new IllegalArgumentException("Number of words < k");
        }

        this.random = Objects.requireNonNull(random, "The random is null.");
        build();
    }

    public MarkovChain(String[] words, int wordNumber) {
        this(words, wordNumber, new Random());
    }

    public String compose(int numberOfWords) {
        checkRequestedNumberOfWords(numberOfWords);
        List<String> startState = vocabulary.get(random.nextInt(vocabulary.size()));

        String[] outputWords = new String[numberOfWords];
        numberOfWords -= wordNumber;

        for (int i = 0; i < startState.size(); ++i) {
            outputWords[i] = startState.get(i);
        }

        int index = wordNumber;

        while (numberOfWords-- > 0) {
            List<String> nextState = randomTransition(startState);
            outputWords[index++] = lastOf(nextState);
            startState = nextState;
        }

        return prepareResultText(outputWords);
    }

    private static <T> T lastOf(List<T> list) {
        return list.get(list.size() - 1);
    }

    private List<String> randomTransition(List<String> startState) {
        Map<List<String>, Integer> localMap = map.get(startState);

        if (localMap == null) {
            return vocabulary.get(random.nextInt(vocabulary.size()));
        }

        int choices = totalCountMap.get(startState);
        int coin = random.nextInt(choices);

        for (Map.Entry<List<String>, Integer> entry : localMap.entrySet()) {
            if (coin < entry.getValue()) {
                return entry.getKey();
            }

            coin -= entry.getValue();
        }

        throw new IllegalStateException("Illegal transition reached");
    }

    private void build() {
        Set<List<String>> filter = new HashSet<>();
        Deque<String> wordDeque = new ArrayDeque<>();

        for (int i = 0; i < wordNumber; ++i) {
            wordDeque.addLast(words[i]);
        }

        for (int i = wordNumber; i < words.length; ++i) {
            List<String> startSentence = new ArrayList<>(wordDeque);
            filter.add(startSentence);

            wordDeque.removeFirst();
            wordDeque.addLast(words[i]);
            List<String> nextSentence = new ArrayList<>(wordDeque);

            Map<List<String>, Integer> localMap = map.computeIfAbsent(startSentence, k -> new HashMap<>());

            localMap.put(nextSentence, localMap.getOrDefault(nextSentence, 0) + 1);

            totalCountMap.put(startSentence, totalCountMap.getOrDefault(startSentence, 0) + 1);
        }

        vocabulary.addAll(filter);
    }

    private int checkPositive(int k) {
        if (k < 1) {
            throw new IllegalArgumentException("k < 1");
        }
        return k;
    }

    private void checkRequestedNumberOfWords(int numberOfWords) {
        if (numberOfWords < wordNumber) {
            throw new IllegalArgumentException(
                    "The minimum number of words for composition should be " + wordNumber
                            + ". Received " + numberOfWords);
        }
    }

    private String capitalizeFirstLettersInSentence(String sentence) {
        int pos = 0;
        boolean capitalize = true;

        StringBuilder sb = new StringBuilder(sentence);
        while (pos < sb.length()) {
            if (sb.charAt(pos) == '.') {
                capitalize = true;
            } else if (capitalize && !Character.isWhitespace(sb.charAt(pos))) {
                sb.setCharAt(pos, Character.toUpperCase(sb.charAt(pos)));
                capitalize = false;
            }
            pos++;
        }

        String result = sb.toString();
        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }

    private String prepareResultText(String[] outputWords) {
        // join words to sentence with whitespace as delimiter
        String result = String.join(" ", outputWords);

        result = capitalizeFirstLettersInSentence(result);

        // and let's make last word really last.
        if (!result.endsWith(".")) {
            result = result.concat(".");
        }
        return result;
    }
}