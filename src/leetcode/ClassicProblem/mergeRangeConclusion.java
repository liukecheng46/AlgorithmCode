package leetcode.ClassicProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

// 区间问题总结
public class mergeRangeConclusion {
    // 合并区间 https://leetcode-cn.com/problems/merge-intervals/
    // 排序加双指针
    public int[][] merge(int[][] intervals) {
        List<int[]> ans = new ArrayList<>();
        Arrays.sort(intervals, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]);
        int start = intervals[0][0], end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= end) {//两个区间有重叠
                end = Math.max(end, intervals[i][1]);
            } else {
                //两个区间没有重叠，保存[start,end]，然后更新
                ans.add(new int[]{start, end});
                start = intervals[i][0];
                end = intervals[i][1];
            }
        }
        ans.add(new int[]{start, end});
        int[][] res = new int[ans.size()][2];
        for (int i = 0; i < res.length; i++) {
            res[i] = ans.get(i);
        }
        return res;
    }

    public class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    // 会议室2 https://cloud.tencent.com/developer/article/1659685 todo
    // 排序+堆
    // 堆个数表示申请了多少个会议室，堆顶表示最早结束的会议时间，如果不够就继续加，否则直接替换最早结束的会议（先pop再offer）
    public int minMeetingRooms(Interval[] intervals) {
        int n = intervals.length;
        Arrays.sort(intervals, (a, b) -> a.start - b.start);
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            if (i > 0 && intervals[i].start >= pq.peek()) pq.poll();
            pq.add(intervals[i].end);
        }
        return pq.size();
    }

    // 会议室2 还可以用差分数组，最大覆盖数量就是答案


    // 插入区间 https://leetcode-cn.com/problems/insert-interval/
    // 同理，排序+双指针
    class Solution {
        public int[][] insert(int[][] intervals, int[] newInterval) {
            int left = newInterval[0];
            int right = newInterval[1];
            boolean placed = false;
            List<int[]> ansList = new ArrayList<int[]>();
            for (int[] interval : intervals) {
                if (interval[0] > right) {
                    // 在插入区间的右侧且无交集
                    if (!placed) {
                        ansList.add(new int[]{left, right});
                        placed = true;
                    }
                    ansList.add(interval);
                } else if (interval[1] < left) {
                    // 在插入区间的左侧且无交集
                    ansList.add(interval);
                } else {
                    // 与插入区间有交集，计算它们的并集
                    left = Math.min(left, interval[0]);
                    right = Math.max(right, interval[1]);
                }
            }
            if (!placed) {
                ansList.add(new int[]{left, right});
            }
            int[][] ans = new int[ansList.size()][2];
            for (int i = 0; i < ansList.size(); ++i) {
                ans[i] = ansList.get(i);
            }
            return ans;
        }
    }

    // 删除被覆盖区间 https://leetcode-cn.com/problems/remove-covered-intervals/
    // 排序后贪心
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);

        int count = 0;
        int end, prev_end = 0;
        for (int[] curr : intervals) {
            end = curr[1];
            // if current interval is not covered
            // by the previous one
            if (prev_end < end) {
                ++count;
                prev_end = end;
            }
        }
        return count;
    }

    // 无重叠区间  https://leetcode-cn.com/problems/non-overlapping-intervals/
    // 按左边排序+指针
    // 还有一道基本一致的题目 用最少数量的箭引爆气球 https://leetcode-cn.com/problems/minimum-number-of-arrows-to-burst-balloons/
    public int eraseOverlapIntervals(int[][] intervals) {

        Arrays.sort(intervals, (a, b) -> {
            return Integer.compare(a[0], b[0]);
        });
        int remove = 0;
        int pre = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (pre > intervals[i][0]) {
                remove++;
                pre = Math.min(pre, intervals[i][1]);
            } else pre = intervals[i][1];
        }
        return remove;
    }

    //  区间列表的交集 https://leetcode-cn.com/problems/interval-list-intersections/
    //  两个列表，维护两个下标索引指针
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> res = new ArrayList<>();
        int i = 0, j = 0;
        while (i < firstList.length && j < secondList.length) {
            int left = Math.max(firstList[i][0], secondList[j][0]);
            int right = Math.min(firstList[i][1], secondList[j][1]);
            if (left <= right) res.add(new int[]{left, right});
            if (firstList[i][1] < secondList[j][1]) i++;
            else j++;
        }
        return res.toArray(new int[res.size()][]);
    }

    //  划分字母区间   https://leetcode-cn.com/problems/partition-labels/
    //  做的时候想的也是排序+维护指针的做法
    //  其实不需要记录每个字母开始的位置并按开始位置排序，只需要记录每个字母最后出现的位置; 因为遍历string就是按照每个字母开始位置的顺序进行的遍历
    public List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        int length = s.length();
        for (int i = 0; i < length; i++) {
            last[s.charAt(i) - 'a'] = i;
        }
        List<Integer> partition = new ArrayList<Integer>();
        int start = 0, end = 0;
        for (int i = 0; i < length; i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            // 如果当前i和end相同，则需要加入答案，
            if (i == end) {
                partition.add(end - start + 1);
                start = end + 1;
            }
        }
        return partition;
    }
}

