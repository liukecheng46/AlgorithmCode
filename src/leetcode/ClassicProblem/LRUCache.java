package leetcode.ClassicProblem;

import java.util.HashMap;
import java.util.Map;

// LRU
// 哈希表存链表节点+双向链表
public class LRUCache {
    class DoubleListNode {
        int key;
        int value;
        DoubleListNode prev;
        DoubleListNode next;
        public DoubleListNode() {}
        public DoubleListNode(int _key,int _value) {
            this.key=_key;
            this.value = _value;
        }
    }

    private Map<Integer,DoubleListNode>  LinkedMap = new HashMap<>();
    private int size;
    private int capacity;
    private DoubleListNode DummyHead,DummyTail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        DummyHead = new DoubleListNode();
        DummyTail = new DoubleListNode();
        DummyHead.next = DummyTail;
        DummyTail.prev = DummyHead;

    }

    public int get(int key) {
        DoubleListNode des = LinkedMap.get(key);
        if(des == null) return -1;
        des.prev.next = des.next;
        des.next.prev = des.prev;
        des.next = DummyHead.next;
        des.prev = DummyHead;
        DummyHead.next.prev = des;
        DummyHead.next = des;
        return des.value;
    }

    public void put(int key, int value) {
        DoubleListNode des = LinkedMap.get(key);
//        map里没有key时
        if(des == null) {
            DoubleListNode newNode = new DoubleListNode(key, value);
            LinkedMap.put(key,newNode);
//            add to head
            newNode.next = DummyHead.next;
            newNode.prev = DummyHead;
            DummyHead.next.prev = newNode;
            DummyHead.next = newNode;

            //         已经满了的话，remove最后一个
//            if(this.size == this.capacity) {
//                DoubleListNode Deleted = DummyTail.prev;
//                DummyTail.prev.prev.next = DummyTail;
//                DummyTail.prev = DummyTail.prev.prev;
//                LinkedMap.remove(Deleted);
//            } else {
//                size++;
//            }
            ++size;
            if (size > capacity) {
                // 如果超出容量，删除双向链表的尾部节点
                DoubleListNode Deleted = DummyTail.prev;
                DummyTail.prev.prev.next = DummyTail;
                DummyTail.prev = DummyTail.prev.prev;
                LinkedMap.remove(Deleted.key);
                --size;
            }
        }
//         map已经有这个key,处理现在位置并放到最前面
         else {
            des.value = value;
            des.prev.next = des.next;
            des.next.prev = des.prev;
            des.next = DummyHead.next;
            des.prev = DummyHead;
            DummyHead.next.prev = des;
            DummyHead.next = des;
        }


    }
}
