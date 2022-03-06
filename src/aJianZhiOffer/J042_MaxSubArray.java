package aJianZhiOffer;

public class J042_MaxSubArray {
    public int maxSubArray(int[] nums) {
        int sum = Integer.MIN_VALUE;
        int max = Integer.MIN_VALUE;
        for(int num:nums) {
            sum = Math.max(sum+num,num);
            max  = Math.max(max,sum);
        }
        return max;
    }
}
