#!/usr/bin/env python3
"""Draw plots from actual experiment output; requires matplotlib."""
import csv
import statistics
from collections import defaultdict
from pathlib import Path
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

ROOT = Path(__file__).resolve().parents[1]
with (ROOT / "results/results.csv").open(newline="") as file:
    rows = list(csv.DictReader(file))
for row in rows:
    row["n"] = int(row["n"])
    row["operations"] = int(row["operations"])
    row["time_ms"] = float(row["time_ms"])

PLOTS = ROOT / "results/plots"
PLOTS.mkdir(exist_ok=True)

def series(alg, input_type, metric):
    group = defaultdict(list)
    for row in rows:
        if row["algorithm"] == alg and row["input"] == input_type:
            group[row["n"]].append(row[metric])
    x = sorted(group)
    return x, [statistics.median(group[n]) for n in x]

def finish(name, title, xlabel, ylabel):
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.grid(alpha=0.25)
    plt.legend()
    plt.tight_layout()
    plt.savefig(PLOTS / name, dpi=180)
    plt.close()

for alg in ("recursive", "table", "two_rows"):
    x, y = series(alg, "different", "operations")
    plt.plot(x, y, marker="o", label=alg)
plt.yscale("log")
finish("01_distance_operations.png", "Edit distance: different characters", "Length of each string (characters)", "State visits / computed DP cells (log scale)")

for alg in ("table", "two_rows"):
    x, y = series(alg, "random", "time_ms")
    plt.plot(x, y, marker="o", label=alg)
finish("02_distance_time.png", "DP runtime: random strings (median of 5)", "Length of each string (characters)", "Elapsed time (ms)")

for alg in ("naive", "kmp"):
    for input_type in ("random", "late_mismatch"):
        x, y = series(alg, input_type, "operations")
        plt.plot(x, y, marker="o", label=f"{alg} - {input_type}")
finish("03_search_comparisons.png", "Search: character comparisons (including KMP LPS)", "Text length (characters)", "Character comparisons (count)")

x, slow = series("naive", "late_mismatch", "operations")
_, fast = series("kmp", "late_mismatch", "operations")
plt.plot(x, [a / b for a, b in zip(slow, fast)], marker="o", label="Naive / KMP")
finish("04_search_ratio.png", "Late-mismatch search: comparison ratio", "Text length (characters)", "Comparison ratio (times)")
print("Created four plots in", PLOTS)
