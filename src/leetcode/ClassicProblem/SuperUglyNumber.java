package leetcode.ClassicProblem;

import java.util.PriorityQueue;

// 超级丑数，2，3,5拓展到任意质数
// 动态规划结合优先队列
// https://leetcode-cn.com/problems/super-ugly-number/
public class SuperUglyNumber {
    public int nthSuperUglyNumber(int n, int[] primes) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        // 丑数递增数组
        int[] res = new int[n];
        res[0] = 1;
        for (int i = 0; i < primes.length; i++) {
            //{a,b,c} a代表值，b代表第几个素数，c代表这个素食当前对应丑数的索引 res[c]
            pq.offer(new int[]{primes[i], i, 0});
        }

        for (int i = 1; i < n; ) {
            int[] cur = pq.poll();
            int x = cur[1], index = cur[2];
            //去重，相当于队列中的相同数 每个对应primes的索引都要加一，但是res只加一次，很关键
            if (cur[0] != res[i - 1]) {
                res[i] = cur[0];
                i++;
            }
            pq.offer(new int[]{res[index + 1] * primes[x], x, index + 1});

        }
        return res[n - 1];
    }


    // 当数据非常大时，丑数还有另一种对答案范围二分的解法
    // 丑数3 https://leetcode-cn.com/problems/ugly-number-iii/
    // 还有一道前置题目：第N个神奇数字 https://leetcode-cn.com/problems/nth-magical-number/solution/di-n-ge-shen-qi-shu-zi-by-leetcode/
    // 第N个神奇数字：被两个数A和B整除的第N个数，N非常大，所以我们考虑对于任意一个数X，f(x)表示小于等于这个数x的树中能被这两个数整除的数的个数，f(x) = x/A +x/B -x/(A*B),这是个单调递增的函数，所以可以二分答案范围求解
    public int nthMagicalNumber(int N, int A, int B) {
        int MOD = 1_000_000_007;
        int L = A / gcd(A, B) * B;

        long lo = 0;
        long hi = (long) 1e15;
        while (lo < hi) {
            long mi = lo + (hi - lo) / 2;
            if (mi / A + mi / B - mi / L < N)
                lo = mi + 1;
            else
                hi = mi;
        }

        return (int) (lo % MOD);
    }

    public int gcd(int x, int y) {
        if (x == 0) return y;
        return gcd(y % x, x);
    }

    // 丑数3 更进一步，被三个数整除的第N个数
    // 利用容斥原理，可求上面提到的f(x)在三个数情况下的计算公式 图解：https://leetcode-cn.com/problems/ugly-number-iii/solution/pyrong-chi-yuan-li-er-fen-zhou-qi-you-hu-8aiq/
    public int nthUglyNumber(int n, int a, int b, int c) {
        long ans = 0;
        long l = 0, r = (long) Math.min(a, Math.min(b, c)) * n;
        long ab = this.lcm(a, b);
        long ac = this.lcm(a, c);
        long bc = this.lcm(b, c);
        long abc = this.lcm(b, ac);
        while (l <= r) {
            long m = l + ((r - l) >> 1);
            long N = m / a + m / b + m / c - m / ab - m / ac - m / bc + m / abc;
            if (N < n) {
                l = m + 1;
                ans = l;
            } else {
                r = m - 1;
            }
        }
        return (int) ans;
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }
}
