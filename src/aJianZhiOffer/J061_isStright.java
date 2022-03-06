package aJianZhiOffer;

public class J061_isStright {
    public boolean isStraight(int[] nums) {
        int[] count = new int[14];
        for(int num:nums) {
            count[num]++;
        }
        int Gap = 0;
        int pre=1000;
        for(int i=1;i<14;i++) {
            if(count[i]>1) return false;
            if(count[i]==1) {
                Gap += Math.max(0,i-pre-1);
                pre = i;
            }
        }
        return count[0]>=Gap;
    }
}
