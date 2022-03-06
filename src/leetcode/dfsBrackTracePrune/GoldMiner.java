package leetcode.dfsBrackTracePrune;

// 黄金矿工
// 经典回溯
public class GoldMiner {
    int max;
    int[] xD = new int[] {0,1,0,-1};
    int[] yD = new int[] {1,0,-1,0};
    public int getMaximumGold(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        boolean[][] vis = new boolean[n][m];
        max = 0;
        for(int i=0;i<n;i++) {
            for(int j=0;j<m;j++) {
                if(grid[i][j]!=0) {
                    vis[i][j] = true;
                    dfs(grid,i,j,vis,grid[i][j]);
                    vis[i][j] = false;

                }
            }
        }
        return max;
    }

    public void dfs(int[][] grid,int i,int j,boolean[][] vis,int count) {
        max = Math.max(max,count);
        for(int x=0;x<4;x++) {
            int newX = i+xD[x],newY = j+yD[x];
            if(newX>=0&&newX<grid.length&&newY>=0&&newY<grid[0].length&&!vis[newX][newY]&& grid[newX][newY]!=0) {
                vis[newX][newY] =true;
                dfs(grid,newX,newY,vis,count+grid[newX][newY]);
                vis[newX][newY] = false;
            }
        }
    }
}
