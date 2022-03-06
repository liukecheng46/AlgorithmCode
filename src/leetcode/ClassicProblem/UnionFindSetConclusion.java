package leetcode.ClassicProblem;

// 并查集总结
public class UnionFindSetConclusion {
    // 飞地的数量 https://leetcode-cn.com/problems/number-of-enclaves/
    // 可以用多源bfs，也可以用并查集+dfs
    // 对于边缘的每个陆地，进行一次dfs并且union（parent都是0），最后看哪些陆地parent是自己
    int N = 550;
    int[] p = new int[N * N];
    int m, n;
    int[][] g;
    // 递归来找父亲节点
//    int find(int x) {
//        if (p[x] != x) p[x] = find(p[x]);
//        return p[x];
//    }
    // 迭代找父亲节点
    int find(int x) {
        int temp = x;
        while (p[x] != x) {
            // 加速寻找过程，这行可以删去
            p[x] = p[p[x]];
            x = p[x];
        }
        p[temp] = x;
        return x;
    }
    boolean query(int a, int b) {
        return find(a) == find(b);
    }
    void union(int a, int b) {
        p[find(a)] = find(b);
    }
    public int numEnclaves(int[][] grid) {
        g = grid;
        m = g.length; n = g[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                p[getIdx(i, j)] = getIdx(i, j);
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                    if (g[i][j] != 1 || query(getIdx(i, j), 0)) continue;
                    dfs(i, j);
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (g[i][j] == 1 && p[getIdx(i, j)] == getIdx(i, j)) ans++;
            }
        }
        return ans;
    }
    int[][] dirs = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};

    void dfs(int x, int y) {
        union(getIdx(x, y), 0);
        for (int[] d : dirs) {
            int nx = x + d[0], ny = y + d[1];
            if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
            if (g[nx][ny] != 1 || query(getIdx(nx, ny), 0)) continue;
            dfs(nx, ny);
        }
    }
    int getIdx(int x, int y) {
        return x * n + y + 1;
    }
}
