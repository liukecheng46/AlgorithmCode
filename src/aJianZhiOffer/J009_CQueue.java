package aJianZhiOffer;

import java.util.Deque;
import java.util.LinkedList;

// 双栈实现队列
public class J009_CQueue {
    Deque<Integer> pushStack;
    Deque<Integer> pollStack;
    public J009_CQueue() {
        pushStack = new LinkedList<>();
        pollStack = new LinkedList<>();
    }

    public void appendTail(int value) {
        pushStack.addFirst(value);

    }

    public int deleteHead() {
        if(!pollStack.isEmpty()) return pollStack.poll();
        else {
            while(!pushStack.isEmpty()) pollStack.addFirst(pushStack.poll());
            return pollStack.isEmpty()?-1:pollStack.poll();
        }
    }
}
