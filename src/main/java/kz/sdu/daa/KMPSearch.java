package kz.sdu.daa;

import java.util.ArrayList;
import java.util.List;

public final class KMPSearch implements StringSearch {
    private Counter counter = new Counter();

    // Public so it can be checked independently against the lecture examples.
    public int[] lps(String pattern) {
        int[] result = new int[pattern.length()];
        int prefix = 0;
        for (int i = 1; i < pattern.length();) {
            counter.tick(); // LPS construction also compares characters.
            if (pattern.charAt(i) == pattern.charAt(prefix)) {
                result[i++] = ++prefix;
            } else if (prefix > 0) {
                prefix = result[prefix - 1];
            } else {
                result[i++] = 0;
            }
        }
        return result;
    }

    @Override
    public List<Integer> search(String text, String pattern) {
        if (pattern.isEmpty()) throw new IllegalArgumentException("Pattern cannot be empty");
        counter = new Counter();
        List<Integer> matches = new ArrayList<>();
        if (pattern.length() > text.length()) return matches;
        int[] prefix = lps(pattern);
        int i = 0, j = 0;
        while (i < text.length()) {
            counter.tick(); // Comparisons in searching + LPS construction.
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == pattern.length()) {
                    matches.add(i - j);
                    j = prefix[j - 1]; // Keep overlap matches.
                }
            } else if (j > 0) {
                j = prefix[j - 1]; // Never move the text index backwards.
            } else {
                i++;
            }
        }
        return matches;
    }

    @Override public long comparisons() { return counter.value(); }
    @Override public String name() { return "kmp"; }
}
