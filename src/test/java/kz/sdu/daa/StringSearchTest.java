package kz.sdu.daa;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class StringSearchTest {
    private final StringSearch naive = new NaiveSearch();
    private final KMPSearch kmp = new KMPSearch();

    @Test void lpsReferences() {
        assertArrayEquals(new int[]{0, 1, 2, 0, 1, 2, 3, 3}, kmp.lps("AAACAAAA"));
        assertArrayEquals(new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4}, kmp.lps("ABABCABAB"));
    }

    @Test void searchReferences() {
        for (StringSearch algorithm : List.of(naive, kmp)) {
            assertEquals(List.of(10), algorithm.search("ABABDABACDABABCABAB", "ABABCABAB"));
            assertEquals(List.of(0, 1, 2, 3), algorithm.search("AAAAA", "AA"));
            assertEquals(List.of(0, 2, 4), algorithm.search("ABABABA", "ABA"));
            assertEquals(List.of(), algorithm.search("abc", "xyz"));
            assertEquals(List.of(), algorithm.search("abc", "abcd"));
            assertEquals(List.of(), algorithm.search("", "a"));
            assertEquals(List.of(0), algorithm.search("abc", "abc"));
            assertThrows(IllegalArgumentException.class, () -> algorithm.search("abc", ""));
        }
    }

    @Test void randomAgreement() {
        Random random = new Random(91);
        for (int test = 0; test < 100; test++) {
            String text = word(random, random.nextInt(70));
            String pattern = word(random, 1 + random.nextInt(9));
            assertEquals(naive.search(text, pattern), kmp.search(text, pattern));
        }
    }

    private String word(Random random, int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) result.append((char) ('a' + random.nextInt(3)));
        return result.toString();
    }
}
