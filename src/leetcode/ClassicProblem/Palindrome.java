package leetcode.ClassicProblem;

// 回文相关
public class Palindrome {
    // 最长回文串 经典问题 https://leetcode-cn.com/problems/longest-palindromic-substring/
    // 按长度从小到大dp （区间dp）
    public String longestPalindrome(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int max = 0;
        int maxBegin = 0;
        //长度为1和为2时初始化
        for (int i = 0; i < n; i++) dp[i][i] = true;
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
            if (dp[i][i + 1]) {
                max = 2;
                maxBegin = i;
            }
        }

        for (int i = 3; i <= n; i++) {
            for (int j = 0; j <= n - i; j++) {
                dp[j][j + i - 1] = s.charAt(j) == s.charAt(j + i - 1) ? dp[j + 1][j + i - 2] : false;
                if (dp[j][j + i - 1] && i > max) {
                    max = i;
                    maxBegin = j;
                }
            }
        }
        return max == 0 ? s.substring(0, 1) : s.substring(maxBegin, maxBegin + max);
    }

    // 最长回文串 解法2： 中心扩散法，枚举每一个字符为中心向两边扩散 也是O（n^2），但是空间复杂度为1、
    // 这个其实算是最好想到的方法，就是中心点往左右两边如何确定长度
    public String longestPalindrome3(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = 0;
            // 如果当前值和下一个值相同，那么以这两个数为起点开始扩散（长度为2的回文串的情况）
            if (i < s.length() - 1 && s.charAt(i) == s.charAt(i + 1)) len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            //
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        return right - left - 1;
    }

    // 最长回文串 解法3： O（n）复杂度的马拉车算法
    // 本质是中心扩散算法，但是存储并利用了之前所有中心点的回文长度
    public String longestPalindrome4(String s) {
        int n = s.length();
        StringBuffer t = new StringBuffer("$#");
        // 解决回文串奇数长度和偶数长度的问题，处理方式是在所有的相邻字符中间插入 # ，这样可以保证所有找到的回文串都是奇数长度的
        for (int i = 0; i < n; ++i)
            t.append(s.charAt(i)).append('#');

        t.append('!');// 字符串边界外，再随便放两个不一样的字符，防越界。e.g. aba → $#a#b#a#!
        n = t.length();

        int[] armLen = new int[n];
        int j = 0, right = 0;// 维护「当前最大的回文的右端点right」以及这个回文右端点对应的回文中心
        int iMax = 0, maxArmLen = 0;//最长回文串的回文中心和臂长

        for (int i = 2; i < n - 2; ++i) {
            // i 被包含在当前最大回文子串内(right与当前点的距离, i关于j对称的点的f值)，不被包含(0)
            // 这里将 right−i 和 f[对称点] 取小，是先要保证这个回文串在当前最大回文串内。
            armLen[i] = i <= right ? Math.min(right - i, armLen[j * 2 - i]) : 0;// 初始化
            while (t.charAt(i + armLen[i] + 1) == t.charAt(i - armLen[i] - 1))// 中心拓展
                ++armLen[i];
            if (i + armLen[i] > right) {// 动态维护 iMax 和 rMax
                j = i;
                right = i + armLen[i];
            }
            if (armLen[i] > maxArmLen) {// 记录当前最长回文串
                iMax = i;
                maxArmLen = armLen[i];
            }
        }

        // 去掉# 返回答案
        StringBuffer ans = new StringBuffer();
        for (int i = iMax - maxArmLen; i < iMax + maxArmLen; ++i)
            if (t.charAt(i) != '#')
                ans.append(t.charAt(i));

        return ans.toString();
    }


    // 最长回文子序列 经典问题 https://leetcode-cn.com/problems/longest-palindromic-subsequence/
    // 和最长回文子串是相同的区间dp，只是状态转移方程稍有不同，并且dp[i][j]代表的是i-j之间的最长回文子序列长度
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        char[] cs = s.toCharArray();
        int[][] f = new int[n][n];
        for (int len = 1; len <= n; len++) {
            for (int l = 0; l + len - 1 < n; l++) {
                int r = l + len - 1;
                if (len == 1) {
                    f[l][r] = 1;
                } else if (len == 2) {
                    f[l][r] = cs[l] == cs[r] ? 2 : 1;
                } else {
                    // 相同的话 加2
                    if(cs[l] == cs[r]) f[l][r] = f[l + 1][r - 1]+2;
                    // 不同的话，取两个半闭半开的最大值，这里是最重要的地方 todo
                    else f[l][r] = Math.max(f[l + 1][r], f[l][r - 1]);
                }
            }
        }
        return f[0][n - 1];
    }

    // 最长回文子序列还有一种巧妙的解法： 将s和s的reverse求LCS
    public int longestPalindromeSubseq2(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        return LCS(s, rev);
    }
    public int LCS(String s, String rev) {
        char[] sc = s.toCharArray();
        char[] rc = rev.toCharArray();
        int len = sc.length;
        int[][] dp = new int[len + 1][len + 1];
        for(int i = 1; i < len + 1; i ++) {
            for(int j = 1; j < len + 1; j ++) {
                if(sc[i - 1] == rc[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[len][len];
    }

    // 最短回文串 https://leetcode-cn.com/problems/shortest-palindrome/
    // 找最长的前缀回文串
    // 必须要是O（n）复杂度的算法
    // 可以使用字符串哈希算法，枚举每一个字符串，看反向和正向哈希值是否相同，相同则是回文串
    public String shortestPalindrome(String s) {
        int n = s.length();
        int base = 131, mod = 1000000007;
        int left = 0, right = 0, mul = 1;
        int best = -1;
        for (int i = 0; i < n; ++i) {
            left = (int) (((long) left * base + s.charAt(i)) % mod);
            right = (int) ((right + (long) mul * s.charAt(i)) % mod);
            if (left == right) {
                best = i;
            }
            mul = (int) ((long) mul * base % mod);
        }
        String add = (best == n - 1 ? "" : s.substring(best + 1));
        StringBuffer ans = new StringBuffer(add).reverse();
        ans.append(s);
        return ans.toString();
    }


}
