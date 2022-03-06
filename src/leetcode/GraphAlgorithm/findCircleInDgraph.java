package leetcode.GraphAlgorithm;

import java.util.*;

// 有向图找在环上的点： dfs 或者 拓扑

//也可以使用并查集，但是并查集最好还是用在无向图找环中。因为有向图中，两个将要合并的点有同样的parent也不一定就有环（有向），还需要再额外判断(很麻烦)，而无向图中可以直接据此判环

// leetcode802 https://leetcode-cn.com/problems/find-eventual-safe-states/
public class findCircleInDgraph {

    // dfs+记忆性染色 找所有不在环上的点
    public List<Integer> dfsFindCircle(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        List<Integer> res = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (dfs(graph,i,color))  res.add(i);
        }
        return res;
    }

    public boolean dfs(int[][] graph, int x, int[] color) {
        if(color[x] ==1) return false;
        if(color[x] ==2) return true;
        color[x] = 1;
        for(int i: graph[x]) {
            if(!dfs(graph,i,color)) {
                return false;
            }
        }
        color[x] = 2;
        return true;
    }



    //拓扑排序找所有不在环上的点（需要反向+拓扑排序）
    // 判断有没有环的话只用判断拓扑排序出队的点是否是全部节点
    // 拓扑排序在无向图上找环时是将所有度 <= 1 的结点入队（有向图上将入度为0的节点入队）
    public List<Integer> topoFindCircle(int[][] graph) {
        int n = graph.length;
        int[] indegree = new int[n];
        // n确定的话，其实外面的list用数组更好
        //List<Integer>[] newGraph = new List[n];
        List<List<Integer>> newGraph = new ArrayList<>();
        List<Integer> res = new LinkedList<>();
        for(int i=0;i<n;i++) {
            newGraph.add(new ArrayList<Integer>());
        }
        for (int x = 0; x < n; x++) {
            for (int y : graph[x]) {
                newGraph.get(y).add(x);
            }
            indegree[x] = graph[x].length;
        }
        Queue<Integer> topoQueue = new ArrayDeque<>();
        for (int i=0;i<n;i++) {
            if(indegree[i]==0) topoQueue.add(i);
        }
        while(!topoQueue.isEmpty()) {
            int top = topoQueue.poll();
            for (int x: newGraph.get(top)) {
                indegree[x]--;
                if(indegree[x]==0) {
                    topoQueue.offer(x);
                }
            }
        }

        for (int i = 0; i < n; ++i) {
            if (indegree[i] == 0) {
                res.add(i);
            }
        }

        return res;
    }
}
