package aJianZhiOffer;

//这题和数组覆盖逻辑 一起食用
public class J021_exchange {
    // 单指针
    public int[] exchange(int[] nums) {
        int n = nums.length;
        int left=0,right=0;
        while(right<n) {
            if( (nums[right]&1)==1) {
                int temp =nums[left];
                nums[left] = nums[right];
                nums[right]= temp;
                left++;
            }
            right++;
        }
        return nums;
    }

    // 双指针，会快一些（少了一些奇数和奇数交换的操作）
    public int[] exchange2(int[] nums) {
        int i = 0, j = nums.length - 1, tmp;
        while(i < j) {
            while(i < j && (nums[i] & 1) == 1) i++;
            while(i < j && (nums[j] & 1) == 0) j--;
            tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
        return nums;
    }

}
