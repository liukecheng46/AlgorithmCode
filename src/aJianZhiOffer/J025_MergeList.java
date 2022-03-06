package aJianZhiOffer;

public class J025_MergeList {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode head  =dummyHead;
        while(l1!=null&&l2!=null) {
            if(l1.val<l2.val) {
                head.next = l1;
                l1=l1.next;
                head = head.next;
            } else {
                head.next = l2;
                l2=l2.next;
                head=head.next;
            }
        }

        if(l1==null) head.next = l2;
        if(l2==null) head.next = l1;
        return dummyHead.next;
    }
}
