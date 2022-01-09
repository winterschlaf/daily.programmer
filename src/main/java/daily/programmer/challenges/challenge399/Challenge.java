package daily.programmer.challenges.challenge399;

import daily.programmer.utility.StackTrace;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

public class Challenge {
    public List<Word> getWordList(String urlString) throws Exception {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        List<Word> words = new ArrayList<>();

        try (
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(isr)
        ) {
            String line;

            while ((line = br.readLine()) != null) {
                words.add(getWord(line));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(StackTrace.asString(e));
        }

        return words;
    }

    public Word getWord(String str) throws NullPointerException {
        if(str == null) {
            throw new NullPointerException("no empty strings allowed");
        }

        int charOffset = 96;

        return new Word(
                str,
                Arrays
                        .stream(str.split(""))
                        .filter(c -> !c.trim().isEmpty())
                        .map(c -> (int) c.charAt(0) - charOffset)
                        .collect(Collectors.toList())
                        .stream()
                        .reduce(0, Integer::sum)
        );
    }

    public List<Word> getWordsBySpecificLetterSum(List<Word> words, int letterSum) {
        return words
                .stream()
                .filter(w -> w.getSum() == letterSum)
                .collect(Collectors.toList());
    }

    public List<Word> getWordsByOddLetterSum(List<Word> words) {
        return words
                .stream()
                .filter(w -> w.getSum()%2 != 0)
                .collect(Collectors.toList());
    }

    public List<Word> getMostCommonLetterSum(List<Word> words) {
        return words
                .stream()
                .collect(Collectors.groupingBy(Word::getSum))
                .values()
                .stream()
                .max(Comparator.comparing(List::size))
                .get();
    }

    public Map<Integer, List<Word>> getWordsWithSameLetterSumAndCertainDistance(List<Word> words, int diff) {
        Map<Integer, List<Word>> result = new HashMap<>();
        words
                .stream()
                .collect(Collectors.groupingBy(Word::getSum))
                .forEach((k,v) -> {
                    List<Word> tmp = new ArrayList<>();

                    for(int i=0;i<v.size()-1;i++) {
                        for(int j=i+1;j<v.size();j++) {
                            if(Math.abs(v.get(i).getLength() - v.get(j).getLength()) == diff) {
                                tmp.add(v.get(i));
                                tmp.add(v.get(j));
                            }
                        }
                    }

                    if(!tmp.isEmpty()) {
                        result.put(k, tmp);
                    }
                });

        return result;
    }

    public Map<Integer, List<Word>> getWordsWithSameLetterSumButDisjointCharacters(List<Word> words, int letterSumLargerThan) {
        Map<Integer, List<Word>> result = new HashMap<>();
        words
                .stream()
                .filter(w -> w.getSum() > letterSumLargerThan)
                .collect(Collectors.groupingBy(Word::getSum))
                .forEach((k,v) -> {
                    List<Word> tmp = new ArrayList<>();

                    for(int i=0;i<v.size()-1;i++) {
                        for(int j=i+1;j<v.size();j++) {
                            if(Collections.disjoint(
                                    Arrays.asList(v.get(i).getWord().split("")),
                                    Arrays.asList(v.get(j).getWord().split("")))
                            ) {
                                tmp.add(v.get(i));
                                tmp.add(v.get(j));
                            }
                        }
                    }

                    if(!tmp.isEmpty()) {
                        result.put(k, tmp);
                    }
                });

        return result;
    }

    public List<Word> getLongestListOfUniqueLetterSumsAndLength(List<Word> words) {
        List<Word> result = new ArrayList<>();

        Map<Integer, Map<Integer, List<Word>>> groupBySumLength = new HashMap<>();
        words.forEach(w -> {
            if(!groupBySumLength.containsKey(w.getSum())) {
                groupBySumLength.put(w.getSum(), new HashMap<>());
            }

            if(!groupBySumLength.get(w.getSum()).containsKey(w.getLength())) {
                groupBySumLength.get(w.getSum()).put(w.getLength(), new ArrayList<>());
            }

            groupBySumLength.get(w.getSum()).get(w.getLength()).add(w);
        });

        next(groupBySumLength, result, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());

        Comparator<Word> comp = Comparator
                .comparing(Word::getLength, Comparator.reverseOrder())
                .thenComparing(Word::getSum);
        result.sort(comp);

        return result;
    }

    private void next(
            Map<Integer, Map<Integer, List<Word>>> groupBySumLength,
            List<Word> result,
            List<Word> tmp,
            List<Integer> usedSum,
            List<Integer> usedLength,
            Map<Integer, List<Integer>> usedSumLength
    ) {
        for(int a: groupBySumLength.keySet()) {
            if(usedSum.contains(a) || usedSumLength.containsKey(a)) {
                continue;
            }

            usedSumLength.put(a, new ArrayList<>());
            usedSum.add(a);

            for(int b: groupBySumLength.get(a).keySet()) {
                if(usedLength.contains(b) || usedSumLength.get(a).contains(b)) {
                    continue;
                }

                usedLength.add(b);
                usedSumLength.get(a).add(b);
                tmp.add(groupBySumLength.get(a).get(b).get(0));

                next(groupBySumLength, result, tmp, usedSum, usedLength, usedSumLength);
            }
        }

        usedSum.clear();
        usedLength.clear();

        if(tmp.size() > result.size()) {
            result.clear();
            result.addAll(tmp);
        }

        tmp.clear();
    }
}
