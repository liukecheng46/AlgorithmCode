package leetcode.ClassicProblem;

import java.util.*;

// 单调栈相关题目
public class MonotoneStackConclusion {
    // 每日温度 https://leetcode-cn.com/problems/daily-temperatures/
    // 存索引的标准单调栈
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new LinkedList<>();
        int[] res = new int[temperatures.length];
        int index = temperatures.length - 1;
        res[index--] = 0;
        //记得linkedlist是双向链表，offer是在尾部插入，但是poll和peek是从头部，所以要offerFirst或者push
        stack.offerFirst(temperatures.length - 1);
        for (int i = temperatures.length - 2; i >= 0; i--) {
            while (!stack.isEmpty() && temperatures[i] >= temperatures[stack.peek()]) {
                stack.poll();
            }
            res[index--] = stack.isEmpty() ? 0 : stack.peek() - i;
            stack.offerFirst(i);
        }
        return res;
    }

    // 下一个更大元素2 https://leetcode-cn.com/problems/next-greater-element-ii/
    // 标准单调栈 不用存索引 但是需要加一个循环数组的技巧：右边复制一份数组变成两倍长度或者取模来模拟，取模来模拟的方法需要掌握，因为通用性更强（循环不止两次的情况）
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] a = new int[n << 1];
        //物理拉长1倍
        for (int i = 0; i < n; i++) {
            a[i] = nums[i];
            a[i + n] = nums[i];
        }
        Deque<Integer> stk = new LinkedList<>();
        int[] res = new int[n];
        for (int i = n * 2 - 1; i >= 0; i--) {
            while (stk.size() != 0 && stk.peek() <= a[i]) stk.pop();
            if (i < n) res[i] = stk.size() == 0 ? -1 : stk.peek();
            stk.push(a[i]);
        }
        return res;
    }

    // 子数组范围和 https://leetcode-cn.com/problems/sum-of-subarray-ranges/
    // 只想到用单调队列的O（n^2)做法
    // 用单调栈计算出每个元素作为最大值和最小值的区间再进行计算，这个方法太妙了
    int n;
    public long subArrayRanges(int[] nums) {
        n = nums.length;
        // min[i] 为 nums[i] 作为区间最小值的次数；max[i] 为 nums[i] 作为区间最大值的次数
        long[] min = getCnt(nums, true), max = getCnt(nums, false);
        long ans = 0;
        for (int i = 0; i < n; i++) ans += (max[i] - min[i]) * nums[i];
        return ans;
    }

    long[] getCnt(int[] nums, boolean isMin) {
        int[] a = new int[n], b = new int[n];
        Deque<Integer> d = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!d.isEmpty() && (isMin ? nums[d.peekLast()] >= nums[i] : nums[d.peekLast()] <= nums[i]))
                d.pollLast();
            a[i] = d.isEmpty() ? -1 : d.peekLast();
            d.addLast(i);
        }
        d.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!d.isEmpty() && (isMin ? nums[d.peekLast()] > nums[i] : nums[d.peekLast()] < nums[i])) d.pollLast();
            b[i] = d.isEmpty() ? n : d.peekLast();
            d.addLast(i);
        }
        long[] ans = new long[n];
        for (int i = 0; i < n; i++) ans[i] = (i - a[i]) * 1L * (b[i] - i);
        return ans;
    }
}
