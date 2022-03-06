package leetcode;

import java.util.*;

// 股票波动 https://leetcode-cn.com/problems/stock-price-fluctuation/solution/gu-piao-jie-ge-bo-dong-by-leetcode-solut-rwrb/
// 优先队列维护最小最大，hashmap记录所有记录，优先队列出队时如果和hashmap中不一致代表已经被更新
public class StockPrice {
        int maxTimestamp;
        HashMap<Integer, Integer> timePriceMap;
        PriorityQueue<int[]> pqMax;
        PriorityQueue<int[]> pqMin;

        public StockPrice() {
            maxTimestamp = 0;
            timePriceMap = new HashMap<Integer, Integer>();
            pqMax = new PriorityQueue<int[]>((a, b) -> b[0] - a[0]);
            pqMin = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
        }

        public void update(int timestamp, int price) {
            maxTimestamp = Math.max(maxTimestamp, timestamp);
            timePriceMap.put(timestamp, price);
            pqMax.offer(new int[]{price, timestamp});
            pqMin.offer(new int[]{price, timestamp});
        }

        public int current() {
            return timePriceMap.get(maxTimestamp);
        }

        public int maximum() {
            while (true) {
                int[] priceTime = pqMax.peek();
                int price = priceTime[0], timestamp = priceTime[1];
                if (timePriceMap.get(timestamp) == price) {
                    return price;
                }
                pqMax.poll();
            }
        }

        public int minimum() {
            while (true) {
                int[] priceTime = pqMin.peek();
                int price = priceTime[0], timestamp = priceTime[1];
                if (timePriceMap.get(timestamp) == price) {
                    return price;
                }
                pqMin.poll();
            }
        }

}
