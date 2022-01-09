package daily.programmer.challenges.challenge399;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class Output {
    public static void print(List<Word> words) {
        int maxSum = Math.max(
                "SUM".length(),
                (""
                        + words
                        .stream()
                        .max(Comparator.comparing(Word::getSum))
                        .orElseThrow(NoSuchElementException::new)
                        .getSum()
                ).length()
        );

        int maxLen = Math.max(
                "LEN".length(),
                (""
                        + words
                        .stream()
                        .max(Comparator.comparing(Word::getLength))
                        .orElseThrow(NoSuchElementException::new)
                        .getLength()
                ).length()
        );

        //print header
        System.out.println(String.format("%1$" + maxLen + "s", "LEN")
                + " "
                + String.format("%1$" + maxSum + "s", "SUM")
                + " "
                + "WORD");

        //print words
        words.forEach(w ->
                System.out.println(
                        String.format("%1$" + maxLen + "d", w.getLength())
                        + " "
                        + String.format("%1$" + maxSum + "d", w.getSum())
                        + " "
                        + w.getWord()
                )
        );
    }
}
