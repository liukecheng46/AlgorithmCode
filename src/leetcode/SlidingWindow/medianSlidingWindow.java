package leetcode.SlidingWindow;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

// 滑动窗口中位数 经典算法
// 维护两个size大小的堆，但是优先队列没法O（1）时间删除指定元素，可以使用treemap来操作
// 使用优先队列的话还需要引入延迟删除的概念，用哈希表存储出窗口的数和对应删除次数，当堆顶元素与其相同时
// 使用优先队列也可以直接用remove来实现删除指定元素，这个复杂度是o(k)而不是o(1)，但是这样写比延迟删除简单很多 https://leetcode-cn.com/problems/sliding-window-median/solution/xiang-jie-po-su-jie-fa-you-xian-dui-lie-mo397/
// 优先队列的remove是先用indexof()找到第一个符合条件的索引，再进行删除，所以不用担心某个值有多个的话remove会全部删去，并且因为有indexof，所以复杂度是O(K)
public class medianSlidingWindow {

    //优先队列+延迟删除做法
    public double[] medianSlidingWindow(int[] nums, int k) {
        DualHeap dh = new DualHeap(k);
        for (int i = 0; i < k; ++i) {
            dh.insert(nums[i]);
        }
        double[] ans = new double[nums.length - k + 1];
        ans[0] = dh.getMedian();
        for (int i = k; i < nums.length; ++i) {
            dh.insert(nums[i]);
            dh.erase(nums[i - k]);
            ans[i - k + 1] = dh.getMedian();
        }
        return ans;
    }

    class DualHeap {
        // 大根堆，维护较小的一半元素
        private PriorityQueue<Integer> small;
        // 小根堆，维护较大的一半元素
        private PriorityQueue<Integer> large;
        // 哈希表，记录「延迟删除」的元素，key 为元素，value 为需要删除的次数
        private Map<Integer, Integer> delayed;

        //滑动窗口大小
        private int k;

        // small 和 large 当前真实包含的元素个数，需要扣除被「延迟删除」的元素
        private int smallSize, largeSize;

        public DualHeap(int k) {
            this.small = new PriorityQueue<Integer>((a, b) -> b - a);
            this.large = new PriorityQueue<Integer>((a, b) -> a - b);
            this.delayed = new HashMap<Integer, Integer>();
            this.k = k;
            this.smallSize = 0;
            this.largeSize = 0;
        }

        public double getMedian() {
            return (k & 1) == 1 ? small.peek() : ((double) small.peek() + large.peek()) / 2;
        }

        public void insert(int num) {
            if (small.isEmpty() || num <= small.peek()) {
                small.offer(num);
                ++smallSize;
            } else {
                large.offer(num);
                ++largeSize;
            }
            makeBalance();
        }

        public void erase(int num) {
            delayed.put(num, delayed.getOrDefault(num, 0) + 1);
            if (num <= small.peek()) {
                --smallSize;
                //维护两个堆顶都是没有被删除的元素
                if (num == small.peek()) {
                    prune(small);
                }
            } else {
                --largeSize;
                if (num == large.peek()) {
                    prune(large);
                }
            }
            makeBalance();
        }

        // 不断地弹出 heap 的堆顶元素，并且更新哈希表
        private void prune(PriorityQueue<Integer> heap) {
            while (!heap.isEmpty()) {
                int num = heap.peek();
                if (delayed.containsKey(num)) {
                    delayed.put(num, delayed.get(num) - 1);
                    if (delayed.get(num) == 0) {
                        delayed.remove(num);
                    }
                    heap.poll();
                } else {
                    break;
                }
            }
        }

        // 调整 small 和 large 中的元素个数，使得二者的元素个数满足要求
        private void makeBalance() {
            if (smallSize > largeSize + 1) {
                // small 比 large 元素多 2 个
                large.offer(small.poll());
                --smallSize;
                ++largeSize;
                // small 堆顶元素被移除，需要进行 prune
                prune(small);
            } else if (smallSize < largeSize) {
                // large 比 small 元素多 1 个
                small.offer(large.poll());
                ++smallSize;
                --largeSize;
                // large 堆顶元素被移除，需要进行 prune
                prune(large);
            }
        }


        // treemap做法
        class Solution {
            class Obj {
                // key为值，value是数量
                TreeMap<Integer, Integer> ml = new TreeMap<Integer, Integer>((a, b) -> b.compareTo(a));
                TreeMap<Integer, Integer> mr = new TreeMap<Integer, Integer>();
                //左右size
                int lsz = 0, rsz = 0;

                public Integer get_first(TreeMap<Integer, Integer> map) {
                    return map.keySet().iterator().next();//获取堆中第一个数
                }

                public void inc(TreeMap<Integer, Integer> map, int x) {
                    map.put(x, map.getOrDefault(x, 0) + 1); //向堆中添加一个x
                }

                public void dec(TreeMap<Integer, Integer> map, int x) {
                    if (map.get(x) == 1) map.remove(x);  //向堆中删除一个x
                    else map.put(x, map.get(x) - 1);
                }

                Obj() {
                }

                // 新进入滑动窗口的数
                public void insert(int x) { //插入，按照约定左侧一定比右侧小
                    if (ml.size() == 0 || x < get_first(ml)) {
                        inc(ml, x);
                        lsz++;
                    } else {
                        inc(mr, x);
                        rsz++;
                    }
                }

                //删除出滑动窗口的数
                public void del(int x) {//删除
                    if (ml.containsKey(x)) {
                        dec(ml, x);
                        lsz--;
                    } else {
                        dec(mr, x);
                        rsz--;
                    }
                }

                public double getMid() {//返回中位数
                    int t = (lsz + rsz) % 2;
                    while (lsz - rsz != t) {//调整两个堆的大小关系，使其满足l==r 或者l+1==r
                        if (lsz - rsz > t) {//左边太多
                            int x = get_first(ml);
                            dec(ml, x);
                            inc(mr, x);
                            lsz--;
                            rsz++;
                        } else {//右边太多
                            int x = get_first(mr);
                            dec(mr, x);
                            inc(ml, x);
                            rsz--;
                            lsz++;
                        }
                    }
                    if (t == 1) return get_first(ml);
                    else return (0.0 + get_first(ml) + get_first(mr)) / 2;
                }
            }

            public double[] medianSlidingWindow(int[] nums, int k) {
                double[] res = new double[nums.length - k + 1];
                int p = 0;
                Obj obj = new Obj();
                for (int i = 0; i < nums.length; i++) {
                    obj.insert(nums[i]);
                    if (i >= k) obj.del(nums[i - k]);
                    if (i >= k - 1) res[p++] = obj.getMid();
                }
                return res;
            }
        }
    }
}

