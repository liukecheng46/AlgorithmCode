package leetcode.BFS;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

// 多源bfs  带有多个同质源点的bfs问题求最短路
// 通过建立「虚拟源点」，我们可以将其转化回「单源 BFS」问题。实现上我们只需要将所有的「真实源点」进行入队，然后再进行 BFS 即可。
// 看起来两者区别不大，但其本质是通过源点/汇点转换，应用常规的 Flood Fill 将多次朴素 BFS 转化为一次 BFS，可以有效降低我们算法的时间复杂度。
// https://juejin.cn/post/6979044529680678920
public class MultiSourceBFS {

    // 地图分析 https://leetcode-cn.com/problems/as-far-from-land-as-possible/
    public int maxDistance(int[][] grid) {
        final int INF = 1000000;
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        int n = grid.length;
        int[][] d = new int[n][n];
        Queue<int[]> queue = new LinkedList<int[]>();

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                d[i][j] = INF;
            }
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1) {
                    d[i][j] = 0;
                    queue.offer(new int[]{i, j});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] f = queue.poll();
            for (int i = 0; i < 4; ++i) {
                int nx = f[0] + dx[i], ny = f[1] + dy[i];
                if (!(nx >= 0 && nx < n && ny >= 0 && ny < n)) {
                    continue;
                }
                if (d[nx][ny] > d[f[0]][f[1]] + 1) {
                    d[nx][ny] = d[f[0]][f[1]] + 1;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }

        int ans = -1;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 0) {
                    ans = Math.max(ans, d[i][j]);
                }
            }
        }

        return ans == INF ? -1 : ans;
    }


    // 腐烂的橘子 https://leetcode-cn.com/problems/rotting-oranges/
    public int orangesRotting(int[][] grid) {
        int[] p1 = {1, -1, 0, 0}, p2 = {0, 0, 1, -1};
        Deque<int[]> queue = new ArrayDeque<>();
        //把腐烂的🍊加入队列中，作为开始扩散的起点
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[] {i, j});
                }
            }
        }

        //从腐烂的🍊开始感染，其实就是一个bfs求无权图最短路的问题
        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] temp = queue.poll();
                for (int j = 0; j < p1.length; j++) {
                    int x = temp[0] + p1[j], y = temp[1] + p2[j];
                    if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == 1) {
                        grid[x][y] = 2;
                        queue.offer(new int[] {x, y});
                    }
                }
            }
            if (!queue.isEmpty()) {
                steps++;
            }
        }
        //遍历矩阵，判断是否有🍊还未被感染，也就是是否还有grid[i][j] = 1的地儿
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    return -1;
                }
            }
        }
        return steps;
    }


    // 飞地的数量 https://leetcode-cn.com/problems/number-of-enclaves/
    // 从边缘的陆地开始多源bfs
    public int numEnclaves(int[][] grid) {
        int[] xDir = new int[] {0,1,0,-1};
        int[] yDir = new int[] {1,0,-1,0};
        Queue<int[]> q = new LinkedList<>();
        // 初始化将最外围一圈的陆地加进队列
        // 遍历最外围其实有一种优雅的写法 todo
        // if (i == 0 || j == 0 || i == m - 1 || j == n - 1)
        for(int i=0;i<grid.length;i++) {
            if(i==0 || i==grid.length-1) {
                for(int j=0;j<grid[0].length;j++) {
                    if(grid[i][j]==1) {
                        q.offer(new int[] {i,j});
                        grid[i][j] =0;
                    }
                }
            } else {
                if(grid[i][0]==1) {
                    q.offer(new int[] {i,0});
                    grid[i][0] =0;
                }
                if(grid[i][grid[0].length-1]==1) {
                    q.offer(new int[] {i,grid[0].length-1});
                    grid[i][grid[0].length-1] =0;
                }
            }
        }

        //BFS
        while(!q.isEmpty()) {
            int[] top = q.poll();
            for(int i=0;i<4;i++) {
                int newX = top[0]+xDir[i], newY = top[1]+yDir[i];
                if(newX>0&&newX<grid.length-1&&newY>0&&newY<grid[0].length-1&&grid[newX][newY]==1) {
                    q.offer(new int[] {newX,newY});
                    grid[newX][newY] = 0;
                }
            }
        }
        int res=0;
        for(int i=0;i<grid.length;i++) {
            for(int j=0;j<grid[0].length;j++) {
                if(grid[i][j]==1) res++;
            }
        }
        return res;
    }


}
