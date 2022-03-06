package leetcode.ClassicProblem;

import java.util.Random;

// 蓄水池抽样算法
// 还有分布式蓄水池抽样算法，详见notion笔记
// https://leetcode-cn.com/problems/linked-list-random-node/solution/lian-biao-sui-ji-jie-dian-by-leetcode-so-x6it/
public class ReservoirSampling {
        Random random;
        int[] reservior;
        int cnt;
        //k代表蓄水池大小
        public ReservoirSampling(int k) {
            reservior = new int[k];
            random = new Random();
            cnt = 0;
        }

        //有新数据到来时
        public void add(int x) {
            if(cnt<=reservior.length) {
                reservior[cnt++] =x;
            } else {
                // M/N的几率进入蓄水池
                int r = random.nextInt(cnt+1);
                if(r<reservior.length) {
                    reservior[r] = x;
                }
            }
        }

        public int[] getRandom() {
            return reservior;
        }
}
