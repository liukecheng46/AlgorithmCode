package leetcode.ClassicProblem;

import java.util.HashMap;
import java.util.Map;

// 知识点： dfs记忆化 -> 动态规划
// 目标和 https://leetcode-cn.com/problems/target-sum/solution/mu-biao-he-by-leetcode-solution-o0cp/
// 做的时候看数据范围可以回溯爆搜，就回溯了
// 这题回溯也是可以用记忆化递归优化的
// 这题也是一道经典的01背包问题,并且利用题目特性将带减法的大范围dp缩小为只做加法的小范围dp，这个思路非常值得学习
public class TargetSum {
    // 记忆化递归来优化回溯 todo
    // 确实没想到这个回溯还能记忆化优化，用index+当前总和作为哈希表的key
    public int findTargetSumWays(int[] nums, int t) {
        return dfs(nums, t, 0, 0);
    }

    Map<String, Integer> cache = new HashMap<>();

    int dfs(int[] nums, int t, int u, int cur) {
        String key = u + "_" + cur;
        if (cache.containsKey(key)) return cache.get(key);
        if (u == nums.length) {
            cache.put(key, cur == t ? 1 : 0);
            return cache.get(key);
        }
        int left = dfs(nums, t, u + 1, cur + nums[u]);
        int right = dfs(nums, t, u + 1, cur - nums[u]);
        // 关键点，left+right
        cache.put(key, left + right);
        return cache.get(key);
    }

    //  基础动态规划解法
    //  dp[i][j]表示用前i个数，能达成j的方案数
    //  用加号或者用减号： dp[i][j]=dp[i−1][j−nums[i−1]]+dp[i−1][j+nums[i−1]]
    //  因为需要做减法，所以j的范围只能为所有可能：-sum到sum，非常大，还需要做偏移以支持负数（0-2*sum）
    public int findTargetSumWays2(int[] nums, int t) {
        int n = nums.length;
        int s = 0;
        for (int i : nums) s += Math.abs(i);
        if (Math.abs(t) > s) return 0;
        int[][] f = new int[n + 1][2 * s + 1];
        // dp[0][0]=1 初始化。表示什么也不用且count为0的方案为一种
        f[0][0 + s] = 1;
        for (int i = 1; i <= n; i++) {
            int x = nums[i - 1];
            // -s到s范围
            for (int j = -s; j <= s; j++) {
                //越界判断
                if ((j - x) + s >= 0) f[i][j + s] += f[i - 1][(j - x) + s];
                if ((j + x) + s <= 2 * s) f[i][j + s] += f[i - 1][(j + x) + s];
            }
        }
        return f[n][t + s];
    }

    // 优化动态规划解法 todo
    // 利用题目特性进行变形，将减法去掉，只做加法，这样j的范围就可以缩小到0-target
    // 设「负值部分」的绝对值总和为 m，即可得：(s−m)−m=s−2∗m=target -> m=(s-target)/2
    // 问题转换为：只使用 + 运算符，从 nums 凑出 m 的方案数。另外，由于 nums 均为非负整数，因此我们需要确保 s - target 能够被 2 整除。
    // 新状态转移方程只会从小至大：f[i][j]=f[i−1][j]+f[i−1][j−nums[i−1]]
    public int findTargetSumWays3(int[] nums, int t) {
        int n = nums.length;
        int s = 0;
        for (int i : nums) s += Math.abs(i);
        if (t > s || (s - t) % 2 != 0) return 0;
        int m = (s - t) / 2;
        int[][] f = new int[n + 1][m + 1];
        f[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            int x = nums[i - 1];
            for (int j = 0; j <= m; j++) {
                f[i][j] += f[i - 1][j];
                if (j >= x) f[i][j] += f[i - 1][j - x];
            }
        }
        return f[n][m];
    }


    //  类似题目： 青蛙过河 https://leetcode-cn.com/problems/frog-jump/solution/
    //  有点像跳跃游戏
    //  数据范围很大，普通dfs不行，需要记忆化，而可以记忆化的一般都可以dp

    //  普通dfs（超时）
        Map<Integer, Integer> map = new HashMap<>();
        public boolean canCross(int[] ss) {
            int n = ss.length;
            // 将石子信息存入哈希表
            // 为了快速判断是否存在某块石子，以及快速查找某块石子所在下标
            for (int i = 0; i < n; i++) {
                map.put(ss[i], i);
            }
            // check first step
            // 根据题意，第一步是固定经过步长 1 到达第一块石子（下标为 1）
            if (!map.containsKey(1)) return false;
            return dfs2(ss, ss.length, 1, 1);
        }

        /**
         * 判定是否能够跳到最后一块石子
         * @param ss 石子列表【不变】
         * @param n  石子列表长度【不变】
         * @param u  当前所在的石子的下标
         * @param k  上一次是经过多少步跳到当前位置的
         * @return 是否能跳到最后一块石子
         */
        boolean dfs2(int[] ss, int n, int u, int k) {
            if (u == n - 1) return true;
            for (int i = -1; i <= 1; i++) {
                // 如果是原地踏步的话，直接跳过
                if (k + i == 0) continue;
                // 下一步的石子理论编号
                int next = ss[u] + k + i;
                // 如果存在下一步的石子，则跳转到下一步石子，并 DFS 下去
                if (map.containsKey(next)) {
                    boolean cur = dfs2(ss, n, map.get(next), k + i);
                    if (cur) return true;
                }
            }
            return false;
        }


        // 记忆化DFS O（n^2）
    class Solution {
        Map<Integer, Integer> map = new HashMap<>();
        // int[][] cache = new int[2009][2009];
        Map<String, Boolean> cache = new HashMap<>();
        public boolean canCross(int[] ss) {
            int n = ss.length;
            for (int i = 0; i < n; i++) {
                map.put(ss[i], i);
            }
            // check first step
            if (!map.containsKey(1)) return false;
            return dfs(ss, ss.length, 1, 1);
        }
        boolean dfs(int[] ss, int n, int u, int k) {
            String key = u + "_" + k;
            // if (cache[u][k] != 0) return cache[u][k] == 1;
            if (cache.containsKey(key)) return cache.get(key);
            if (u == n - 1) return true;
            for (int i = -1; i <= 1; i++) {
                if (k + i == 0) continue;
                int next = ss[u] + k + i;
                if (map.containsKey(next)) {
                    boolean cur = dfs(ss, n, map.get(next), k + i);
                    // cache[u][k] = cur ? 1 : -1;
                    cache.put(key, cur);
                    if (cur) return true;
                }
            }
            // cache[u][k] = -1;
            cache.put(key, false);
            return false;
        }
    }

    // 动态规划
    class Solution2 {
        public boolean canCross(int[] ss) {
            int n = ss.length;
            // check first step
            if (ss[1] != 1) return false;
            boolean[][] f = new boolean[n + 1][n + 1];
            f[1][1] = true;
            for (int i = 2; i < n; i++) {
                for (int j = 1; j < i; j++) {
                    int k = ss[i] - ss[j];
                    // 我们知道从位置 j 到位置 i 是需要步长为 k 的跳跃
                    // 而从位置 j 发起的跳跃最多不超过 j + 1
                    // 因为每次跳跃，下标至少增加 1，而步长最多增加 1
                    if (k <= j + 1) {
                        f[i][k] = f[j][k - 1] || f[j][k] || f[j][k + 1];
                    }
                }
            }
            for (int i = 1; i < n; i++) {
                if (f[n - 1][i]) return true;
            }
            return false;
        }
    }

}
