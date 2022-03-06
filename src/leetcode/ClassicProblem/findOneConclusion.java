package leetcode.ClassicProblem;

// 其他数出现N次，找到出现一次的数 总结
public class findOneConclusion {
    // 只出现一次的数字1 一个数一次 其他数都是两次
    // 直接异或就行 代码略


    // 只出现一次的数字2 一个数一次 其他数都是三次
    // 这种有通解，其他数都出现N次的话，统计每一二进制位出现次数得到除以N的余数，合起来的二进制数就是答案
    public int singleNumber(int[] nums) {
        int[] cnt = new int[32];
        for (int x : nums) {
            for (int i = 0; i < 32; i++) {
                if (((x >> i) & 1) == 1) {
                    cnt[i]++;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if ((cnt[i] % 3 & 1) == 1) {
                ans |= (1 << i);
            }
        }
        return ans;
    }

    // 只出现一次的数字3 两个数一次 其他数两次
    // 先异或一遍，得到两个数的异或，再找到任意一位是1（比如用x&-x：保留二进制下最后出现的1的位置，其余位置置0），遍历数组，这位是1的异或，不是1的异或，最后结果就是这两个数
    class Solution {
        public int[] singleNumber(int[] nums) {
            int sum = 0;
            for (int i : nums) sum ^= i;
            int k = -1;
            for (int i = 31; i >= 0 && k == -1; i--) {
                if (((sum >> i) & 1) == 1) k = i;
            }
            int[] ans = new int[2];
            for (int i : nums) {
                if (((i >> k) & 1) == 1) ans[1] ^= i;
                else ans[0] ^= i;
            }
            return ans;
        }
    }

    // 使用x&-x
    public int[] singleNumber2(int[] nums) {
        int xorsum = 0;
        for (int num : nums) {
            xorsum ^= num;
        }
        // 防止溢出
        int lsb = (xorsum == Integer.MIN_VALUE ? xorsum : xorsum & (-xorsum));
        int type1 = 0, type2 = 0;
        for (int num : nums) {
            if ((num & lsb) != 0) {
                type1 ^= num;
            } else {
                type2 ^= num;
            }
        }
        return new int[]{type1, type2};
    }

    // 有序数组中的单一元素 https://leetcode-cn.com/problems/single-element-in-a-sorted-array/
    // 其他相同元素两两出现，只有一个元素出现一次，找到这个元素（不有序也没关系，这里题目提到的有序是告诉你出现两次的元素都是两两出现）
    // o（logn）二分 ： 对于出现一次的数 他左边的数num[i]=num[i+1] 那么i是偶数，他右边的数num[i]=num[i+1]，那么i是奇数。
    // 所以对于mid，如果mid 是偶数，则比较 nums[mid] 和 nums[mid+1] 是否相等；
    // 如果 mid 是奇数，则比较nums[mid−1] 和 nums[mid] 是否相等。
    // 变形二分的模板要注意
    public int singleNonDuplicate(int[] nums) {
        int n = nums.length;
        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + r >> 1;
            if (mid % 2 == 0) {
                if (mid + 1 < n && nums[mid] == nums[mid + 1]) l = mid + 1;
                else r = mid;
            } else {
                if (mid - 1 >= 0 && nums[mid - 1] == nums[mid]) l = mid + 1;
                else r = mid;
            }
        }
        return nums[r];
    }
}
