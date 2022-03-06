package leetcode.ClassicProblem;

import java.util.Arrays;
import java.util.HashMap;

// 至少有k个重复字符的最长子串 https://leetcode-cn.com/problems/longest-substring-with-at-least-k-repeating-characters/ todo
// 这道题很妙 分治和滑动窗口两种解法都和常规思路上的分治和滑窗不同，需要学习
// 做题的时候只想到常规思路的滑动窗口，但是觉得不行（确实不行）
public class KRepitedSubstring {
    // 分治：如果某个位置的字符在当前整个字符串中出现次数大于0小于k，那么最大子串一定在它的左边子串或者右边子串里
    public int longestSubstring(String s, int k) {
        int n = s.length();
        return dfs(s, 0, n - 1, k);
    }

    public int dfs(String s, int l, int r, int k) {
        int[] cnt = new int[26];
        for (int i = l; i <= r; i++) {
            cnt[s.charAt(i) - 'a']++;
        }

        char split = 0;
        // 找到第一个不符合条件的字符
        for (int i = 0; i < 26; i++) {
            if (cnt[i] > 0 && cnt[i] < k) {
                split = (char) (i + 'a');
                break;
            }
        }
        //如果所有字符都符合，那么返回
        if (split == 0) {
            return r - l + 1;
        }

        int i = l;
        int ret = 0;
        // 对于分割字符的所有位置，都进行分治并进行下一次dfs
        // 其实用split()更简单
        while (i <= r) {
            while (i <= r && s.charAt(i) == split) {
                i++;
            }
            if (i > r) {
                break;
            }
            int start = i;
            while (i <= r && s.charAt(i) != split) {
                i++;
            }

            int length = dfs(s, start, i - 1, k);
            ret = Math.max(ret, length);
        }
        return ret;
    }

    //分治简单写法
    public int longestSubstring2(String s, int k) {
        if (s.length() < k) return 0;
        HashMap<Character, Integer> counter = new HashMap();
        for (int i = 0; i < s.length(); i++) {
            counter.put(s.charAt(i), counter.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (char c : counter.keySet()) {
            if (counter.get(c) < k) {
                int res = 0;
                for (String t : s.split(String.valueOf(c))) {
                    res = Math.max(res, longestSubstring2(t, k));
                }
                return res;
            }
        }
        return s.length();
    }

    // 滑窗
    // 常规滑窗是不可行的，但是这里有另一个性质：元素只包括小写字母，即最多26种。因此可以枚举滑窗包括的元素种类数量，如果滑窗元素种类数量超过限定数量，左边界右移，否则右边界右移
    public int longestSubstring3(String s, int k) {
        int ans = 0;
        int n = s.length();
        char[] cs = s.toCharArray();
        int[] cnt = new int[26];
        // 枚举滑动窗口包含的元素种类数量
        for (int p = 1; p <= 26; p++) {
            Arrays.fill(cnt, 0);
            // tot 代表 [j, i] 区间所有的字符种类数量；sum 代表满足「出现次数不少于 k」的字符种类数量
            for (int i = 0, j = 0, tot = 0, sum = 0; i < n; i++) {
                int u = cs[i] - 'a';
                cnt[u]++;
                // 如果添加到 cnt 之后为 1，说明字符总数 +1
                if (cnt[u] == 1) tot++;
                // 如果添加到 cnt 之后等于 k，说明该字符从不达标变为达标，达标数量 + 1
                if (cnt[u] == k) sum++;
                // 当区间所包含的字符种类数量 tot 超过了当前限定的数量 p，那么我们要删除掉一些字母，即「左指针」右移
                while (tot > p) {
                    int t = cs[j++] - 'a';
                    cnt[t]--;
                    // 如果添加到 cnt 之后为 0，说明字符总数-1
                    if (cnt[t] == 0) tot--;
                    // 如果添加到 cnt 之后等于 k - 1，说明该字符从达标变为不达标，达标数量 - 1
                    if (cnt[t] == k - 1) sum--;
                }
                // 当所有字符都符合要求，更新答案
                if (tot == sum) ans = Math.max(ans, i - j + 1);
            }
        }
        return ans;
    }

}
