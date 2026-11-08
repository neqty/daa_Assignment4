package kz.sdu.daa;

public final class TableDistance implements EditDistance {
    private Counter counter = new Counter();

    // State: dp[i][j] stores the distance between prefixes of lengths i and j.
    // Transition: copy diagonal on a match; else 1+min(top,left,diagonal).
    // Base case: dp[i][0]=i and dp[0][j]=j for empty prefixes.
    @Override
    public int distance(String a, String b) {
        counter = new Counter();
        int n = a.length(), m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                counter.tick(); // One filled inner table cell.
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j],
                            Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }
        return dp[n][m];
    }

    @Override public long operations() { return counter.value(); }
    @Override public String name() { return "table"; }
}
