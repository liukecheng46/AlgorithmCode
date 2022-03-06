package leetcode.ClassicProblem;

import java.util.*;

// 当字符都无重复时，可以将LCS问题通过映射转化为LIS问题来优化时间复杂度（O（nm）-> O(mlogm)）
// https://leetcode-cn.com/problems/minimum-operations-to-make-a-subsequence/solution/gong-shui-san-xie-noxiang-xin-ke-xue-xi-oj7yu/
public class LCStoLIS {
    // 朴素的想法是求target与arr的最长公共子序列lcs, 然后用target.len() - lcs.len()即可
    // 但题目中给出的数据范围target.len() 与 arr.len()的长度n可能到达10^5, 无法用O(n^2)的动态规划解法
    // 这时候就体现出了困难题的困难, 需要十分巧妙的转化(说不定还要转化很多次)将其转换为我们熟知的类型
    // 看例1:
    // tar = [5, 1, 3],  arr = [9, 4, 2, 3, 4]
    // 因为(1). 只涉及给arr'增加'元素。 (2). 最终答案与arr的长度也无关。
    // 据此我们可以把arr中出现的, 且在tar中不存在的元素去掉, 其并不会有什么影响
    // tar = [5, 1, 3], arr' = [3]
    // 这时候我心里有了些感觉, 但例1元素太少还是不够直观, 我们再用例2试一次
    // 例2:
    // tar = [6, 4, 8, 1, 3, 2],  arr = [4, 7, 6, 2, 3, 8, 6, 1]
    // tar = [6, 4, 8, 1, 3, 2], arr' = [4, 6, 2, 3, 8, 6, 1]
    // 这时候注意到tar中元素无重复, 这个性质就像是索引一样, 我们当然就可以把他们当成索引, 得到一个新性质: 有序
    // 其实tar本身就像是一种自定义的有序集合: 我们可以人为设定 4比6大(或小), 8比4大(或小) 等等
    // 但为了方便后续的比较使用, 我们可以让其每个数字映射其对应的数组下标:
    // idx = [0, 1, 2, 3, 4, 5]
    // tar = [6, 4, 8, 1, 3, 2]
    // (tar -> idx) = [(6 -> 0), (4 -> 1), (8 -> 2), (1 -> 3), (3 -> 4), (2 -> 5)]
    // 这样, arr' = [4, 6, 2, 3, 8, 6, 1] -> [1, 0, 5, 4, 2, 0, 3]
    // 经过上述变换, 相当于我们把原始的tar与arr分别变为
    // tar' = [0, 1, 2, 3, 4, 5]
    // arr' = [1, 0, 5, 4, 2, 0, 3]
    // 其中tar'是递增的顺序集合, 而arr'是一种乱序集合
    // 这时候我们只需要找到arr'中最长的符合严格单调递增性质的子序列lis长度即可
    public int minOperations(int[] t, int[] arr) {
        int n = t.length, m = arr.length;
        Map<Integer, Integer> map = new HashMap<>();
        // 将target中的数按下标映射
        for (int i = 0; i < n; i++) {
            map.put(t[i], i);
        }
        //去除target中没有的数，因为对后续无影响。并且同时转化为下标索引值
        List<Integer> list = new ArrayList<>();
        for (int x : arr) {
            if (map.containsKey(x)) list.add(map.get(x));
        }
        int len = list.size();
        int[] f = new int[len];
        for(int i=0;i<len;i++) {
            f[i] = list.get(i);
        }
        int max = LIS(f);
        return n - max;
    }

    //LIS
    public int LIS(int[] nums) {
        int n = nums.length;
        int[] tails = new int[n];
        int LISLength = 0;
        // LCStoLIS.java有另一种LIS的写法
        for(int x:nums) {
            int left = 0, right = LISLength;
            while(left<right) {
                int mid = (left+right)>>1;
                if(tails[mid]>=x) right = mid;
                else left=mid+1;
            }
            //将第一个满足tails[i]>x的数更新为x,包含了下述两种特殊情况
            //如果x比tail中所有数都小，更新第一个，此时right=0
            //如果x比tail中所有数都大，更新最后一个，此时right=LISLengthm
            tails[right] = x;
            //如果都大的话，意味着LISLength可以加一
            if(right == LISLength) LISLength++;
        }
        return LISLength;
    }

}
