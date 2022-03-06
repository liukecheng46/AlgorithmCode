package leetcode.ClassicProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// 重建队列 https://leetcode-cn.com/problems/queue-reconstruction-by-height/
public class reconstructQueue {
    // 自己做的时候想到了身高升序，个数降序的排序方法
    // 虽然是做出来了，但是击败5%，发现是因为list插入到第几个位置是有api的，不需要用另一个list倒来倒去！！！
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people,(a,b)->a[0]==b[0]?b[1]-a[1]:a[0]-b[0]);
        LinkedList<int[]> res =new LinkedList<>();
        LinkedList<int[]> temp =new LinkedList<>();
        for(int i=people.length-1;i>=0;i--) {
            int count = people[i][1];
            // 用一个templist来倒数，其实可以直接用api插入
            while(count-->0) {
                temp.add(res.removeFirst());
            }
            res.addFirst(people[i]);
            while(!temp.isEmpty()) {
                res.addFirst(temp.removeLast());
            }
        }
        return res.toArray(new int[people.length][2]);
    }

    // 这种思路调用api直接插入的简洁代码
    public int[][] reconstructQueue2(int[][] people) {
        Arrays.sort(people, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0]);

        LinkedList<int[]> list = new LinkedList<>();
        for (int[] i : people) {
            //调用api 插入到第i个位置
            list.add(i[1], i);
        }

        return list.toArray(new int[list.size()][2]);
    }
}
