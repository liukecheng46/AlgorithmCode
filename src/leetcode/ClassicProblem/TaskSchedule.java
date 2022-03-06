package leetcode.ClassicProblem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class TaskSchedule {
    // 任务调度器 https://leetcode-cn.com/problems/task-scheduler/
    // 自己做想的优先队列倒来倒去的思路，编码有点问题，在题解看到一个写出来的(主要是从优先队列里导出来的没想好用什么结构存比较好，存在list里面再重新导回优先队列感觉复杂度太高了，但是确实这样可以)

    // 优先队列写法
    public int leastInterval(char[] tasks, int n) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        int[] map = new int[26];
        for (char c : tasks) {
            map[c - 'A']++;
        }
        for (int i = 0; i < 26; i++) {
            if (map[i] != 0) pq.add(new int[]{i, map[i]});
        }
        int res = 0;
        int k = 0;
        List<int[]> tempList = new LinkedList<>();
        while (!pq.isEmpty()) {
            k = n + 1;
            tempList.clear();
            while (!pq.isEmpty() && k-- > 0) {
                int[] top = pq.poll();
                top[1]--;
                if (top[1] != 0) tempList.add(top);
            }
            pq.addAll(tempList);
            res += n + 1;
        }
        return res - k;
    }

    // 用数组代替优先队列，每次重新sort的写法，思路和优先队列相同，但是代码更巧妙，不需要另开一个list倒来倒去
    public int leastInterval2(char[] tasks, int n) {
        if (n == 0) return tasks.length;

        //用一个最大堆来存放任务，数组末尾为堆顶
        int[] counts = new int[26];
        for (char c : tasks) counts[c - 'A']++;

        Arrays.sort(counts);

        int res = 0;
        //堆顶不为0，说明还有任务没有执行完
        while (counts[25] > 0) {
            //每次从堆顶取冷却时间为0的任务执行
            //然后再贪心地取n个不相同的任务接在后面执行，如果没有n个不相同的任务，剩余时间就会处于待命状态
            for (int i = 0; i <= n; ++i) {
                if (counts[25] == 0)
                    break;
                if (25 - i >= 0 && counts[25 - i] > 0)
                    counts[25 - i]--;
                res++;
            }
            //重排最大堆
            Arrays.sort(counts);
        }

        return res;
    }

    // 还有一种数学思路 https://leetcode-cn.com/problems/task-scheduler/solution/tian-tong-si-lu-you-tu-kan-wan-jiu-dong-by-mei-jia/
    // 简化模拟填桶过程
    public class Solution {

        public int leastInterval(char[] tasks, int n) {

            int[] counts = new int[26];

            for (char c : tasks) {
                counts[c - 'A'] += 1;
            }

            int max = 0;
            for (int count : counts) {
                max = Math.max(max, count);
            }

            int maxCount = 0;
            for (int count : counts) {
                if (count == max) {
                    maxCount++;
                }
            }

            return Math.max((n + 1) * (max - 1) + maxCount, tasks.length);
        }

    }

    // 重构字符串  https://leetcode-cn.com/problems/reorganize-string/
    // 相同思路 只是间隔变为2，所以不用一个temp的list来存

}
