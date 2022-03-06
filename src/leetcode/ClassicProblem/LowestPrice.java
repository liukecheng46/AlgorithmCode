package leetcode.ClassicProblem;

import java.util.HashSet;
import java.util.Set;

// 最低票价 https://leetcode-cn.com/problems/minimum-cost-for-tickets/
// 没想到怎么记忆化dfs，所以没想出来怎么dp
// 题目隐藏的信息：当天需要出行则进行考虑，不需要出行则dp[i]=dp[i-1]，主要是这里做的时候没想通
public class LowestPrice {

    // 记忆化dp，重点在于反向
    int[] costs;
    Integer[] memo;
    Set<Integer> dayset;
    public int mincostTickets(int[] days, int[] costs) {
        this.costs = costs;
        memo = new Integer[366];
        dayset = new HashSet();
        for (int d: days) {
            dayset.add(d);
        }
        return dp(1);
    }

    public int dp(int i) {
        if (i > 365) {
            return 0;
        }
        if (memo[i] != null) {
            return memo[i];
        }
        if (dayset.contains(i)) {
            memo[i] = Math.min(Math.min(dp(i + 1) + costs[0], dp(i + 7) + costs[1]), dp(i + 30) + costs[2]);
        } else {
            memo[i] = dp(i + 1);
        }
        return memo[i];
    }


    // 比较好想也需要掌握的解法
    // 前向dp -> 第4-10天的票，第10天才付款 ：第i天的花费等于第i-1 i-7 i-30天买票最小的那个，类似于跳楼梯，只是多了一个出不出行
    public int mincostTickets2(int[] days, int[] costs) {
        int[] dp = new int[days[days.length-1]+1];
        dp[0] = 0;
        for (int i = 0; i < days.length; i++) {
            dp[days[i]] = -1;
        }
        for (int i = 1; i <= days[days.length-1]; i++) {
            if(dp[i] == -1){
                dp[i] = Math.min(Math.min(dp[i-1>=0?i-1:0]+costs[0],dp[i-7>=0?i-7:0]+costs[1]),dp[i-30>=0?i-30:0]+costs[2]);
            }else {
                dp[i] = dp[i-1];
            }
        }
        return dp[dp.length-1];
    }

}
