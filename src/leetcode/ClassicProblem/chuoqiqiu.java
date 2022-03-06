package leetcode.ClassicProblem;

// 预测赢家 -> 石子游戏 -> 我能赢吗-> 戳气球
// 掌握从  minimax（普通dfs） -> 记忆化dfs -> 状态压缩的记忆化dfs -> 动态规划 -> 巧妙选择dp的状态表示 的分析方法
public class chuoqiqiu {
    // 预测赢家 https://leetcode-cn.com/problems/predict-the-winner/
    // 每次从两边拿，得分累计比谁大

    // 解法一：minimax：不带记忆化的dfs
    public boolean PredictTheWinner(int[] nums) {
        return play(nums, 0, nums.length - 1) >= 0;
    }
    // 表示当前操作的选手相对另一个选手最多可以多多少收益
    private int play(int[] nums, int start, int end) {
        if (start > end) return 0;
        // 选左边的数，可能获得的最大收益
        int planA = nums[start] - play(nums, start + 1, end);
        // 选右边的数，可能获得的最大收益
        int planB = nums[end] - play(nums, start, end - 1);
        return Math.max(planA, planB);
    }

    // 解法二： 记忆化dfs
    int[][] memo;
    public boolean PredictTheWinner2(int[] nums) {
        memo = new int[nums.length][nums.length];
        return play2(nums, 0, nums.length - 1) >= 0;
    }
    // 表示当前操作的选手相对另一个选手最多可以多多少收益
    private int play2(int[] nums, int start, int end) {
        if (start > end) return 0;
        if (memo[start][end] != 0) return memo[start][end];
        // 选左边的数，可能获得的最大收益
        int planA = nums[start] - play2(nums, start + 1, end);
        // 选右边的数，可能获得的最大收益
        int planB = nums[end] - play2(nums, start, end - 1);
        memo[start][end] = Math.max(planA, planB);

        return memo[start][end];
    }

    // 解法三： 自顶向下的记忆化dfs 归化为 自底向上的动态规划
    // dp[i][j]表示当前剩下i-j的数时，先手的玩家能获得的最大收益
    // 转移方程：dp[i][j]=max(nums[i]−dp[i+1][j],nums[j]−dp[i][j−1])
    // 观察转移方程，转化为区间dp问题
    public boolean PredictTheWinner3(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = nums[i];
        }

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i<n-len+1;i++) {
                int j = i+len-1;
                dp[i][j] = Math.max(nums[i]-dp[i+1][j],nums[j]-dp[i][j-1]);
            }
        }
        return dp[0][n-1]>=0;
    }


    // 石子游戏 https://leetcode-cn.com/problems/stone-game/
    // 和前一题一样，唯一区别是长度为偶数，且和为奇数，即一定有输赢
    // 也可以用前面的三种方法做,但还有一种博弈论的分析方法
    // 将石子按偶数和奇数分为两组，先手只取最开始计算总和大的那组，可以做到必胜



    // 我能赢吗 https://leetcode-cn.com/problems/can-i-win/
    // 换汤不换药 区别在于 记忆化数组当前状态的表示方式不能只用i和j，需要状态压缩来表示每个数是否被选择
    // 还有就是中间步骤每一次都可能达成胜利条件，所以dfs函数应该返回当前状态是否能稳赢
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if(maxChoosableInteger >= desiredTotal) return true;
        if((1 + maxChoosableInteger) * maxChoosableInteger / 2 < desiredTotal) return false;

        return dfs(0, desiredTotal, new Boolean[1 << maxChoosableInteger], maxChoosableInteger);
    }

    private boolean dfs(int state, int desiredTotal, Boolean[] dp, int maxChoosableInteger){
        if(dp[state] != null){
            return dp[state];
        }

        for(int i = 1; i <= maxChoosableInteger; i++){
            int cur = 1 << (i - 1);
            if((cur & state) != 0){
                continue;
            }

            if(i >= desiredTotal || !dfs(cur|state, desiredTotal - i, dp, maxChoosableInteger)){
                return dp[state] = true;
            }
        }
        return dp[state] = false;
    }
    // 猜数字大小2 https://leetcode-cn.com/problems/guess-number-higher-or-lower-ii/
    // 也是同理，不能用二分，也没有数学规律，只能dfs尝试所有情况再加上记忆化优化，也可以归化为动态规划问题
    static int N = 210;
    static int[][] cache = new int[N][N];
    public int getMoneyAmount(int n) {
        return dfs(1, n);
    }
    int dfs(int l, int r) {
        if (l >= r) return 0;
        if (cache[l][r] != 0) return cache[l][r];
        int ans = 0x3f3f3f3f;
        // 尝试选择这个区间里的所有数
        for (int x = l; x <= r; x++) {
            // 当选择的数位 x 时，至少需要 cur 才能猜中数字（这里是max）
            int cur = Math.max(dfs(l, x - 1), dfs(x + 1, r)) + x;
            // 在所有我们可以决策的数值之间取最优（这里是min，注意区分）
            ans = Math.min(ans, cur);
        }
        cache[l][r] = ans;
        return ans;
    }


    // nim游戏


    // 戳气球
    // 不能回溯上加记忆化，因为和每一步的顺序都有关，没办法状态压缩记忆化
    // 考虑dp时，正常思路是对于dp[i][j]，枚举每一种可能的划分点k，再分为i-k和k+1-j两个子区间，但是删除k的话i-k区间的右边又有k+1，两个子区间问题发生了依赖，所以没办法这样dp
    // 所以考虑对于dp[i][j]，设划分点k为这个区间最后一次的划分点，即除了i,k和j外其他点都已经被删除完了，这样dp[i][j]= dp[i+1,k-1]+dp[k+1][j-1]+num[i]*num[j]*num[k]，转化为区间dp问题
    // 初始化：dp[i][i]为0
    // https://leetcode-cn.com/problems/burst-balloons/solution/chao-xiang-xi-hui-su-dao-fen-zhi-dao-dp-by-niu-you/
    public static int maxCoins(int[] nums) {
        //避免空指针异常
        if (nums == null) {
            return 0;
        }

        //创建虚拟边界
        int length = nums.length;
        int[] nums2 = new int[length + 2];
        System.arraycopy(nums, 0, nums2, 1, length);
        nums2[0] = 1;
        nums2[length + 1] = 1;
        length = nums2.length;

        //创建dp表
        length = nums2.length;
        int[][] dp = new int[length][length];

        //开始dp：i为begin，j为end，k为在i、j区间划分子问题时的边界
        for (int i = length - 2; i > -1; i--) {
            for (int j = i + 2; j < length; j++) {
                //维护一个最大值；如果i、j相邻，值为0
                int max = 0;
                for (int k = i + 1; k < j; k++) {
                    int temp = dp[i][k] + dp[k][j] + nums2[i] * nums2[k] * nums2[j];
                    if (temp > max) {
                        max = temp;
                    }
                }
                dp[i][j] = max;
            }
        }
        return dp[0][length-1];
    }

}
