package leetcode.dfsBrackTracePrune;

// 马走日，判断k步后还停留在棋盘上的概率  https://leetcode-cn.com/problems/knight-probability-in-chessboard/
// 比较典型的dfs->记忆化->动态规划模板题变形(求概率且可回退)
// 因为可以回退，所以不需要vis数组，最开始用的是暴力dfs，用全局变量count统计次数，dfs到k为0时count+1，但是超时了
// 所以需要引入记忆化，前面的dfs因为是全局变量统计所有合法次数没办法记忆化，所以需要用dfs函数返回当前状态下的概率来记忆化
// 记忆化之后的下一步就是研究自底向上怎么归化为动态规划
// 根据memo数组，可知dp数组应该为dp[i][j][step]，表示从i,j出发，走了step步后仍在棋盘上的概率，则状态转移方程为 dp[i+dir[x]][j+dir[y]][step-1]的8个方向概率和，初始化step为0时均为1
public class knightProbability {
    // 记忆化搜索解法
    int[][] dirs  = new int[][] {{2,1},{1,2},{-1,2},{-2,1},{-2,-1},{-1,-2},{1,-2},{2,-1}};
    int[][] knight;
    int count;
    double[][] memo;
    public double knightProbability(int n, int k, int row, int column) {
        knight = new int[n][n];
        memo = new double[n*n+1][k+1];
        return dfs(row,column,k);
    }

    public double dfs(int row,int column,int k) {
        if(k==0) {
            return 1;
        }
        if(memo[row*knight.length+column][k]!=0) return memo[row*knight.length+column][k];
        double prob=0;
        for(int[] dir:dirs) {
            int newX = row+ dir[0];
            int newY = column+dir[1];
            if(check(newX,newY)) {
                prob+=dfs(newX,newY,k-1)/8;
            }
        }
        memo[row*knight.length+column][k] = prob;
        return prob;
    }

    public boolean check(int x,int y) {
        return x >= 0 && x <= knight.length - 1 && y >= 0 && y <= knight.length - 1;
    }


    // 动态规划解法，u1s1如果不是从记忆化规划想过来，还挺难正着想的
    public double knightProbability2(int n, int k, int row, int column) {
        double[][][] f = new double[n][n][k + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                f[i][j][0] = 1;
            }
        }
        for (int p = 1; p <= k; p++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int[] d : dirs) {
                        int nx = i + d[0], ny = j + d[1];
                        if (nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                        f[i][j][p] += f[nx][ny][p - 1] / 8;
                    }
                }
            }
        }
        return f[row][column][k];
    }
}
