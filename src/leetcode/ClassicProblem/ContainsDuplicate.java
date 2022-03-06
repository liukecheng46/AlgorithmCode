package leetcode.ClassicProblem;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

// 存在重复元素3 是否有k范围内的元素绝对值之差小于t
// https://leetcode-cn.com/problems/contains-duplicate-iii/
// 两种思路都很好。需要掌握运用
// 1. 需要找一个支持添加和删除指定元素的数据结构，并且还能做到有序且进行范围判断（找到第一个大于或小于给定值的元素） -> TreeSet
// 2. 桶的思想，桶大小为t+1，一个桶不可能有两个元素，对于新数，判断当前桶中有无元素后再与左右两个桶中元素计算是否在k范围内（hashpmap实现桶就可以）
public class ContainsDuplicate {
    // Treeset有序集合
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        int n = nums.length;
        TreeSet<Long> ts = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            Long u = nums[i] * 1L;
            // 从 ts 中找到小于等于 u 的最大值（小于等于 u 的最接近 u 的数）
            Long l = ts.floor(u);
            // 从 ts 中找到大于等于 u 的最小值（大于等于 u 的最接近 u 的数）
            Long r = ts.ceiling(u);
            if(l != null && u - l <= t) return true;
            if(r != null && r - u <= t) return true;
            // 将当前数加到 ts 中，并移除下标范围不在 [max(0, i - k), i) 的数（维持滑动窗口大小为 k）
            ts.add(u);
            if (i >= k) ts.remove(nums[i - k] * 1L);
        }
        return false;
    }

    //桶思想
    public boolean containsNearbyAlmostDuplicate2(int[] nums, int k, int t) {
        int n = nums.length;
        // key存桶的索引，value存值。 每个桶一定只有一个值，如果有两个已经返回true了
        HashMap<Long,Long> bucket = new HashMap<>();
        for(int i=0;i<n;i++) {
            long idx = getBucketIndex( nums[i],t);
            if (bucket.containsKey(idx)) {
                return true;
            }
            if (bucket.containsKey(idx - 1) && Math.abs(nums[i] - bucket.get(idx - 1)) < t) {
                return true;
            }
            if (bucket.containsKey(idx + 1) && Math.abs(nums[i] - bucket.get(idx + 1)) < t) {
                return true;
            }
            bucket.put(idx,(long)nums[i]);
            if(i>=k) {
                bucket.remove(getBucketIndex(nums[i - k], t));
            }
        }
        return false;
    }

    public long getBucketIndex(int x,int t) {
        if (x >= 0) {
            return x / t;
        }
        return (x + 1) / t - 1;
    }
}
