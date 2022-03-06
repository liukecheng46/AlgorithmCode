package leetcode.ClassicProblem;

import java.util.ArrayDeque;
import java.util.Deque;

//单调栈
//对于每个点，找左右两边第一个小于他的索引位置
//https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
public class largestRectangleArea {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.poll();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            //这里不能用offer，poll和peek默认是first，但是offer默认是last
            stack.push(i);
        }

        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.poll();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, (right[i] - left[i] - 1) * heights[i]);
        }
        return res;
    }
}
