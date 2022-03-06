package aJianZhiOffer;

public class J056_singleNumber {
    public int[] singleNumbers(int[] nums) {
        int xor =0;
        for(int num:nums) {
            xor^=num;
        }
        int mark=0;
        for(int i=0;i<32;i++) {
            if(((xor>>i)&1)==1) {
                mark=i;
                break;
            }
        }
        int a=0,b=0;
        for(int num:nums){
            if(((num>>mark)&1)==1) a^=num;
            else b^=num;
        }
        return new int[] {a,b};
    }
}
