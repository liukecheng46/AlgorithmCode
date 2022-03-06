package aJianZhiOffer;

import java.util.ArrayDeque;
import java.util.Deque;

public class J059_maxSlidingWindow {
    Deque<Integer> dq = new ArrayDeque<>();
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums.length==0 ||k==0) return new int[0];
        int[] res = new int[nums.length-k+1];
        int index=0;
        for(int i=0;i<nums.length;i++) {
            while(!dq.isEmpty() && dq.peekLast()<nums[i]) dq.pollLast();
            dq.offerLast(nums[i]);
            if(i+1>k) {
                if(dq.peekFirst().equals(nums[i-k])) dq.pollFirst();
            }
            if(i+1>=k) res[index++] = dq.peekFirst();
        }
        return res;
    }
}
