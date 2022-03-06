package leetcode.GraphAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

// 有向图建图+Dijkstra
// 建图方式及如何选择：https://leetcode-cn.com/problems/network-delay-time/solution/gong-shui-san-xie-yi-ti-wu-jie-wu-chong-oghpz/
//leetcode743 https://leetcode-cn.com/problems/network-delay-time/
public class Dijkstra {
    public int networkDelayTime(int[][] times, int n, int k) {
        //邻接矩阵
        int[][] graph = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            //此处不能初始化为max_value，因为后面有两个初始值相加，会导致溢出
            Arrays.fill(graph[i], Integer.MAX_VALUE / 2);
        }
        for (int[] time : times) {
            graph[time[0]][time[1]] = time[2];
        }

        int[] dist = DijkstraNumerate(n, k, graph);
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, dist[i]);
        }
        return res == Integer.MAX_VALUE / 2 ? -1 : res;
    }

    // Dijkstra 枚举写法
    public int[] DijkstraNumerate(int n, int k, int[][] graph) {
        //距离k点的距离数组
        int[] dist = new int[n + 1];
        boolean[] visited = new boolean[n + 1];
        Arrays.fill(visited, false);
        Arrays.fill(dist, Integer.MAX_VALUE / 2);
        dist[k] = 0;
        for (int i = 1; i <= n; i++) {
            // 初始化为-1而不是第一个节点索引1是因为 visited[1]不一定为false
            int index = -1;
            for (int j = 1; j <= n; j++) {
                //选择没有被访问过并且离k点最小距离最近的一个点
                if (!visited[j] && (index == -1 || dist[j] < dist[index])) index = j;
            }
            visited[index] = true;
            //用选择的最小点更新其他所有点的dist距离
            for (int j = 1; j <= n; j++) {
                //若初始化为Integer.Maxvalue，此处为导致溢出而出现负值
                dist[j] = Math.min(dist[j], dist[index] + graph[index][j]);
            }
        }
        return dist;
    }


    public int networkDelayTime2(int[][] times, int n, int k) {
        //带距离的邻接表
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; ++i) {
//           int[]  = [index,distance]
            graph.add(new ArrayList<int[]>());
        }
        for (int[] time : times) {
            int x = time[0], y = time[1];
            graph.get(x).add(new int[]{y, time[2]});
        }

        int[] dist = DijkstraQueue(n, k, graph);
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, dist[i]);
        }
        return res == Integer.MAX_VALUE / 2 ? -1 : res;
    }

    // Dijkstra 优先队列写法（等于h为0的a星算法）
    // 将priorityQueue改为queue就是BFS
    public int[] DijkstraQueue(int n, int k, List<List<int[]>> graph) {
        //距离k点的距离数组
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE / 2);
        dist[k] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
//        优先队列改为队列就是bfs
//        Queue<int[]> pq = new ArrayDeque<>();
        pq.offer(new int[]{k, 0});
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int index = top[0], distance = top[1];
            //剪枝，如果队列前更新的距离小于当前记录的距离，则跳过
            if(dist[index]<distance) continue;
            for(int[] x: graph.get(index)) {
                int newIndex = x[0], newDistance = dist[index] + x[1];
                if(newDistance<dist[newIndex]) {
                    dist[newIndex] = newDistance;
                    pq.offer(new int[] {newIndex,newDistance});
                }
            }
        }
        return dist;
    }
}

