package aJianZhiOffer;

import java.util.HashMap;
import java.util.Map;

public class J048_LongestSubstring {
    Map<Character,Integer> indexMap = new HashMap<>();
    public int lengthOfLongestSubstring(String s) {
        int max=0;
        char[] cs = s.toCharArray();
        // 这个-1很关键
        for(int i=0,j=0;i<cs.length;i++) {
            if(!indexMap.containsKey(cs[i])) {
                max= Math.max(max,i-j+1);
                indexMap.put(cs[i],i);
            } else {
                j= Math.max(indexMap.get(cs[i])+1,j);
                // map包含当前值的情况，也需要比较max，应该可能这个值在窗口外面
                max= Math.max(max,i-j+1);
                indexMap.remove(cs[i]);
                indexMap.put(cs[i],i);
            }
        }
        return max;
    }

    public int lengthOfLongestSubstring2(String s) {
        int length = s.length();
        int max = 0;

        Map<Character, Integer> map = new HashMap<>();
        for(int start = 0, end = 0; end < length; end++){
            char element = s.charAt(end);
            if(map.containsKey(element)){
                start = Math.max(map.get(element) + 1, start); //map.get()的地方进行+1操作
            }
            max = Math.max(max, end - start + 1);
            map.put(element, end);
        }
        return max;
    }
}
