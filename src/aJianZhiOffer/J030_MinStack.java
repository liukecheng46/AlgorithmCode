package aJianZhiOffer;

import java.util.ArrayDeque;
import java.util.Deque;

public class J030_MinStack {
    Deque<Integer> num;
    Deque<Integer> min;
    public J030_MinStack() {
        num = new ArrayDeque<>();
        min = new ArrayDeque<>();
    }

    public void push(int x) {
        num.addFirst(x);
        // 这里等于号不能去掉，有多个和最小值相同的加入都要加入到min里
        if(min.isEmpty() || x<=min.peek()) min.addFirst(x);
    }

    public void pop() {
        if(num.isEmpty()) return;
        if(num.poll().equals(min.peek())) min.poll();
    }

    public int top() {
        if(num.isEmpty()) return -1;
        return num.peek();
    }

    public int min() {
        if(min.isEmpty()) return -1;
        return min.peek();
    }
}
