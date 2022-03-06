package aJianZhiOffer;

public class J052_InsectionNode {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 找到哪个list更长
        ListNode head1=headA,head2 =headB;
        while(head1!=null && head2!=null) {
            head1=head1.next;
            head2=head2.next;
        }
        ListNode longer;
        ListNode shorter;
        ListNode cur;
        if(head1==null) {
            longer = headB;
            shorter= headA;
            cur = head2;
        }
        else {
            longer = headA;
            shorter = headB;
            cur=head1;
        }

        while(cur!=null) {
            cur=cur.next;
            longer=longer.next;
        }
        while(longer!=shorter) {
            longer=longer.next;
            shorter=shorter.next;
        }
        return longer;
    }
}
