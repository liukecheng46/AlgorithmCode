package leetcode.ClassicProblem;


// 两数相除 不能使用乘除mod https://leetcode-cn.com/problems/divide-two-integers/solution/po-su-de-xiang-fa-mei-you-wei-yun-suan-mei-you-yi-/
// 一个一个减的方法太慢了，可以每次把被除数乘2，一直到比除数大的时候，把除数减去上一个数，再进行下一次dfs（比如11除3,3*2<11,3*4>11，那么把答案加2，然后11-3*2=5，把5作为除数进入下一个递归函数
// 还有一种其实有点怪的方法，在被除数范围进行二分，check函数比较mid*x和y的大小（用快速乘法计算mid*x）
public class TwoDivide {
    // 解法一
    public int divide(int dividend, int divisor) {
         int a = Math.abs(dividend),b = Math.abs(divisor);
         int res = dfs(a,b);
         return (dividend>0) == (divisor>0)? res:-res;
    }

    public int dfs(int dividend,int divisor) {
        if(dividend<divisor) return 0;
        int cur = divisor;
        int count=1;
        while(cur<=dividend) {
            cur+=cur;
            count+=count;
        }
        int res=(count>>1)+dfs(dividend-(cur>>1),divisor);
        return res;
    }

    // 解法2 二分+快速乘法（快速乘法和快速幂本质相同，一个是用加法实现乘法，一个是用乘法实现幂）
    public int divide2(int dividend, int divisor) {
        int left=0,right=dividend;
        while(left<right) {
            int mid = left+right>>1;
            if(check(mid,dividend,divisor)) right=mid;
            else left=mid+1;
        }
        return left-1;
    }

    public boolean check(int cur,int dividend,int divisor) {
        return qucikMul(cur,divisor)>dividend;
    }

    // 快速乘法
    public int qucikMul(int x,int y) {
        int cur = x;
        int res= 0;
        while(y>0) {
            if((y&1)==1) res+=cur;
            cur+=cur;
            y=y>>1;
        }
        return res;
    }

    //快速幂
    public int quickPow(int x,int y) {
        int cur=x;
        int res=1;
        while(y>0) {
            if((y&1)==1) res*=cur;
            cur*=cur;
            y=y>>1;
        }
        return res;
    }
}
