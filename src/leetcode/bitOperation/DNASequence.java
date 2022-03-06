package leetcode.bitOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//滑动窗口+哈希表时间复杂度是O（nk），用位运算将k优化掉
// 没有什么很巧妙的位运算奇淫技巧，但是对位运算运算基本功有要求
public class DNASequence {
    Map<Character, Integer> bin = new HashMap<Character, Integer>() {{
        put('A', 0);
        put('C', 1);
        put('G', 2);
        put('T', 3);
    }};


    public List<String> findRepeatedDnaSequences(String s) {
        int window = 0;
        List<String> ans = new ArrayList<String>();
        Map<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        for (int i = 0; i < s.length(); i++) {
            window = window << 2;
            window |= bin.get(s.charAt(i));
            if(i>=10) {
                window = window & (1<<20-1);
            }
            cnt.put(window, cnt.getOrDefault(window, 0) + 1);
            if (cnt.get(window) == 2) {
                ans.add(String.valueOf(window));
            }
        }
        return ans;
    }
}
