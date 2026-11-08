# DAA Assignment 4 — City Search & Spell-Check

**Yerkinbek Arsen | SE-2516**  
Design and Analysis of Algorithms — Assignment 4

This project compares three ways of finding edit distance and two methods of finding a pattern in a text. I used a city search example because users can mistype street names and we may need to search large log files.

## What's inside

- `RecursiveDistance` — simple recursion, intentionally slow for different strings.
- `TableDistance` — bottom-up dynamic programming with a full table.
- `TwoRowDistance` — same answer, but only keeps the previous and current row.
- `NaiveSearch` — compares the pattern at every possible text position.
- `KMPSearch` — uses an LPS array to avoid repeating matches.
- `InputGenerator` — fixed-seed inputs so the experiments are repeatable.
- `Experiment` — measures 5 runs per case and saves each run in CSV.

## Run in IntelliJ IDEA

1. Open this folder as a project, not just a single Java file.
2. Use JDK 17 or newer and let IntelliJ import the Maven `pom.xml`.
3. Run `EditDistanceTest` and `StringSearchTest`, or use the Maven commands below.
4. Run `Experiment.main()` to regenerate `results/results.csv`.
5. Run `python3 scripts/plot_results.py` to regenerate the PNG charts (needs matplotlib).

From the terminal in the project folder:

```bash
mvn test
mvn exec:java
python3 -m pip install matplotlib  # only if matplotlib is missing
python3 scripts/plot_results.py
```

## A few implementation choices

I used a common interface for each algorithm family, so the benchmark doesn't need separate code for every version. I counted recursive calls for recursion, inner DP cells for the table methods, and actual character comparisons for search. KMP counts comparisons made while building LPS too. All counters are updated inside the algorithms, not estimated later.

The experiment deliberately includes a difficult input: all `a` characters in the text and a search pattern ending with `b`. This makes naive search almost finish the pattern at every text position before failing. The two-row DP version also swaps the strings when helpful to use memory based on the shorter string.

## Results and write-up

See [REPORT.md](REPORT.md) for the experiment design, charts, what I expected, and what I found. The raw data is in [results/results.csv](results/results.csv). Timing depends on the computer and JVM warm-up; comparisons and DP-cell counts are better for explaining growth.

## Git submission

Submit the ZIP in Moodle and include your own GitHub repository link on branch `main` with tag `v1.0`. See [COMMIT_GUIDE.md](COMMIT_GUIDE.md) for a possible sequence of 10 small commits.

## AI use

I used ChatGPT to help draft and review the Java implementation, test cases, and report structure. I checked the algorithm examples and generated the experiment data by running the program. The measurements shown in this repository are from actual execution, not estimated values.
