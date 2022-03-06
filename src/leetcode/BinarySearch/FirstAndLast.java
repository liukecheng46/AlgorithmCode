package leetcode.BinarySearch;

// 二分查找第一个和最后一个位置 https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/
// 二分的两种模板 记牢 todo
// 二分总结还可以见notion笔记 https://www.notion.so/outbreaker/7062b1e572d5472e9180eaa73604146c
public class FirstAndLast {
    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[2];
        // 找第一个出现的位置
        int left=0,right=nums.length-1;
        if(nums.length==0) return new int[] {-1,-1};
        while(left<right) {
            int mid =(left+right)>>1;
            if(nums[mid]>=target) right=mid;
            else left = mid+1;
        }
        res[0] = nums[left]==target?left:-1;
        if(res[0]==-1) {
            res[1] =-1;
            return res;
        }
        // 找最后一个出现的位置
        left=0;right=nums.length-1;
        while(left<right) {
            int mid =(left+right+1)>>1;
            if(nums[mid]<=target) left=mid;
            else right = mid-1;
        }
        res[1] = left;
        return res;
    }
}
