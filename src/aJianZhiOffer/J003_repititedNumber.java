package aJianZhiOffer;

// 数组中重复数字
// O（n）解法哈希表
// 需要掌握O（1）解法原地哈希
public class J003_repititedNumber {
    // 原地哈希
    public int findRepeatNumber(int[] nums) {
        for(int i=0;i<nums.length;i++) {
            while(nums[i]!=nums[nums[i]]) {
                // 注意赋值顺序
                int temp = nums[nums[i]];
                nums[nums[i]] = nums[i];
                nums[i] = temp;
            }
            // 可能正好i=nums[i]=nums[nums[i]],但此时该数字并不重复，这里需要注意
            if(i!=nums[i]) return nums[i];
        }
        return -1;
    }

    // 还有一题类似的 找没有出现的最小正数 https://leetcode-cn.com/problems/first-missing-positive/
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int temp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = temp;
            }
        }
        for (int i = 0; i < n; ++i) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }
}
