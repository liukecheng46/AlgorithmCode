package leetcode.ClassicProblem;

import java.util.Arrays;

// 差分数组运用总结
// 最经典的是 370 范围加
// 还有一道差分性质运用题目 看到挺多笔试面试考了 https://leetcode-cn.com/problems/minimum-moves-to-equal-array-elements/

public class DiffArray {
    // 范围加 https://baihuqian.github.io/2018-08-16-range-addition/
    // 肯定不能每次操作都操作原数组 O（n^2）会超时
    // 差分数组d的性质是，如果把(L,R)区间的数都增加k，那么d[L]+K, 如果R小于d.len-1，还会有d[R+1]-K,其他都不变，所以只需要2次修改就可表示一次操作
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] arr = new int[length];
        Arrays.fill(arr, 0);

        for(int i = 0; i < updates.length; i++) {
            arr[updates[i][0]] += updates[i][2];
            if(updates[i][1] < length - 1)
                arr[updates[i][1] + 1] -= updates[i][2];
        }
        for(int i = 1; i < length; i++) {
            arr[i] += arr[i - 1];
        }
        return arr;
    }

    // 形成目标数组的子数组最少增加次数 https://leetcode-cn.com/problems/minimum-number-of-increments-on-subarrays-to-form-a-target-array/
    // 从初始都是0 到目标数组的最少范围加1次数
    // 可以反向思考，从目标数组到全为0数组的最少次数，先求目标数组的差分数组，因为数组均大于0，则可得差分数组的和也是大于0的（等于原数组最后一个数）
    // 这样我们每次把差分数组一个大于1的和小于一的数匹配，一个减一 一个加一（类似于抵消）。 因为差分数组和大于0，所以和负数抵消后剩下的数一定大于0，我们把这些数继续进行操作，右边界选择为d.len，这样就不用找负数而是可以直接抵消
    // 综上，上述过程就等于找差分数组中大于0的数的累加和
    // 这道题还有很多变种，比如一开始全为n，那么就等于把目标差分数组转化为[n,0,0,...0]的最小次数，同理，等于差分数组正数和减去n
    public int minNumberOperations(int[] target) {
        int n = target.length;
        int ans = target[0];
        for (int i = 1; i < n; ++i) {
            ans += Math.max(target[i] - target[i - 1], 0);
        }
        return ans;
    }

    // 还有一道变种题目 https://www.codeleading.com/article/60334945018/
    // 每次可以区间加一或者减一，最少使所有数相等的操作是多少，且求最少次数情况下，最后的可能情况有多少种？


    // 最小操作次数使元素相等 https://leetcode-cn.com/problems/minimum-moves-to-equal-array-elements/
    // 这道题没用差分，但是用了类似前面的反向思考
    // 每次使n-1个元素加1，反向看就等于选择任意一个数-1，因此最少次数就等于所有数和数组中最小值的差值和
    public int minMoves(int[] nums) {
        int minNum = Arrays.stream(nums).min().getAsInt();
        int res = 0;
        for (int num : nums) {
            res += num - minNum;
        }
        return res;
    }


}
