package aJianZhiOffer;

public class J018_deleteNodew {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode deleteNode(ListNode head, int val) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode pre = dummyHead;
        ListNode cur = pre.next;
        while(cur!=null&&cur.val!=val) {
            cur=cur.next;
            pre = pre.next;
        }
        if(cur!=null) {
            pre.next = cur.next;
        }
        return dummyHead.next;
    }
}
