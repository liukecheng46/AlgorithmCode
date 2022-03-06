package leetcode.LinkedList;

import java.util.List;

//链表两数相加
public class addTwoNumberInLinkedList {
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

    // 两数相加
    // 基础链表操作加进位，很容易忽略两个链表最后一位还要进位的情况（flag!=false） 写的时候忽略了
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();
        ListNode res = head;
        boolean flag = false;
        while (l1 != null || l2 != null || flag) {
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

    // 两数之和2
    // 反转链表
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        // 反转l1
        ListNode new_l1 = reverseList(l1);

        // 反转l2
        ListNode new_l2 = reverseList(l2);

        return reverseList(addTwoNumbers(new_l1,new_l2));

    }

    //反转链表抽象出来
    ListNode reverseList(ListNode s) {
        ListNode pre = null;
        ListNode cur=s;
        while(cur!=null) {
            ListNode next = cur.next;
            cur.next =pre;
            pre =cur;
            cur=next;
        }
        return pre;
    }


    // 回文链表 https://leetcode-cn.com/problems/palindrome-linked-list/
    // 用快慢指针找到中间节点，将后半段反转，再进行验证，最后再将后半段反转回来
    public boolean isPalindrome(ListNode head) {
        // 快慢指针
        ListNode mid = findMid(head);
        // 反转链表
        ListNode Last = reverseList(mid);
        while(head!=mid && Last!=null) {
            if(head.val!=Last.val) return false;
            head= head.next;
            Last = Last.next;
        }
        return true;
    }

    public ListNode findMid(ListNode head) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode fast = dummy,slow= dummy;
        while(fast!=null && fast.next!=null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow.next;
    }

    // 反转链表还是得按标准写法把next放在while循环里面
    // 这种把next放在外面的写法需要对head为空的情况特殊处理，所以next初始化放在while循环里比较好
    public ListNode reverse(ListNode head) {
        ListNode pre = null,cur = head ,next = cur.next;
        while(cur!=null) {
            cur.next = pre;
            pre = cur;
            cur = next;
            if(next!=null) next = next.next;
        }
        return pre;
    }


    // 反转链表2 https://leetcode-cn.com/problems/reverse-linked-list-ii/
    // 反转给定范围链表
    // 第一种思路是反转给定范围链表再重新链接
    // 第二种思路是头插法
    public ListNode reverseBetween(ListNode head, int left, int right) {
        int len = right-left+1;
        ListNode rangePre = head;
        while(left-->2) {
            rangePre =rangePre.next;
        }
        ListNode rangeHead = rangePre.next;
        if(rangeHead==null) return head;
        ListNode pre = null,cur=rangeHead;
        while(cur!=null && len-->0) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur= next;
        }
        ListNode rangeNext = cur;
        ListNode rangeFirst = pre;
        rangePre.next = rangeFirst;
        rangeHead.next = rangeNext;
        return head;
    }


    // 头插法 todo
    public ListNode reverseBetween2(ListNode head, int m, int n) {
        // 定义一个dummyHead, 方便处理
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;

        // 初始化指针
        ListNode pre = dummyHead;
        ListNode cur = dummyHead.next;

        // 将指针移到相应的位置
        for(int step = 0; step < m - 1; step++) {
            pre = pre.next; cur = cur.next;
        }

        // 头插法插入节点
        for (int i = 0; i < n - m; i++) {
            ListNode next = cur.next;
            cur.next = cur.next.next;

            next.next = pre.next;
            pre.next = next;
        }

        return dummyHead.next;
    }


    // K个一组翻转链表 https://leetcode-cn.com/problems/reverse-nodes-in-k-group/
    // 也可以拿出来反转链表再连回去或者头插法
    // 头插法
    // 两两交换链表中的节点 是这道题 k为2的特殊情况 https://leetcode-cn.com/problems/swap-nodes-in-pairs/
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode pre = dummyHead,cur = head;
        while(cur!=null) {
            // 先确定后面还有k个节点，没有的话不进行翻转
            ListNode tempCur = cur;
            int count =k;
            while(count-->1) {
                tempCur=tempCur.next;
                // 注意这里不能retur head，而是dummyHead.next，因为头插把head插到了每段的最后
                if(tempCur==null) return dummyHead.next;
            }
            for(int i=0;i<k-1;i++) {
                ListNode next = cur.next;
                cur.next  = cur.next.next;
                next.next = pre.next;
                pre.next = next;
            }
            // 这一段翻转完后，更新pre和cur来翻转下一段
            pre=cur;
            cur=cur.next;
        }
        return dummyHead.next;
    }

}
