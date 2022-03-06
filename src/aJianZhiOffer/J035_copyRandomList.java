package aJianZhiOffer;


// 想到了用哈希表存的解法
// 需要掌握O（1）空间的复制解法 todo
public class J035_copyRandomList {
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        if(head==null) return null;
        Node cur = head;
        //在后面复制
        while(cur!=null) {
            Node curNew = new Node(cur.val);
            curNew.next  = cur.next;
            cur.next = curNew;
            cur = cur.next.next;
        }
        //所有的复制指针都被创建了，所以复制随机指针
        Node copy =head;
        while(copy!=null) {
            Node random = copy.random;
            copy.next.random = random==null? null:random.next;
            copy = copy.next.next;
        }

        // 分离原节点和复制节点
        Node newHead = head.next;
        while(head!=null) {
            Node next = head.next;
            head.next = next==null? null:head.next.next;
            head = next;
        }
        return newHead;
    }
}
