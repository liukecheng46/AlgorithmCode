package leetcode.ClassicProblem;

// 正则表达式匹配 https://leetcode-cn.com/problems/regular-expression-matching/solution/dong-tai-gui-hua-jie-xi-liang-fen-zhong-bg4mb/
//  经典难题, 动态规划分很多种情况讨论，最难想到的是a*表示2个及以上a的情况如何表示 -> 左边去掉一个a，右边什么也不去掉
public class isMatch {
    public boolean isMatch(String s, String p) {
        //使用动态规划来做
        //获取s和p的长度
        int m = s.length(); int n = p.length();
        //定一个二维布尔数组,这里加1是为了冗余,防止数组越界异常
        /*
        其中 dp[i][j]代表s中第i个字符与p中第j个字符是否匹配
        */
        boolean[][] dp = new boolean[m+1][n+1];

        //规定默认匹配，所以0 0为true
        dp[0][0] = true;

        //这里考虑特殊情况 当s为空 p为'x*'这类正则时
        for (int i = 1; i <= n; i++) {
            /*
            因为p为'x*'这类正则，所以空字符串匹配也是true
            这时候会发现dp[0][2] 正好继承 dp[0][0]的布尔值
            也就是true
            后面不管出现几次'x*'都会继承到dp[0][0],也就是true
            但只要出现了不是 字母带* 的 就会出现继承false
            导致最后结果也为false
            */
            if(p.charAt(i-1)=='*'){
                dp[0][i] = dp[0][i-2];
            }
        }

        //开始匹配两个字符串
        //下面获取字符都减一是因为 i和j都是从1开始
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                //当p中的j位置的字符为字母时,判断s中对应的位置是不是相同的字母
                if(p.charAt(j-1)>='a' && p.charAt(j-1)<='z'){
                    if(s.charAt(i-1) == p.charAt(j-1)){
                        /*
                        匹配，所以dp[i][j]的布尔值应该由dp[i-1][j-1]定夺
                        比如 dp[0][0]为true s=aaa p=aa*
                        这时 s.charAt(i-1) == p.charAt(j-1)
                        继承前面一个正则匹配的布尔值
                        因为本身的结果是true的，但如果前面有一个正则匹配不匹配，
                        那么我继承前面的就会是false，最后结果也就是false了
                        */
                        dp[i][j] = dp[i-1][j-1];
                    }
                }else if(p.charAt(j-1)=='.'){
                    //为'.'，任意字符，无论咋样都匹配，所以继承前面的布尔值，原因在上面
                    dp[i][j] = dp[i-1][j-1];
                }else if(p.charAt(j-1)=='*'){

                    //最麻烦的*
                        /*
                        当遇到*时，应该将*和*前一位的字母作为一个整体来匹配
                        *有两种情况
                        1.多次匹配:
                          这种情况就是判断s中i位置的字符是否和*前一个字符的字符一样，一样则继承
                          对应 dp[i][j] = dp[i-1][j]
                        2.零次匹配或者匹配完成了的时候:
                          这种情况就可直接跳过当前这个匹配规则,注意当成一个整体，所以要跳两个字符继承
                          对应 dp[i][j] = dp[i][j-2]
                        */
                    if(s.charAt(i-1) != p.charAt(j-2) && p.charAt(j-2) != '.'){
                        //零次匹配
                        dp[i][j] = dp[i][j-2];
                    }else{
                        //多次匹配或者匹配完成了的时候
                        dp[i][j] = dp[i-1][j] || dp[i][j-2]||dp[i][j-1];
                    }
                }
            }
        }
        /*
        因为所有结果都是向前继承，中间只要所有规则都匹配,自然就会继承到dp[0][0]
        一路继承过来，最后结果也会是true，如果中间有不匹配的正则值，就会继承到false
        最后结果也就是为false
        */
        return dp[m][n];
    }
}
