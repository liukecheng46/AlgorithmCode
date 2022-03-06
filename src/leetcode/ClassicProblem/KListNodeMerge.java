package leetcode.ClassicProblem;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

//leetcode23
public class KListNodeMerge {
    public static class ListNode {
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

    public ListNode mergeKLists(ListNode[] lists) {
        int len = lists.length;
        if (len == 0) return null;

//        数组原地合并，不需要每merge一轮都开新数组
        while (!(len == 1)) {
            int count = 0;
            for (int i = 0; i < len - 2; i = i + 2) {
                lists[count] = mergeTwoNode(lists[i], lists[i + 1]);
                count++;
            }
            if (len % 2 == 0) {
                lists[count] = mergeTwoNode(lists[len - 2], lists[len - 1]);
            } else {
                lists[count] = lists[len - 1];
            }
            len = count + 1;
        }
        return lists[0];
    }

    //    递归归并
    public ListNode mergeKListsRecurse(ListNode[] lists, int left, int right) {
        if (left == right) {
            return lists[left];
        }
//        处理空列表
        if (left > right) {
            return null;
        }
        int mid = (left + right) >> 1;
        return mergeTwoNode(mergeKListsRecurse(lists, left, mid), mergeKListsRecurse(lists, mid + 1, right));
    }

    //    合并两个链表，细节问题很多，而且写的很垃圾 应该都不为空时写while，有一个为空写在while后面
    public ListNode mergeTwoNode(ListNode root1, ListNode root2) {
        ListNode newRoot = new ListNode();
        ListNode root = newRoot;
        while (root1 != null || root2 != null) {
            if (root1 == null) {
                root.next = root2;
                root2 = root2.next;
                root = root.next;
                continue;
            }
            if (root2 == null) {
                root.next = root1;
                root1 = root1.next;
                root = root.next;
                continue;
            }

            if (root1.val < root2.val) {
                root.next = root1;
                root1 = root1.next;
                root = root.next;
            } else {
                root.next = root2;
                root2 = root2.next;
                root = root.next;
            }
        }
        return newRoot.next;
    }

    public ListNode mergeTwoNodeRecurse(ListNode root1, ListNode root2) {
        if (root1 == null) return root2;
        if (root2 == null) return root1;
        if (root1.val < root2.val) {
            root1.next = mergeTwoNodeRecurse(root1.next, root2);
            return root1;
        } else {
            root2.next = mergeTwoNodeRecurse(root1, root2.next);
            return root2;
        }
    }

    //     维护k个head的小根堆
    public ListNode mergeKListsByHeap(ListNode[] lists) {
        Queue<ListNode> heap = new PriorityQueue<>((v1, v2) -> v1.val - v2.val);
        for (ListNode node : lists) {
            heap.offer(node);
        }

        ListNode DummyNode = new ListNode(0);
        ListNode head = DummyNode;

        while (!heap.isEmpty()) {
            ListNode minNode = heap.poll();
            DummyNode.next = minNode;
            DummyNode = DummyNode.next;
            if (minNode.next != null) {
                heap.offer(minNode.next);
            }
        }
        return head.next;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();
        ListNode res = head;
        boolean flag = false;
        while (l1 != null || l2 != null || flag!=false) {
            int n1 = l1 == null ? 0 : l1.val;
            int n2 = l2 == null ? 0 : l2.val;
            int sum = n1 + n2;
            sum += flag?1:0;
            flag =false;
            if(sum>9) flag = true;
            ListNode cur = new ListNode(sum%10);
            head.next = cur;
            head = head.next;
            if(l1!=null)l1 = l1.next;
            if(l2!=null)l2 = l2.next;
        }
        return res.next;
    }
}
