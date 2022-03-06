package leetcode.ClassicProblem;

import java.util.Deque;
import java.util.LinkedList;

// 接雨水
// 除了扫两遍的动态规划基本做法，还需要掌握空间优化后的双指针动态规划和单调栈
public class TrappingWater {
    // 双指针做法
    // 下标i处的雨水量是由左右两边单调递增最大值的最小值决定的，所以如果height[left] < height[right]，那么leftMax一定小于rightMax，这样i的雨水量等于leftMax-height[i]
    // 和扫两遍的做法本质相同，空间进行了优化
    public int trap(int[] height) {
        int ans = 0;
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            // leftMax一定小
            if (height[left] < height[right]) {
                ans += leftMax - height[left];
                ++left;
            } else {
                ans += rightMax - height[right];
                --right;
            }
        }
        return ans;
    }

    // 单调栈
    // 从左到右遍历数组，遍历到下标 i 时，如果栈内至少有两个元素，记栈顶元素为 top 的下面一个元素是 left，则一定有height[left]≥height[top]。
    // 如果height[i]>height[top]，则得到一个可以接雨水的区域，该区域的宽度是 i−left−1，高度是min(height[left],height[i])−height[top]，根据宽度和高度即可计算得到该区域能接的雨水量。
    // 为了得到 left，需要将 top 出栈。在对 top 计算能接的雨水量之后，left 变成新的 top，重复上述操作，直到栈变为空，或者栈顶下标对应的 height 中的元素大于或等于 height[i]。
    public int trap2(int[] height) {
        int ans = 0;
        Deque<Integer> stack = new LinkedList<Integer>();
        int n = height.length;
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int left = stack.peek();
                int currWidth = i - left - 1;
                int currHeight = Math.min(height[left], height[i]) - height[top];
                ans += currWidth * currHeight;
            }
            stack.push(i);
        }
        return ans;
    }



}
