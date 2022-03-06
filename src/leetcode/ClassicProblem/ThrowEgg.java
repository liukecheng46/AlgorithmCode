package leetcode.ClassicProblem;


// 典中典之扔鸡蛋问题 https://leetcode-cn.com/problems/super-egg-drop/solution/ji-dan-diao-luo-xiang-jie-by-shellbye/
// 100层楼 扔两个鸡蛋 -> n层楼扔k个鸡蛋
// 100层楼扔2个鸡蛋，类似于平均分组的贪心，分成x组，每组包含x,x-1,x-2,x-3....1层，这样能均分第一个鸡蛋碎不碎之后的总期望（都是x），因此x+x^2/2>100 解得x=14
// 通用解：dp[n][k]表示n层楼 k个鸡蛋需要的最少次数，则 dp[n][k] = Min(Max(dp[n-i][k],dp[i][k-1])) 0<i<n;
// 上面这种dp的复杂度还是太高，观察到比较max的两个值随着i的增大一个单调递增一个单调递减（鸡蛋相同，楼层多的话肯定需要次数不会变少），因此可以用二分优化
// 还有另一种dp思路，dp[k][m]表示m次操作，k个鸡蛋能确定的最少层数 dp[k][m] = dp[k][m - 1] + dp[k - 1][m - 1] + 1;
// 我们在dp[k-1][t-1] + 1 层丢鸡蛋，如果鸡蛋碎了，那么上面任意多层都可以被排除，这是最好情况，但我们需要考虑最坏情况
// 如果鸡蛋没碎，我们首先排除了该层以下的 dp[k-1][t-1] 层楼，此时我们还有 k 个蛋和 t-1 步，那么我们去该层以上的楼层继续测得 dp[k][t-1] 层楼。因此这种情况下，我们总共可以求解 dp[k-1][t-1] + dp[k][t-1] + 1 层楼。
// 为什么在dp[k-1][t-1] + 1 层丢鸡蛋呢？ 如果在低楼层扔鸡蛋，那么dp得到的不是“最大”可以测量楼层数，如果在高楼层扔，那假设碎了，剩下的层数大于dp[k-1][t-1]无法保证能求出层数。（我觉得这个是最难最难想到的地方，贪心+动规）
public class ThrowEgg {
    // 普通动规
    public int superEggDrop(int K, int N) {
        int[][] middleResults = new int[K + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            middleResults[1][i] = i; // only one egg
            middleResults[0][i] = 0; // 0个蛋 i层楼需要0次
        }
        for (int i = 1; i <= K; i++) {
            middleResults[i][0] = 0; // i个蛋 0层楼也是0次
        }

        for (int k = 2; k <= K; k++) { // start from two egg
            for (int n = 1; n <= N; n++) {
                int tMinDrop = N * N;
                for (int x = 1; x <= n; x++) {
                    tMinDrop = Math.min(tMinDrop, 1 + Math.max(middleResults[k - 1][x - 1], middleResults[k][n - x]));
                }
                middleResults[k][n] = tMinDrop;
            }
        }

        return middleResults[K][N];
    }

    // 二分动规
    public int superEggDrop2(int K, int N) {
        int[][] middleResults = new int[K + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            middleResults[1][i] = i; // only one egg
            middleResults[0][i] = 0; // 0个蛋 i层楼需要0次
        }
        for (int i = 1; i <= K; i++) {
            middleResults[i][0] = 0; // i个蛋 0层楼也是0次
        }

        for (int k = 2; k <= K; k++) {
            for (int n = 1; n <= N; n++) {
                int tMinDrop = N * N;
                int left=1,right = n;
                while(left<right) {
                    int mid = (left+right)/2;
                    if(middleResults[k - 1][ mid- 1]>= middleResults[k][n - mid]) right=mid;
                    else left=mid+1;
                }
                // 两边值不一定相等，所以需要比较两边
                middleResults[k][n] = 1 + Math.max(middleResults[k - 1][left - 1], middleResults[k][n - left]);
            }
        }

        return middleResults[K][N];
    }


    // 优化版本： 贪心+动规
    public int superEggDrop3(int K, int N) {
        int[][] dp = new int[K + 1][N + 1];
        for (int m = 1; m <= N; m++) {
            dp[0][m] = 0; // zero egg
            for (int k = 1; k <= K; k++) {
                dp[k][m] = dp[k][m - 1] + dp[k - 1][m - 1] + 1;
                if (dp[k][m] >= N) {
                    return m;
                }
            }
        }
        return N;
    }
}
