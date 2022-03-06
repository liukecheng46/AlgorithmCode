package aJianZhiOffer;

public class J0013_MovingCount {
    boolean[][] vis;
    int count;
    static int[][] dirs= new int[][] {{-1,0},{1,0},{0,1},{0,-1}};
    public int movingCount(int m, int n, int k) {
        vis =new boolean[m][n];
        int count =dfs(0,0,k);
        return count;
    }

    // 这里如果要用回溯的写法，虽然可以上下左右走，但是vis[i][j]=false这句不能加,加了会重复计算
    public int dfs(int x,int y,int k) {
        if(vis[x][y]) return 0;
        int count=1;
        vis[x][y] = true;
        for(int[] dir: dirs) {
            int newX = x+dir[0];
            int newY = y+dir[1];
            if(check(newX,newY,k)&& !vis[newX][newY]) count+= dfs(newX,newY,k);
        }
        // 这里的回溯不能加，加了就重复计算了
        // vis[x][y]=false
        return count;
    }

    public boolean check(int x,int y,int k) {
        if(x<0 || x>vis.length-1) return false;
        if(y<0 || y>vis[0].length-1) return false;
        int count = getCount(x,y);
        if(count>k) return false;
        return true;
    }

    public int getCount(int x,int y) {
        int res=0;
        while(x>0) {
            res+=x%10;
            x/=10;
        }
        while(y>0) {
            res+=y%10;
            y/=10;
        }
        return res;
    }


    // 解法2： dp
    public int movingCount2(int m, int n, int k) {
        boolean[][] dp = new boolean[m][n];
        int count=0;
        dp[0][0] = true;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                if(getCount(i,j)>k) continue;
                if(i-1>=0) dp[i][j]|=dp[i-1][j];
                if(j-1>=0) dp[i][j]|=dp[i][j-1];
                count+=dp[i][j]?1:0;
            }
        }
        return count;
    }
}
