package leetcode.ClassicProblem;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

//https://leetcode-cn.com/problems/dui-lie-de-zui-da-zhi-lcof/
public class MaxQueue {
    Queue<Integer> queue;
    Deque<Integer> max_queue;
    public MaxQueue() {
        queue = new LinkedList<Integer>();
        max_queue = new LinkedList<Integer>();
    }

    public int max_value() {
        return max_queue.isEmpty()? -1:max_queue.peek();
    }

    public void push_back(int value) {
        while (!max_queue.isEmpty() && max_queue.peekLast() < value) {
            max_queue.pollLast();
        }
        max_queue.offer(value);
        queue.offer(value);

    }

    public int pop_front() {
        if(!max_queue.isEmpty() &&max_queue.peek().equals(queue.peek())) {
            max_queue.poll();
        }
        return queue.isEmpty()? -1:queue.poll();
    }
}
