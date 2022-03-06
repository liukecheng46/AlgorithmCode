package aJianZhiOffer;

public class J065_add {
    public int add(int a, int b) {
        int flag = 0;
        int res =0;
        // 第一是因为哪个数大不清楚，第二是因为还有负数（补码前面全为1），所以要遍历32位（int类型长度）
        for (int i = 0; i < 32; i++) {
            int u1 = (a>>i) & 1 ;
            int u2 = (b>>i) & 1 ;
            // 注意这里，不能用+法，只能用异或和且来表示进位和当前位 todo
            int cur = u1^u2^flag;
            flag = (u1&u2) |((u1^u2)&flag);
            res|=cur<<i;
        }
        return res;
    }
}
