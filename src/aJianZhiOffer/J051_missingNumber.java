package aJianZhiOffer;

// 原地哈希，因为是找缺失的，所以哈希后遍历
public class J051_missingNumber {
    public int missingNumber(int[] nums) {
        for(int i=0;i<nums.length;i++) {
            while(nums[i]<nums.length &&nums[nums[i]]!=nums[i]) {
                int temp = nums[nums[i]];
                nums[nums[i]] =nums[i];
                nums[i] = temp;
            }
        }

        for(int i=0;i<nums.length;i++) {
            if(nums[i]!=i) return i;
        }
        return nums.length;
    }
}
