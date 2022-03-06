package leetcode.sortAlgorithm;

// （链表的排序）插入 归并
public class sortList {
    public class ListNode {
        public int val;
        public ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //      version1
    //  插入排序 leetcode147
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
//        dummyhead维护已经排好序的链表，先将head加入这个链表；
        ListNode dummyHead = new ListNode(Integer.MIN_VALUE);
        dummyHead.next = head;
        head = head.next;
        dummyHead.next.next = null;

//       lastSorted是排好序链表的尾节点
        ListNode lastSorted = dummyHead.next;
        while (head != null) {
            if (head.val >= lastSorted.val) {
                lastSorted.next = head;
                head = head.next;
                lastSorted = lastSorted.next;
                lastSorted.next = null;
            } else {
                ListNode prev = dummyHead;
                while (prev.next != null && prev.next.val <= head.val) {
                    prev = prev.next;
                }
                ListNode firstLargerNode = prev.next;
                prev.next = head;
                head = head.next;
                prev.next.next = firstLargerNode;
            }
        }
        return dummyHead.next;
    }


    //      version2
//     归并排序递归版（自顶向下）  空间复杂度为O(logn)
//     快慢指针找中点，合并两个有序链表
    public ListNode mergeSortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode mid = findMid(head);
        ListNode rightHead = mid.next;
        mid.next = null;
        return mergeTwoSortedList(mergeSortList(head), mergeSortList(rightHead));
    }

    //    找中点，比如有1234，要找2然后从2切分，不是双向链表所以不能从3切分
//    因此要加一个虚拟头节点，且快指针到结尾时就要停止,这样慢指针指的是2不是3
    public ListNode findMid(ListNode head) {
        ListNode dummyHead = new ListNode(Integer.MIN_VALUE, head);
        ListNode fast = dummyHead;
        ListNode slow = dummyHead;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    //    合并两个有序链表
    public ListNode mergeTwoSortedList(ListNode head1, ListNode head2) {
        ListNode head = new ListNode(Integer.MIN_VALUE);
        ListNode res = head;
        while (head1 != null & head2 != null) {
            if (head1.val <= head2.val) {
                head.next = head1;
                head1 = head1.next;
                head = head.next;
                head.next = null;
            } else {
                head.next = head2;
                head2 = head2.next;
                head = head.next;
                head.next = null;
            }
        }
        if (head1 != null) {
            head.next = head1;
        }
        if (head2 != null) {
            head.next = head2;
        }
        return res.next;
    }

    //     version3
//     归并排序迭代版（自底向上） 空间复杂度可以为O(1)，因为不像数组归并需要辅助数组
    public ListNode mergeSortListIterative(ListNode head) {
        if (head == null || head.next == null) return head;
        int length = 0;
        ListNode node = head;
        while (node != null) {
            length++;
            node = node.next;
        }
//        需要维护prev和next指针来进行链表的删除和排序后重新连接
        ListNode dummyHead = new ListNode(Integer.MIN_VALUE, head);
        for (int iterateLength = 1; iterateLength < length; iterateLength *= 2) {
            ListNode prev = dummyHead, cur = dummyHead.next;
            while (cur != null) {
                ListNode head1 = cur;
                for (int i = 1; i < iterateLength && cur.next != null; i++) {
                    cur = cur.next;
                }
                ListNode head2 = cur.next;
                cur.next = null;
                cur = head2;
                for (int i = 1; i < iterateLength && cur != null && cur.next != null; i++) {
                    cur = cur.next;
                }
                ListNode next = null;
                if (cur != null) {
                    next = cur.next;
                    cur.next = null;
                }
                prev.next = mergeTwoSortedList(head1, head2);
                while (prev.next != null) {
                    prev = prev.next;
                }
                cur = next;
            }
        }
        return dummyHead.next;
    }
}
