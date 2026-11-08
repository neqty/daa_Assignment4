package kz.sdu.daa;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class EditDistanceTest {
    private final List<EditDistance> algorithms = List.of(
            new RecursiveDistance(), new TableDistance(), new TwoRowDistance());

    @Test void referenceValues() {
        for (EditDistance algorithm : algorithms) {
            assertEquals(3, algorithm.distance("kitten", "sitting"));
            assertEquals(2, algorithm.distance("flaw", "lawn"));
            assertEquals(5, algorithm.distance("intention", "execution"));
            assertEquals(3, algorithm.distance("", "abc"));
            assertEquals(3, algorithm.distance("abc", ""));
            assertEquals(0, algorithm.distance("", ""));
            assertEquals(0, algorithm.distance("abc", "abc"));
            assertEquals(1, algorithm.distance("a", "b"));
        }
    }

    @Test void randomAgreement() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            String a = word(random, random.nextInt(6));
            String b = word(random, random.nextInt(6));
            int expected = algorithms.get(0).distance(a, b);
            assertEquals(expected, algorithms.get(1).distance(a, b), a + " / " + b);
            assertEquals(expected, algorithms.get(2).distance(a, b), a + " / " + b);
        }
    }

    private String word(Random random, int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) result.append((char) ('a' + random.nextInt(4)));
        return result.toString();
    }
}
