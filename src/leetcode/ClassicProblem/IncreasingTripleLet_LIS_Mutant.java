package leetcode.ClassicProblem;

// 三元上升序列 LIS（最长递增子序列）的变种
// 可以使用贪心，双向遍历（接雨水），也可以使用LIS经典的贪心二分dp
// 非常好的一道题，很多经典问题的思路都可以解决
//https://leetcode-cn.com/problems/increasing-triplet-subsequence/solution/
public class IncreasingTripleLet_LIS_Mutant {
    public boolean increasingTriplet(int[] nums) {
        return pureGreedySolution(nums);
    }

    // 双向扫描，左边找min右边找max，接雨水是两边都找max
    // 双向扫两遍，最后判断再扫一遍，一共三遍
    public boolean biDirectScan(int[] nums) {
        int n = nums.length;
        int[] leftMin = new int[n];
        int cur_min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            cur_min = Math.min(nums[i], cur_min);
            leftMin[i] = cur_min;
        }

        int[] rightMax = new int[n];
        int cur_max = Integer.MIN_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            cur_max = Math.max(nums[i], cur_max);
            rightMax[i] = cur_max;
        }

        for (int i = 0; i < n; i++) {
            if (leftMin[i] < nums[i] && rightMax[i] > nums[i]) return true;
        }
        return false;
    }

    // 纯贪心，维护first和second变量，在扫一遍的过程中进行判断更新
    // 贪心思想和LIS的二分贪心是一样的，只是特例化 提出前两个（求是否大于等于3），降低复杂度
    // 只需要扫一遍
    public boolean pureGreedySolution(int[] nums) {
        if (nums.length < 3) return false;
        int first = nums[0], second = Integer.MAX_VALUE;
        // 小于second和first如何更新比较抽象，自己想的时候感觉有问题，但是细想确实没问题
        for (int x : nums) {
            if (x > second) return true;
                //比first小就更新第一个
            else if (x <= first) {
                first = x;
            }
            //在first和second之间就更新second
            else second = x;
        }
        return false;
    }

    // LIS的贪心二分解法，判断最长递增序列是否大于3
    // O(nlogn）
    public boolean LISGreedy(int[] nums) {
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
        return LISLength>=3;
    }
}
