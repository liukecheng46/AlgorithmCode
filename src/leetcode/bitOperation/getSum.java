package leetcode.bitOperation;

//位运算实现两数之和
public class getSum {
    public int getSum(int a, int b) {
        int flag = 0;
        int res =0;
        // 第一是因为哪个数大不清楚，第二是因为还有负数（补码前面全为1），所以要遍历32位（int类型长度）
        for (int i = 0; i < 32; i++) {
            int u1 = (a>>i) & 1 ;
            int u2 = (b>>i) & 1 ;
            int sum = u1+u2;
            sum+=flag;
            flag = sum/2;
            res|=(sum%2)<<i;
        }
        return res;
    }
}
