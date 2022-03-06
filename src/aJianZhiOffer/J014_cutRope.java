package aJianZhiOffer;

public class J014_cutRope {
    // 记忆化递归-> dp -> 数学证明最优
    public int cuttingRope(int n) {
        int dp[] = new int[n+1];
        dp[1]=1;
        dp[0]=1;
        for(int i=1;i<=n;i++) {
            for(int j=1;j<i;j++) {
                // 这里少想到一个地方，不只是j*dp[i-1]，还要考虑它和j*(i-j)的大小，因为n=3有特例，3分了才等于2，不分却等于3，或者可以初始化3及以下的n，这里就可以不加这个条件
                dp[i] =Math.max(dp[i],Math.max(j*(i-j),j*dp[i-j]));
            }
        }
        return dp[n];
    }

    // 数学解法：尽可能以3来划分的时候，最优（结合快速幂）
    public int cuttingRope2(int n) {
        if (n <= 3) {
            return n - 1;
        }
        int a = n / 3;
        int b = n % 3;
        if (b == 0) {
            return (int) pow(3, a) % 1000000007;
        } else if (b == 1) {
            return (int) ((pow(3, a - 1) * 4) % 1000000007);//注意，int要包裹在最外层
        } else {
            return (int) ((pow(3, a) * 2) % 1000000007);//成功，注意int要放在最外层
        }
    }

    public long pow(long x, int n) {
        long temp = x;
        long result = 1;
        while (n > 0) {
            if ((n & 1) == 1) {
                result = (result * temp) % 1000000007;
            }
            temp = (temp * temp) % 1000000007;
            n = n >> 1;
        }
        return result;
    }
}
