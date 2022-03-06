package leetcode.ClassicProblem;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

//滑动窗口最大值
public class MaxSlidingWindow {
    // 维持一个大小为k的双端队列，可能会超时，可以优化成只存储最大值
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> dq = new LinkedList<>();
        int[] res = new int[nums.length-k+1];;
        int j=0;
        for(int i=0;i<nums.length;i++) {
            //大于窗口容量 弹出最左边的最大值并记录
            if(dq.size()==k) res[j++] = dq.pollFirst();
            //窗口右边滑动一格的时候，更新双端队列
            int cnt=0;
            while(!dq.isEmpty() && dq.peekLast()<nums[i]) {
                dq.pollLast();
                cnt++;
            }
            while(cnt-->=0) {
                dq.offerLast(nums[i]);
            }
        }
        res[j] = dq.pollFirst();
        return res;
    }

    //存储索引，根据索引判断是否已经在窗口外
    class Solution {
        public int[] maxSlidingWindow(int[] nums, int k) {
            if(nums == null || nums.length < 2) return nums;
            // 双向队列 保存当前窗口最大值的数组位置 保证队列中数组位置的数值按从大到小排序
            LinkedList<Integer> queue = new LinkedList();
            // 结果数组
            int[] result = new int[nums.length-k+1];
            // 遍历nums数组
            for(int i = 0;i < nums.length;i++){
                // 保证从大到小 如果前面数小则需要依次弹出，直至满足要求
                while(!queue.isEmpty() && nums[queue.peekLast()] <= nums[i]){
                    queue.pollLast();
                }
                // 添加当前值对应的数组下标
                queue.addLast(i);
                // 判断当前队列中队首的值是否有效
                if(queue.peek() <= i-k){
                    queue.poll();
                }
                // 当窗口长度为k时 保存当前窗口中最大值
                if(i+1 >= k){
                    result[i+1-k] = nums[queue.peek()];
                }
            }
            return result;
        }
    }
}
