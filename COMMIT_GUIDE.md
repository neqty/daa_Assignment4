# Suggested Git history (10 small commits)

This ZIP has **no existing Git commits**. If you want a meaningful history, stage and commit files in the sequence below. Do not pretend these were made on earlier dates; run the commands yourself in order.

Start with `git init -b main`. Then run the commands from inside the project folder:

```bash
git add pom.xml .gitignore
git commit -m "chore: set up Maven project and Java 17"

git add src/main/java/kz/sdu/daa/Counter.java src/main/java/kz/sdu/daa/EditDistance.java src/main/java/kz/sdu/daa/RecursiveDistance.java
git commit -m "feat(edit): implement basic recursive distance"

git add src/main/java/kz/sdu/daa/TableDistance.java
git commit -m "feat(edit): implement bottom-up DP table"

git add src/main/java/kz/sdu/daa/TwoRowDistance.java
git commit -m "feat(edit): reduce DP memory to two rows"

git add src/main/java/kz/sdu/daa/StringSearch.java src/main/java/kz/sdu/daa/NaiveSearch.java
git commit -m "feat(search): add naive pattern matching"

git add src/main/java/kz/sdu/daa/KMPSearch.java
git commit -m "feat(search): implement LPS and KMP matching"

git add src/test/java/kz/sdu/daa/
git commit -m "test: cover reference values and random cases"

git add src/main/java/kz/sdu/daa/InputGenerator.java src/main/java/kz/sdu/daa/Experiment.java results/results.csv
git commit -m "feat(experiment): add seeded inputs and CSV measurements"

git add scripts/plot_results.py results/plots/ REPORT.md
git commit -m "docs(report): add plots and discuss measurements"

git add README.md COMMIT_GUIDE.md
git commit -m "docs: explain setup, testing and repository"

git tag v1.0
```

Check history: `git log --oneline --decorate`. Then make a new empty repository on GitHub and run:

```bash
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO.git
git push -u origin main
git push origin v1.0
```

You can edit any file before making its commit. If you rerun the benchmark later, `results.csv` changes because elapsed time is not deterministic; commit the updated CSV if you decide to submit the updated measurements.
