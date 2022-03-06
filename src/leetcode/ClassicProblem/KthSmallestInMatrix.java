package leetcode.ClassicProblem;

import java.util.PriorityQueue;

// 逐行逐列递增矩阵中找到第k小的数
// 和合并k个链表的优先队列解法一样，优先队列实现多路归并
// 也可以用二分（进一步利用逐列也递增的特性），其他类似但逐列不递增的题目只能用归并做法
//https://leetcode-cn.com/problems/kth-smallest-element-in-a-sorted-matrix/
// 稍微变形，本质相同的题目：https://leetcode-cn.com/problems/find-k-pairs-with-smallest-sums/  https://leetcode-cn.com/problems/k-th-smallest-prime-fraction/
public class KthSmallestInMatrix {
    //优先队列做法
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0]-b[0]);
        int n = matrix.length;
        for(int i=0;i<n;i++) {
            pq.offer(new int[] {matrix[i][0],i,0});
        }

        for(int i=0;i<k-1;i++) {
            int[] cur = pq.poll();
            if(cur[2]+1<=n-1) {
                pq.offer(new int[] {matrix[cur[1]][cur[2]+1],cur[1],cur[2]+1});
            }
        }
        return pq.poll()[0];
    }

    //二分做法
    // O（n）时间判断有多少数小于mid的方法
    public int kthSmallest2(int[][] matrix, int k) {
        int left = matrix[0][0];
        int right = matrix[matrix.length-1][matrix.length-1];
        while(left<right) {
            int mid = left+(right-left)/2;
            if(check(matrix,mid, k)) right = mid;
            else left =mid+1;
        }
        return right;
    }

    public boolean check(int[][] matrix, int mid,int k) {
        int i =  matrix.length-1;
        int j = 0;
        int cnt = 0;
        while(i>=0 && j<=matrix.length-1) {
            if(matrix[i][j]<=mid) {
                cnt+=i+1;
                j++;
            } else {
                i--;
            }
        }
        return cnt>=k;
    }


}
