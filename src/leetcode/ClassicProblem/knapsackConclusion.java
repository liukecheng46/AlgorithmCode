package leetcode.ClassicProblem;

import java.util.Arrays;
import java.util.List;

// leetcode 背包问题总结
// 每种背包问题都分存在，大小，组合三种模板
// 01背包问题，只能使用一次，dp[i][j] =k表示 使用前i个物品，在费用为j的情况下所能达成的最大收益k -> dp[i][j]=Math.max(dp[i-1][j],dp[i-1][j-cost[i]]+profit[i])
// 完全背包问题，每种可以无限使用 dp[i][j]=Math.max(dp[i-1][j],dp[i-1][j-k*cost[i]]+k*profit[i])  0<=k*cost[i]<=j
public class knapsackConclusion {
    // 分割等和子集
    // 01背包存在问题 （也是可以从记忆化递归推导过来的）
    public boolean canPartition(int[] nums) {
        if (nums.length < 2) return false;
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 != 0) return false;
        int target = sum / 2;
        boolean[][] dp = new boolean[nums.length][target + 1];
        for (int i = 0; i < nums.length; i++) dp[i][0] = true;
        if (nums[0] > target) return false;
        dp[0][nums[0]] = true;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < target + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i])
                    dp[i][j] = dp[i][j] || dp[i - 1][j - nums[i]];
            }
        }
        return dp[nums.length - 1][target];
    }

    // 空间优化的01背包模板（时间复杂度不变）
    // 01背包因为每一行的dp只与上一行的dp有关，所以可以化简为一维滚动数组 表示每行的dp值
    // 需要注意的是，因为每个dp与小的dp有关，所以如果还是由小到大遍历，之前更新的dp[i] 可能后面还需要用到，所以要从大到小遍历，这样对于每个dp[i]，后面需要用到的都已经更新完毕了
    public boolean canPartition2(int[] nums) {
        if (nums.length < 2) return false;
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 != 0) return false;
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        if (nums[0] > target) return false;
        dp[nums[0]] = true;
        for (int i = 1; i < nums.length; i++) {
            // 反向遍历，避免覆盖还需要用到的值
            for (int j = target; j >= 0; j--) {
                if (j >= nums[i])
                    dp[j] |= dp[j - nums[i]];
            }
        }
        return dp[target];
    }

    // 目标和
    // 01背包问题求方案数
    // dp[i][j]=dp[i-1][j] + dp[i-1][j-cost[i]
    public int findTargetSumWays(int[] nums, int t) {
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
                //
                f[i][j] = f[i - 1][j];
                if (j >= x) f[i][j] += f[i - 1][j - x];
            }
        }
        return f[n][m];
    }

    //  最后一块石头的重量II https://leetcode-cn.com/problems/last-stone-weight-ii/solution/yi-pian-wen-zhang-chi-tou-bei-bao-wen-ti-5lfv/
    // 01背包的阅读理解题，和目标和一样可以转化为分为两堆，在小于sum/2的情况下尽可能大，即01背包问题并且每个石子的cost和value相同，套01背包求cost即可，其实也可以用01背包求是否存在解的模板，从sum/2往下找第一个为dp值为true
    public int lastStoneWeightII(int[] ss) {
        int n = ss.length;
        int sum = 0;
        for (int i : ss) sum += i;
        int t = sum / 2;
        int[][] f = new int[n + 1][t + 1];
        for (int i = 1; i <= n; i++) {
            int x = ss[i - 1];
            for (int j = 0; j <= t; j++) {
                f[i][j] = f[i - 1][j];
                if (j >= x) f[i][j] = Math.max(f[i][j], f[i - 1][j - x] + x);
            }
        }
        return Math.abs(sum - f[n][t] - f[n][t]);
    }

    // 多维01背包
    //一和零 https://leetcode-cn.com/problems/ones-and-zeroes/solution/gong-shui-san-xie-xiang-jie-ru-he-zhuan-174wv/
    // 多维01背包求最大收益，区别仅是多了一个维度 f[k][i][j]=max(f[k−1][i][j],f[k−1][i−cnt[k][0]][j−cnt[k][1]]+1)
    // 01背包模板 多加一重反向遍历的循环，也可以用记忆化递归，记忆化数组多加一维
    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        int[][] cnt = new int[len][2];
        for (int i = 0; i < len; i++) {
            int zero = 0, one = 0;
            for (char c : strs[i].toCharArray()) {
                if (c == '0') zero++;
                else one++;
            }
            cnt[i] = new int[]{zero, one};
        }
        int[][] f = new int[m + 1][n + 1];
        for (int k = 0; k < len; k++) {
            int zero = cnt[k][0], one = cnt[k][1];
            for (int i = m; i >= zero; i--) {
                //多了一个维度，还是反向遍历
                for (int j = n; j >= one; j--) {
                    f[i][j] = Math.max(f[i][j], f[i - zero][j - one] + 1);
                }
            }
        }
        return f[m][n];
    }



    // 零钱兑换 https://leetcode-cn.com/problems/coin-change/solution/er-wei-zhuan-yi-wei-dpzui-jian-ji-yi-don-3pfv/
    // 完全平方数 https://leetcode-cn.com/problems/perfect-squares/solution/gong-shui-san-xie-xiang-jie-wan-quan-bei-nqes/
    // 完全背包求最小cost问题，也可以用记忆化递归
    // 标准写法（每个硬币的cost均为1）
    int INF = 1000000000; // 这里不能用Integer.MAX_VALUE会溢出导致结果错误，可以用Integer.MAX_VALUE/2

    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int[][] f = new int[n + 1][amount + 1];

        // 因为是求最小个数问题，所以初始化为极大值
        for (int i = 0; i <= n; i++)
            for (int j = 0; j <= amount; j++)
                f[i][j] = INF;

        f[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            int val = coins[i - 1];
            for (int j = 0; j <= amount; j++) {
                for (int k = 0; k * val <= j; k++) {
                    f[i][j] = Math.min(f[i][j], f[i - 1][j - k * val] + k);
                }
            }
        }
        if (f[n][amount] == INF) f[n][amount] = -1;
        return f[n][amount];
    }

    // 零钱兑换
    // 完全背包的优化写法（相对于01背包，不止空间，时间也优化了，避免了k*cost[i]的重复计算）
    // 01背包的空间写法基本一致，唯一区别是变回正向遍历
    public int coinChange2(int[] coins, int amount) {
        int[] f = new int[amount + 1];
        Arrays.fill(f, INF);
        f[0] = 0;
        for (int i = 0; i < coins.length; i++)
            // 变回正向遍历，等价于考虑k*cost[i]
            for (int j = coins[i]; j <= amount; j++) {
                f[j] = Math.min(f[j], f[j - coins[i]] + 1);
            }
        if (f[amount] == INF) f[amount] = -1;
        return f[amount];
    }

    // 记忆化递归写法，因为每种硬币能重复选择，所以记忆数组只需要记录组合count的最少硬币数
    public int coinChange3(int[] coins, int amount) {
        int[] memo = new int[amount + 1];
        Arrays.fill(memo, -2);
        Arrays.sort(coins);
        return dfs(coins, amount, memo);
    }
    private int dfs(int[] coins, int amount, int[] memo) {
        int res = Integer.MAX_VALUE;
        if (amount < 0) return -1;
        if (amount == 0) {
            return 0;
        }

        if (memo[amount] != -2) {
            return memo[amount];
        }

        for (int coin : coins) {
            int subRes = dfs(coins, amount - coin, memo);
            if (subRes == -1) {
                continue;
            }
            res = Math.min(res, subRes + 1);
        }
        return memo[amount] = (res == Integer.MAX_VALUE) ? -1 : res;
    }


    // 区分爬楼梯类问题和完全背包求方案数的区别 todo
    // 组合总和4 有点类似于完全背包求方案数，但是区别在于不同选择顺序代表不同方案，完全背包选择顺序不同也代表相同方案
    // 因此这道题等价于爬楼梯问题，每次可以爬nums[i]步，求爬到t的方案数（可以重复，即1,2,1和2,1,1不同）
    public int combinationSum4(int[] nums, int t) {
        // 因为 nums[i] 最小值为 1，因此构成答案的最大长度为 target
        int len = nums.length;
        int[][] f = new int[len + 1][t + 1];
        f[0][0] = 1;
        int ans = 0;
        for (int i = 1; i <= len; i++) {
            for (int j = 0; j <= t; j++) {
                for (int u : nums) {
                    if (j >= u) f[i][j] += f[i - 1][j - u];
                }
            }
            ans += f[i][t];
        }
        return ans;
    }
    // 组合总和4： 复杂版爬楼梯 一维空间优化写法
    public int combinationSum42(int[] nums, int t) {
        int[] f = new int[t + 1];
        f[0] = 1;
        for (int j = 1; j <= t; j++) {
            for (int u : nums) {
                if (j >= u) f[j] += f[j - u];
            }
        }
        return f[t];
    }



    // 零钱兑换2 经典的完全背包求方案数问题 （不能重复）
    // 一维空间优化，和上面爬楼梯的代码比较，发现其实只是内外循环变了下，但是代表的含义差距很大
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }

    // 单词拆分1 https://leetcode-cn.com/problems/word-break/
    // 有放入顺序的完全背包存在问题（是否可行），
    // 因为需要考虑放入顺序，所以需要把物品迭代放在里面，把背包迭代放在外面，并且不需要反序
    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            for (String word : wordDict) {   // 对物品的迭代应该放在最里层
                int len = word.length();
                if (len <= i && word.equals(s.substring(i - len, i))) {
                    dp[i] = dp[i] || dp[i - len];
                }
            }
        }
        return dp[n];
    }


    // 数位成本和为目标值的最大数字  https://leetcode-cn.com/problems/form-largest-integer-with-digits-that-add-up-to-target/
    // 复杂的完全背包问题，需要在过程中比较字符串字典序
    // 或者可以让dp代表cost为i时最长长度，再单独算该长度下的最大字符串 https://leetcode-cn.com/problems/form-largest-integer-with-digits-that-add-up-to-target/solution/gong-shui-san-xie-fen-liang-bu-kao-lu-we-uy4y/
    class Solution {
        public String largestNumber(int[] cost, int target) {
            String[] f = new String[target + 1];

            int n = cost.length;
            // 起始状态
            f[0] = "";
            // 背包问题，先枚举物品
            for(int i = 0; i < n; i ++) {
                // 完全背包，从低到高枚举背包容量
                for(int j = cost[i]; j <= target; j++) {
                    // 之前的容量是无法组成物品的，所以添加当前物品是没有意义的
                    if(f[j - cost[i]] == null) continue;
                    // 因为是从低到高枚举的物品，所以，优先把数字放在前面
                    String cur = String.valueOf(i + 1) + f[j - cost[i]];
                    // 比较两个数字，取大的
                    if(compare(cur, f[j])) {
                        f[j] = cur;
                    }
                }
            }

            return f[target] == null ? "0" : f[target];
        }

        // 判断两个字符串组成的数字的大小
        private boolean compare(String a, String b) {
            if(b == null) return true;
            int n = a.length(), m = b.length();
            if(n > m) return true;
            if(m > n) return false;

            for(int i = 0; i < n; i++) {
                if(a.charAt(i) > b.charAt(i)) return true;
                else if(a.charAt(i) < b.charAt(i)) return false;
            }

            return true;
        }


    }



    // 分组背包问题
    // 和完全背包问题的区别在于，每种元素只能使用k次
    // 扔骰子的k种方法 https://leetcode-cn.com/problems/number-of-dice-rolls-with-target-sum/solution/jian-dan-de-ji-yi-hua-sou-suo-by-zhouzih-6j4e/
    // 记忆化搜索-> 分组背包 求方案数
    int mod= (int) (1e9+7);
    int[][] memo = new int[31][1001];
    int numRollsToTarget(int d, int f, int target) {
        for(int i=0;i<31;i++)for(int j=0;j<1001;j++)memo[i][j]=-1;
        return dfs(d,target,f);
    }
    int dfs(int cnt,int target,int maxNum){
        if(cnt<=0||target<=0){
            if(cnt==0&&target==0)return 1;
            else return 0;
        }
        if(memo[cnt][target]!=-1)return memo[cnt][target];
        int ans=0;
        for(int i=1;i<=maxNum;i++){
            ans=(ans+dfs(cnt-1,target-i,maxNum))%mod;
        }
        memo[cnt][target]=ans;
        return ans;
    }

    // 分组背包 dp写法 求方案数
    public int numRollsToTarget2(int n, int m, int t) {
        int[][] f = new int[n + 1][t + 1];
        f[0][0] = 1;
        // 枚举物品组（每个骰子）
        for (int i = 1; i <= n; i++) {
            // 枚举背包容量（所掷得的总点数）
            for (int j = 0; j <= t; j++) {
                // 枚举决策（当前骰子所掷得的点数）
                for (int k = 1; k <= m; k++) {
                    if (j >= k) {
                        f[i][j] = (f[i][j] + f[i-1][j-k]) % mod;
                    }
                }
            }
        }
        return f[n][t];
    }

}
