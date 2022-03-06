package leetcode.ClassicProblem;

// 找到相交链表的交点
// 核心思想：a和b往下走，走到头就从另一方头结点开始继续走
// a = x+y , b=z+y，若a和b相交，则在走完x+z+y之后两个指针一定在相交处第一次相遇(速度相同，路程相同)
// 走到尽头见不到你，于是走过你来时的路，等到相遇时才发现，你也走过我来时的路。
// 也可以首先记录双方长度，长的先走差值再一起走
public class IntersectionLinkedList {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }
}
