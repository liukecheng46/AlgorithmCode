package leetcode.ClassicProblem;

import java.util.Deque;
import java.util.LinkedList;

//https://leetcode-cn.com/problems/min-stack-lcci/submissions/
//单栈双栈解法都要会
public class MinStack {
    Deque<Integer> q;
    Integer min;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        q = new LinkedList<Integer>();
        min = Integer.MAX_VALUE;
    }

    public void push(int x) {
        if (min >= x) {
            q.offerFirst(min);
            min = x;
        }
        q.offerFirst(x);

    }

    public void pop() {
        if (min.equals(q.pollFirst())) {
            min = q.pollFirst();
        }

    }

    public int top() {
        return q.peekFirst();
    }

    public int getMin() {
        return min;
    }
}
