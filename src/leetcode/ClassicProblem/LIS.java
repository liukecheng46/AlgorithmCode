package leetcode.ClassicProblem;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// 最长递增子序列
// 掌握o(n^2)的dp是不够滴，需要掌握的是O（nlogn）的贪心二分dp
// 原始dp[i]表示以i下标为结尾的最长递增序列
// 改进的tails[i]表示长度为i的所有递增序列的结尾的最小值,扫一遍数组就可以完成所有状态转移，
// 区间中存在 tails[i] > nums[k] ： 将第一个满足 tails[i] > nums[k] 执行 tails[i] = nums[k] ；因为更小的 nums[k] 后更可能接一个比它大的数字（前面分析过）。
// 区间中不存在 tails[i] > nums[k] ： 意味着 nums[k] 可以接在前面所有长度的子序列之后，因此肯定是接到最长的后面（长度为 res），新子序列长度为 res + 1。
// tails数组结合贪心和dp思想，用nums[i]更新tails使用二分,这种方法得到的不一定是真实的最长递增序列，但是长度一定相同
// https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/dong-tai-gui-hua-er-fen-cha-zhao-tan-xin-suan-fa-p/
public class LIS {
    //二分做法
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] tails = new int[n];
        int LISLength = 0;
        for(int x:nums) {
            int left = 0, right = LISLength;
            while(left<right) {
                int mid = (left+right)>>1;
                if(tails[mid]>=x) right = mid;
                else left=mid+1;
            }
            //将第一个满足tails[i]>x的数更新为x,包含了下述两种特殊情况
            //如果x比tail中所有数都小，更新第一个，此时right=0
            //如果x比tail中所有数都大，更新最后一个，此时right=LISLengthm
            tails[right] = x;
            //如果都大的话，意味着LISLength可以加一
            if(right == LISLength) LISLength++;
        }
        return LISLength;
    }

    // 最长斐波那契递增子序列 https://leetcode-cn.com/problems/length-of-longest-fibonacci-subsequence/ todo
    // 和LIS O(N^2)的做法很像
    // dp[i][j]表示以i和j为最后两项的斐波那契子序列的最大长度。 对于每个j，遍历找i再找是否存在k（dp[k][i]），并且取这些的最大值加一
    // 朴素思想是O(N^3)会超时，用哈希表优化至n^2
    public int lenLongestFibSubseq(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.put(arr[i], i);
        }

        for (int k = 2; k < n; k++) {
            for (int j = k - 1; j > 0; j--) {
                int i_value = arr[k] - arr[j];
                if (map.containsKey(i_value)) {
                    int i = map.get(i_value);
                    if (i < j) {
                        dp[j][k] = Math.max(dp[j][k], dp[i][j] + 1);
                        // 因为dp[j][k]一开始定义的时候少了前2项的长度(i,j,k本来是三项，但是dp[j][k]会是1)，所以需要加2.
                        ans = Math.max(ans, dp[j][k] + 2);
                    }
                }
            }
        }

        return ans;
    }

    // 俄罗斯套娃信封问题 https://leetcode-cn.com/problems/russian-doll-envelopes/
    // 二维最长上升子序列问题
    // 先将一个维度排序（相同时第二个维度按照降序排列，避免特殊情况），然后对第二个维度求LIS
    public int maxEnvelopes(int[][] es) {
        int n = es.length;
        if (n == 0) return n;
        // 由于我们使用了 g 记录高度，因此这里只需将 w 从小到达排序即可
        Arrays.sort(es, (a, b)->a[0] - b[0]);
        // f(i) 为考虑前 i 个物品，并以第 i 个物品为结尾的最大值
        int[] f = new int[n];
        // g(i) 记录的是长度为 i 的最长上升子序列的最小「信封高度」
        int[] g = new int[n];
        // 因为要取 min，用一个足够大（不可能）的高度初始化
        Arrays.fill(g, Integer.MAX_VALUE);
        g[0] = 0;
        int ans = 1;
        for (int i = 0, j = 0, len = 1; i < n; i++) {
            // 对于 w 相同的数据，不更新 g 数组
            if (es[i][0] != es[j][0]) {
                // 限制 j 不能越过 i，确保 g 数组中只会出现第 i 个信封前的「历史信封」
                while (j < i) {
                    int prev = f[j], cur = es[j][1];
                    if (prev == len) {
                        // 与当前长度一致了，说明上升序列多增加一位
                        g[len++] = cur;
                    } else {
                        // 始终保留最小的「信封高度」，这样可以确保有更多的信封可以与其行程上升序列
                        // 举例：同样是上升长度为 5 的序列，保留最小高度为 5 记录（而不是保留任意的，比如 10），这样之后高度为 7 8 9 的信封都能形成序列；
                        g[prev] = Math.min(g[prev], cur);
                    }
                    j++;
                }
            }

            // 二分过程
            // g[i] 代表的是上升子序列长度为 i 的「最小信封高度」
            int l = 0, r = len;
            while (l < r) {
                int mid = l + r >> 1;
                // 令 check 条件为 es[i][1] <= g[mid]（代表 w 和 h 都严格小于当前信封）
                // 这样我们找到的就是满足条件，最靠近数组中心点的数据（也就是满足 check 条件的最大下标）
                // 对应回 g[] 数组的含义，其实就是找到 w 和 h 都满足条件的最大上升长度
                if (es[i][1] <= g[mid]) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            // 更新 f[i] 与答案
            f[i] = r;
            ans = Math.max(ans, f[i]);
        }
        return ans;
    }
}
