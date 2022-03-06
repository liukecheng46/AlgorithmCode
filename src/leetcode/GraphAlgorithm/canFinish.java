package leetcode.GraphAlgorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

//课程表能否完成 一样是判断有向图是否有环
public class canFinish {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new ArrayList[numCourses];
        int[] indegree = new int[numCourses];
        for(int i=0;i<numCourses;i++) {
            graph[i] = new ArrayList<>();
        }
        for(int[] preq:prerequisites) {
            graph[preq[0]].add(preq[1]);
            indegree[preq[1]]++;
        }
        Queue<Integer> q = new ArrayDeque<>();
        for(int i=0;i<numCourses;i++) {
            if(indegree[i]==0) {
                q.offer(i);
            }
        }
        int cnt=0;
        while(!q.isEmpty()) {
            int top = q.poll();
            cnt++;
            for(int y:graph[top]) {
                indegree[y]--;
                if(indegree[y]==0) q.offer(y);
            }
        }
        return cnt==numCourses;
    }
}
