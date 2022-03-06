package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class maxAppleToEat {
    public static int countNum(int[] apples, int[] days) {
        int n = apples.length;
        int m = days.length;
        int time = 0;
        int cnt = 0;
        PriorityQueue<int[]> appleQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        while (time < n || !appleQueue.isEmpty()) {
            if (time < n && apples[time] > 0) appleQueue.offer(new int[]{time + days[time], apples[time]});
//            去掉过期的
            while (!appleQueue.isEmpty() && time >= appleQueue.peek()[0]) {
                appleQueue.poll();
            }
//            不为空的话就吃掉离过期最近的第一个，还有就减一塞回去
            if (!appleQueue.isEmpty()) {
                int[] top = appleQueue.poll();
                cnt++;
                if (--top[1] > 0 ) {
                    appleQueue.offer(top);
                }
            }
            time++;
        }
        return cnt;
    }
}
