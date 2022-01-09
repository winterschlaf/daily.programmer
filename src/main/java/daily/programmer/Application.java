package daily.programmer;

import daily.programmer.challenges.challenge399.Challenge;
import daily.programmer.challenges.challenge399.Output;
import daily.programmer.challenges.challenge399.Word;
import daily.programmer.utility.StackTrace;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args)
    {
        try {
            challenge399(args);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(StackTrace.asString(e));
        }
    }

    private static void challenge399(String[] args) throws Exception {
        Challenge challenge = new Challenge();
        System.out.println("words from input");
        List<Word> words = Arrays
                .stream(args)
                .map(challenge::getWord)
                .collect(Collectors.toList());
        Output.print(words);

        // extra challenges
        words = challenge.getWordList("https://raw.githubusercontent.com/dolph/dictionary/master/enable1.txt");

        // letter sum 319
        int letterSum = 319;
        List<Word> a = challenge.getWordsBySpecificLetterSum(words, letterSum);
        System.out.println();
        System.out.println("1) there is/are " + a.size() + " word(s) with a letter sum of " + a.get(0).getLength());
        Output.print(a);

        // odd letter sum
        List<Word> b = challenge.getWordsByOddLetterSum(words);
        System.out.println();
        System.out.println("2) there are " + b.size() + " words with an odd letter sum");
        //Output.print(b);

        List<Word> c = challenge.getMostCommonLetterSum(words);
        System.out.println();
        System.out.println("3) most common letter sum is "
                + c.get(0).getSum()
                + ", there are " + c.size() + " word(s) with this letter sum"
        );
        //Output.print(c);

        // same letter sum but only words with a certain letter distance between them
        int diff = 11;
        Map<Integer, List<Word>> d = challenge.getWordsWithSameLetterSumAndCertainDistance(words, diff);
        System.out.println();
        System.out.println("4) there is/are "
                + d.keySet().size()
                + " list(s) with words of the same letter sum and a distance of "
                + diff
                + " letters between them");
        d.forEach((k,v) -> {
            System.out.println();
            System.out.println("letter sum of " + k);
            Output.print(v);
        });

        // same letter sum but words have no letter in common
        int letterSumLargerThan = 188;
        Map<Integer, List<Word>> e = challenge.getWordsWithSameLetterSumButDisjointCharacters(words, letterSumLargerThan);
        System.out.println();
        System.out.println("5) there is/are "
                + e.keySet().size()
                + " list(s) with words of the same letter sum which share no characters between them");
        e.forEach((k,v) -> {
            System.out.println();
            System.out.println("letter sum of " + k);
            Output.print(v);
        });

        // longest list of words with unique letter sums and word length
        List<Word> f = challenge.getLongestListOfUniqueLetterSumsAndLength(words);
        System.out.println();
        System.out.println("6) the longest list of unique letter sums and word lengths contains " + f.size() + " word(s)");
        Output.print(f);
    }
}
