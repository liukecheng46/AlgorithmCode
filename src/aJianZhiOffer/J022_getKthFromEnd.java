package aJianZhiOffer;

import java.util.List;

// 快慢指针
public class J022_getKthFromEnd {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode slow =dummyHead;
        ListNode quick = dummyHead;
        while(k-->0) {
            quick = quick.next;
        }
        while(quick!=null) {
            quick=quick.next;
            slow=slow.next;
        }
        return slow;
    }
}
