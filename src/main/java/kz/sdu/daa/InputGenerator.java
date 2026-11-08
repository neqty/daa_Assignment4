package kz.sdu.daa;

import java.util.Random;

public final class InputGenerator {
    public record Pair(String first, String second) {}
    private InputGenerator() {}

    public static Pair distanceInput(int n, String input) {
        if (input.equals("different")) return new Pair("a".repeat(n), "b".repeat(n));
        if (input.equals("random")) {
            Random random = new Random(20261108L + n);
            return new Pair(randomString(random, n, 4), randomString(random, n, 4));
        }
        throw new IllegalArgumentException("Unknown distance input: " + input);
    }

    public static Pair searchInput(int n, String input) {
        int m = 12;
        if (input.equals("late_mismatch")) {
            return new Pair("a".repeat(n), "a".repeat(m - 1) + "b");
        }
        if (input.equals("random")) {
            Random random = new Random(20261108L + n * 37L);
            return new Pair(randomString(random, n, 4), randomString(random, m, 4));
        }
        throw new IllegalArgumentException("Unknown search input: " + input);
    }

    private static String randomString(Random random, int size, int alphabetSize) {
        StringBuilder builder = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            builder.append((char) ('a' + random.nextInt(alphabetSize)));
        }
        return builder.toString();
    }
}
