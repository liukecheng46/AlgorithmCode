package aJianZhiOffer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

public class J041_MedianFinder {
    PriorityQueue<Integer> small;
    PriorityQueue<Integer> big;

    public J041_MedianFinder() {
        small = new PriorityQueue<>((a, b) -> b - a);
        big = new PriorityQueue<>();
    }

    public void addNum(int num) {
        //这里的逻辑需要理清
        //我们维护small=big 或者small=big+1
        //如果small size 为0，需要加入到small（初始化不能遗漏）
        // 如果当前数小于等于small堆顶的数，则需要加入到small，反之加入到big。并且每次加入后都需要维护small=big或者small=big+1
        if (small.size() == 0 || num <= small.peek()) {
            small.offer(num);
            while(small.size()>big.size()+1) {
                big.offer(small.poll());
            }
        } else {
            big.offer(num);
            while(small.size()<big.size()) {
                small.offer(big.poll());
            }
        }

    }

    public double findMedian() {
        if (small.size() == big.size()) return  (small.peek() + big.peek())/ 2.0;
        else return small.peek();
    }
}
