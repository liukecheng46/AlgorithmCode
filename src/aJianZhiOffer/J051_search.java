package aJianZhiOffer;

public class J051_search {
    public int search(int[] nums, int target) {
        int left=0,right=nums.length-1;
        while(left<right) {
            int mid =  (left+right)/2;
            if(nums[mid]>=target) right=mid;
            else left=mid+1;
        }
        int count=0;
        while(left<nums.length) {
            if(nums[left]==target) {
                count++;
                left++;
            } else {
                break;
            }
        }
        return count;
    }
}
