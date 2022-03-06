package aJianZhiOffer;

// 摩尔法
// 可以拓展到1/3 或者1/N
// 1/N做法：维护一个大小为N的map，若map填满后新的数和map里所有数都不同，那么所有数都减一，若新数等于map里某个数，那该数加一，其他数不变，若map某数count为0则delete，若map大小小于N下一个新数直接放入
public class J039_moreThanHalf {
    public int majorityElement(int[] nums) {
        int mj = nums[0];
        int count = 1;
        for(int i=1;i<nums.length;i++) {
            if(nums[i]==mj) count++;
            if(nums[i]!=mj) count--;
            if(count<0) {
                mj = nums[i];
                count =1;
            }
        }
        return mj;
    }
}
