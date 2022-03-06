package leetcode.SlidingWindow;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// 经典滑动窗口
// https://leetcode-cn.com/problems/minimum-window-substring/
public class minWindow {
    public String minWindows(String s, String t) {
        // 哈希表也可以用数组[128]优化
        Map<Character, Integer> source = new HashMap<>();
        Map<Character, Integer> target = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            Character x = t.charAt(i);
            target.put(x, target.getOrDefault(x, 0) + 1);
        }
        int j = 0, cnt = 0;
        int min = Integer.MAX_VALUE;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            Character x = s.charAt(i);
            if (target.containsKey(x)) {
                source.put(x, source.getOrDefault(x, 0) + 1);
                if (Objects.equals(source.get(x), target.get(x))) cnt++;
            }
            while (cnt == target.size()) {
                if (i - j + 1 < min) {
                    min = i - j + 1;
                    start = j;
                }
                min = Math.min(min, i - j + 1);
                if (target.containsKey(s.charAt(j)) && Objects.equals(source.get(s.charAt(j)), target.get(s.charAt(j)))) {
                    cnt--;
                }
                if (target.containsKey(s.charAt(j))) {
                    source.put(s.charAt(j), source.get(s.charAt(j)) - 1);
                }
                j++;
            }
        }
        return min == Integer.MAX_VALUE ? "" : s.substring(start, start + min);

    }
}
