package daily.programmer.challenges.challenge399;

public class Word {
    private final String word;
    private final int sum;

    public Word(String word, int sum) {
        this.word = word;
        this.sum = sum;

    }

    public String getWord() {
        return word;
    }

    public int getSum() {
        return sum;
    }

    public int getLength() {
        return word.length();
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", sum=" + sum +
                ", length=" + word.length() +
                '}';
    }
}
