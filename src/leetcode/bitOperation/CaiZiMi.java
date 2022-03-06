package leetcode.bitOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 猜字谜 https://leetcode-cn.com/problems/number-of-valid-words-for-each-puzzle/
// 二进制状态压缩想到了，但是对于每个字谜，还是需要遍历所有单词，这里一定会超时，没有想到解决办法
// 破局关键： 字谜长度只有7，这个条件非常关键，所以对于每个二进制表示的字谜，与其遍历所有单词看是否包含，不如枚举这个7位字符串的所有子集(2^6)，从遍历所有单词的10^5降低到2^6
// 枚举子集的方法有很多，深究移步 leetcode78.子集 https://leetcode-cn.com/problems/subsets/
public class CaiZiMi {
        public List<Integer> findNumOfValidWords(String[] ws, String[] ps) {
            // 转用 「哈希表」来统计出所有的 word 所对应的二进制数值
            Map<Integer, Integer> map = new HashMap<>();
            for (String w : ws) {
                int t = getBin(w);
                map.put(t, map.getOrDefault(t, 0) + 1);
            }
            // 判定每个 puzzle 有多少个谜底
            List<Integer> ans = new ArrayList<>();
            for (String p : ps) ans.add(getCnt(map, p));
            return ans;
        }
        int getCnt(Map<Integer, Integer> map, String str) {
            int ans = 0;
            int m = str.length();
            char[] cs = str.toCharArray();
            // 当前 puzzle 的首个字符在二进制数值中的位置
            int first = cs[0] - 'a';
            // 枚举「保留首个字母」的所有子集
            // 即我们需要先固定 puzzle 的首位字母，然后枚举剩余的 6 位是否保留
            // 由于是二进制，每一位共有 0 和 1 两种选择，因此共有 2^6 种可能性，也就是 2^6 = 1 << (7 - 1) = 64 种
            // i 代表了所有「保留首个字母」的子集的「后六位」的二进制表示 (m=7)
            for (int i = 0; i < (1 << (m - 1)); i++) {
                // u 代表了当前可能的谜底。先将首字母提取出来
                int u = 1 << first;
                // 枚举「首个字母」之后的每一位
                for (int j = 1; j < m; j++) {
                    // 如果当前位为 1，代表该位置要保留，将该位置的字母追加到谜底 u 中
                    if (((i >> (j - 1)) & 1) != 0) u |= 1 << (cs[j] - 'a');
                }
                // 查询这样的字符是否出现在 `words` 中，出现了多少次
                if (map.containsKey(u)) ans += map.get(u);
            }
            return ans;
        }
        // 将 str 所包含的字母用二进制标识
        // 如果 str = abz 则对应的二进制为 100...011 (共 26 位，从右往左是 a - z)
        int getBin(String str) {
            int t = 0;
            char[] cs = str.toCharArray();
            for (char c : cs) {
                // 每一位字符所对应二进制数字中哪一位
                int u = c - 'a';
                t |= 1 << u;
            }
            return t;
        }


}
