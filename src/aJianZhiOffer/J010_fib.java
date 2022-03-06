package aJianZhiOffer;

// 斐波那契
public class J010_fib {
    public int fib(int n) {
        if(n==0) return 0;
        int a =1, b=1;
        while(n-->1) {
            int temp = (a%1000000007+b%1000000007)%1000000007;
            a=b;
            b=temp;
        }
        return a;
    }
}
