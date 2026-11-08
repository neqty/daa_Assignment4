package kz.sdu.daa;

public final class TwoRowDistance implements EditDistance {
    private Counter counter = new Counter();

    // State: previous[j] and current[j] represent consecutive DP rows.
    // Transition: on match use previous[j-1], else 1+min(previous[j],current[j-1],previous[j-1]).
    // Base case: first row is 0..m; first column of each next row is its row index.
    @Override
    public int distance(String a, String b) {
        counter = new Counter();
        // Use the shorter string as columns for O(min(n,m)) memory.
        if (b.length() > a.length()) {
            String temp = a;
            a = b;
            b = temp;
        }
        int[] previous = new int[b.length() + 1];
        int[] current = new int[b.length() + 1];
        for (int j = 0; j <= b.length(); j++) previous[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            current[0] = i;
            for (int j = 1; j <= b.length(); j++) {
                counter.tick(); // One computed DP cell, same as table implementation.
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    current[j] = previous[j - 1];
                } else {
                    current[j] = 1 + Math.min(previous[j],
                            Math.min(current[j - 1], previous[j - 1]));
                }
            }
            int[] temp = previous;
            previous = current;
            current = temp;
        }
        return previous[b.length()];
    }

    @Override public long operations() { return counter.value(); }
    @Override public String name() { return "two_rows"; }
}
