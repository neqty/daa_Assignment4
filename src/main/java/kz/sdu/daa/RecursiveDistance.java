package kz.sdu.daa;

public final class RecursiveDistance implements EditDistance {
    private Counter counter = new Counter();

    @Override
    public int distance(String a, String b) {
        counter = new Counter();
        return solve(a, b, a.length(), b.length());
    }

    // State: solve(i,j) is the distance between the first i and j characters.
    // Transition: matching ends use solve(i-1,j-1); otherwise min(delete, insert, replace)+1.
    // Base case: if either prefix is empty, return the length of the other prefix.
    private int solve(String a, String b, int i, int j) {
        counter.tick(); // One recursive state visit, including repeated visits.
        if (i == 0) return j;
        if (j == 0) return i;
        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return solve(a, b, i - 1, j - 1);
        }
        int deletion = solve(a, b, i - 1, j);
        int insertion = solve(a, b, i, j - 1);
        int replacement = solve(a, b, i - 1, j - 1);
        return 1 + Math.min(replacement, Math.min(deletion, insertion));
    }

    @Override public long operations() { return counter.value(); }
    @Override public String name() { return "recursive"; }
}
