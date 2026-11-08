package kz.sdu.daa;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public final class Experiment {
    private static final int RUNS = 5;
    private Experiment() {}

    public static void main(String[] args) throws IOException {
        Path output = Path.of("results/results.csv");
        Files.createDirectories(output.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(output)) {
            writer.write("family,algorithm,input,n,pattern_length,run,time_ms,operations,result\n");
            for (EditDistance algorithm : List.of(new RecursiveDistance(), new TableDistance(), new TwoRowDistance())) {
                int[] sizes = algorithm instanceof RecursiveDistance
                        ? new int[]{2, 4, 6, 8} : new int[]{40, 80, 160, 320};
                for (String input : List.of("random", "different")) {
                    for (int n : sizes) {
                        InputGenerator.Pair pair = InputGenerator.distanceInput(n, input);
                        for (int run = 1; run <= RUNS; run++) {
                            long start = System.nanoTime();
                            int answer = algorithm.distance(pair.first(), pair.second());
                            double time = (System.nanoTime() - start) / 1_000_000.0;
                            row(writer, "distance", algorithm.name(), input, n, 0, run,
                                    time, algorithm.operations(), answer);
                        }
                    }
                }
            }
            for (StringSearch algorithm : List.of(new NaiveSearch(), new KMPSearch())) {
                for (String input : List.of("random", "late_mismatch")) {
                    for (int n : new int[]{1000, 2000, 4000, 8000}) {
                        InputGenerator.Pair pair = InputGenerator.searchInput(n, input);
                        for (int run = 1; run <= RUNS; run++) {
                            long start = System.nanoTime();
                            int count = algorithm.search(pair.first(), pair.second()).size();
                            double time = (System.nanoTime() - start) / 1_000_000.0;
                            row(writer, "search", algorithm.name(), input, n,
                                    pair.second().length(), run, time, algorithm.comparisons(), count);
                        }
                    }
                }
            }
        }
        System.out.println("Wrote " + output + " (200 measured runs)");
    }

    private static void row(BufferedWriter writer, String family, String algorithm, String input,
                            int n, int patternLength, int run, double time, long operations, int result)
            throws IOException {
        writer.write(String.format(Locale.US, "%s,%s,%s,%d,%d,%d,%.6f,%d,%d%n",
                family, algorithm, input, n, patternLength, run, time, operations, result));
    }
}
