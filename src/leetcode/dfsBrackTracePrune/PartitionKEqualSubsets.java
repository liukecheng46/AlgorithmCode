package leetcode.dfsBrackTracePrune;

import java.util.Arrays;
import java.util.Comparator;

// 划分为k个相等的子集
//桶和数字两种dfs方式及分析： https://mp.weixin.qq.com/s?__biz=MzAxODQxMDM0Mw==&mid=2247490981&idx=1&sn=db17864d2f6f189d63e051e434c05460&scene=21#wechat_redirect
public class PartitionKEqualSubsets {
    int sum;

    public boolean PartitionKSubsets(int[] nums, int k) {
        //将数组降序排列
        //Comparator.reverseOrder() 只能对Integer类型使用，所以用流操作先将int转化为Integer再转化回来
        nums = Arrays.stream(nums)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
        sum = Arrays.stream(nums).sum();
        //不能整除 直接返回
        if (sum % k != 0) return false;
        int target = sum / k;
        //最大的数比平均数大 直接返回
        if (nums[0] > target) return false;
        int[] sumList = new int[k];
        return dfs(nums, 0, target, sumList);
    }


    //以数字的角度进行dfs
    public boolean dfs(int[] nums, int i, int target, int[] sumList) {
        for (int n = 0; n < sumList.length; n++) {
            if (sumList[n] + nums[i] <= target) {
                sumList[n] += nums[i];
                //当dfs完nums中所有数时，sumlist中有不等于target的数则返回false
                if (i == nums.length - 1) {
                    for (int sum : sumList) {
                        if (sum != target) return false;
                    }
                }
                //当dfs完nums中所有数时 且sumlist中数都等于target时返回true
                if (i == nums.length - 1) return true;
                if (dfs(nums, i + 1, target, sumList)) return true;
                sumList[n] -= nums[i];
            }
        }
        //都不符合 return false
        return false;
    }

    // 从桶的角度，因为k比n小，所以k^n 比n^k小，即桶的角度会比数字的角度dfs快很多
    boolean backtrack(int k, int bucket,
                      int[] nums, int start, boolean[] used, int target) {
        // base case
        if (k == 0) {
            // 所有桶都被装满了，而且 nums 一定全部用完了
            // 因为 target == sum / k
            return true;
        }
        if (bucket == target) {
            // 装满了当前桶，递归穷举下一个桶的选择
            // 让下一个桶从 nums[0] 开始选数字
            return backtrack(k - 1, 0 ,nums, 0, used, target);
        }

        // 从 start 开始向后探查有效的 nums[i] 装入当前桶
        for (int i = start; i < nums.length; i++) {
            // 剪枝
            if (used[i]) {
                // nums[i] 已经被装入别的桶中
                continue;
            }
            if (nums[i] + bucket > target) {
                // 当前桶装不下 nums[i]
                continue;
            }
            // 做选择，将 nums[i] 装入当前桶中
            used[i] = true;
            bucket += nums[i];
            // 递归穷举下一个数字是否装入当前桶
            if (backtrack(k, bucket, nums, i + 1, used, target)) {
                return true;
            }
            // 撤销选择
            used[i] = false;
            bucket -= nums[i];
        }
        // 穷举了所有数字，都无法装满当前桶
        return false;
    }
}
