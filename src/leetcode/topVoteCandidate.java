package leetcode;

import java.util.HashMap;
import java.util.Map;

//预处理+二分
public class topVoteCandidate {
    public int[] time;
    public int[] topCandidate;

    public topVoteCandidate(int[] persons, int[] times) {
        int n = persons.length;
        time = times;
        topCandidate = new int[n];
        Map<Integer, Integer> mp = new HashMap<>();
        int curTop = 0;
        for (int i = 0; i < n; i++) {
            int p = persons[i];
            mp.put(p, mp.getOrDefault(p, 0) + 1);
            if (mp.get(p) >= mp.get(curTop)) {
                curTop = p;
            }
            topCandidate[i] = curTop;
        }
    }

    public int q(int t) {
        int l = 0, r = time.length - 1;
        if(t>time[r]) return topCandidate[r];
        if(t<time[0]) return topCandidate[0];
        while (l<r) {
            int mid = (l+r)>>1;
            if(time[mid]>=t) r=mid;
            else l=mid+1;
        }
        return time[r]==t? topCandidate[r]:topCandidate[r-1];
    }
}
