package aJianZhiOffer;

import leetcode.sortAlgorithm.sortList;

import java.util.Deque;
import java.util.LinkedList;

public class J006_reversePrint {
    public int[] reversePrint(sortList.ListNode head) {
        Deque<Integer> stack = new LinkedList<Integer>();
        while(head != null) {
            stack.addLast(head.val);
            head = head.next;
        }
        int[] res = new int[stack.size()];
        for(int i = 0; i < res.length; i++)
            res[i] = stack.removeLast();
        return res;
    }

}
