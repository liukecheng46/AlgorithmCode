package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class isStraightHand {
    public boolean isNStraightHand(int[] hand, int m) {
        int n = hand.length;
        if(n%m !=0) return false;
        Map<Integer,Integer> countMap = new HashMap<>();
        PriorityQueue<Integer> valueHeap = new PriorityQueue<>();
        for(int i:hand) {
            countMap.put(i,countMap.getOrDefault(i,0)+1);
            valueHeap.add(i);
        }
        while(!valueHeap.isEmpty()) {
            int top = valueHeap.poll();
//            如果堆顶元素在map中数量已经为0，说明在之前的顺子中已经用完这个值
            if(countMap.get(top)==0) continue;
            for(int i=0;i<m;i++) {
                if(countMap.getOrDefault(top+i,0) ==0) return false;
                countMap.put(top+i,countMap.get(top+i)-1);
            }
        }
        return true;
    }
}
