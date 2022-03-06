package aJianZhiOffer;

public class J016_QuickPow {
    public double myPow(double x, int n) {
        boolean flag=false;
        if(n<0) {
            flag = true;
            n=-n;
        }
        long b =n;
        double cur= x;
        double res= 1;
        while(b>0) {
            if((b&1)==1) res = res*cur;
            cur = cur*cur;
            b>>=1;
        }
        return flag?1/res:res;
    }
}
