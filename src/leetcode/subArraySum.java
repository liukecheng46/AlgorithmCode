package leetcode;

import java.util.Arrays;

public class subArraySum {
    public int subarraySum(int[] nums, int k) {
        Arrays.sort(nums);
        int left=0;
        int sum = 0;
        int res = 0;
        for(int right=0;right<nums.length;) {
            if(sum<k) {
                sum+= nums[++right];
            } else {
                if(sum ==k ) res++;
                sum-=nums[left++];
            }
        }
        return res;
    }
}
