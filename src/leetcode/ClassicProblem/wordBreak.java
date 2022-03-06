package leetcode.ClassicProblem;

import java.util.*;
import java.util.stream.Collectors;

// 单词拆分
// 单词拆分2不卡时间，标准回溯就能过，单词拆分1反而需要加记忆化或者用完全背包的dp解法
public class wordBreak {
    HashSet<Integer> indexSet;
    List<String> wordDict;

    //解法一： 记忆化递归，因为只需要判断能否划分，每个索引如果之前遍历过了那么之后就直接跳过
    public boolean wordBreak(String s, List<String> wordDict_) {
        indexSet = new HashSet<>();
        wordDict = wordDict_;
        return dfs(s, 0);
    }

    //start表示的是从字符串s的哪个位置开始
    public boolean dfs(String s, int start) {
        //字符串都拆分完了，返回true
        if (start == s.length())
            return true;
        for (int i = start + 1; i <= s.length(); i++) {
            //如果已经判断过了，就直接跳过，防止重复判断
            if (indexSet.contains(i))
                continue;
            //截取子串，判断是否是在字典中
            if (wordDict.contains(s.substring(start, i))) {
                if (dfs(s, i))
                    return true;
                //标记为已判断过
                indexSet.add(i);
            }
        }
        return false;
    }

    //解法2： 动态递归，因为只需要判断能否划分，因此可以使用n^2的动态规划（单词可以重复使用，归化为完全背包问题）
    public boolean wordBreak2(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }


    // 单词划分2 需要输出所有情况，因此要用到回溯 https://leetcode-cn.com/problems/word-break-ii/submissions/
    // 标准回溯模板
    List<String> result = new ArrayList<>();
    List<String> list = new ArrayList<>();
    HashSet<String> dictionary = new HashSet<>();

    public List<String> wordBreak4(String s, List<String> wordDict) {
        dictionary.addAll(wordDict);
        backtrack(s, 0);
        return result;
    }

    private void backtrack(String s, int start) {
        if (start == s.length()) {
            result.add(String.join(" ", list));
        }

        for (int i = start; i < s.length(); ++i) {
            String sub = s.substring(start, i + 1);
            if (dictionary.contains(sub)) {
                list.add(sub);
                backtrack(s, i + 1);
                list.remove(list.size() - 1);
            }
        }
    }

    // 连接词 单词划分系列的终极题目 https://leetcode-cn.com/problems/concatenated-words/
    // 卡时间卡的很死，dfs不行，必须加上字典树优化-前缀不匹配时直接退出，而不是遍历所有可能性。并且字典树也要加上记忆化
    // dfs+记忆化好像可以？
    // 最优解是字典树+dfs+记忆化
    // 还有一种哈希优化的方式-rolling hash，这样省去每次substring的o（n）开销， https://leetcode-cn.com/problems/concatenated-words/solution/gong-shui-san-xie-xu-lie-dpzi-fu-chuan-h-p7no/

    //dfs+记忆化
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> ans = new ArrayList<>();
        Set<String> set = Arrays.stream(words).collect(Collectors.toSet());
        Map<Integer, Boolean> memo = new HashMap<>();
        for (String word : words) {
            memo.clear();
            if (!word.isEmpty() && dfs(word, memo, set, 0)) {
                ans.add(word);
            }
        }
        return ans;
    }

    private boolean dfs(String str, Map<Integer, Boolean> memo, Set<String> set, int start) {
        if (start == str.length()) return true;
        if (memo.containsKey(start)) return memo.get(start);
        int len = str.length();
        int max = start == 0 ? len - 1 : len;
        for (int i = start + 1; i <= max; i++) {
            if (set.contains(str.substring(start, i)) && dfs(str, memo, set, i)) {
                memo.put(start, true);
                return true;
            }
        }
        memo.put(start, false);
        return false;
    }



    //字典树+dfs+记忆化 todo
    class Solution {
        Trie trie = new Trie();

        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            List<String> ans = new ArrayList<String>();
            // 排序：长单词只能由短单词构成，所以升序后边遍历边将入字典树，减少开销
            Arrays.sort(words, (a, b) -> a.length() - b.length());
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (word.length() == 0) {
                    continue;
                }
                boolean[] visited = new boolean[word.length()];
                if (dfs(word, 0, visited)) {
                    ans.add(word);
                } else {
                    insert(word);
                }
            }
            return ans;
        }

        public boolean dfs(String word, int start, boolean[] visited) {
            if (word.length() == start) {
                return true;
            }
            if (visited[start]) {
                return false;
            }
            visited[start] = true;
            Trie node = trie;
            for (int i = start; i < word.length(); i++) {
                char ch = word.charAt(i);
                int index = ch - 'a';
                node = node.children[index];
                // 字典树比哈希表快的地方，如果前缀不存在，那么可以提前停止
                if (node == null) {
                    return false;
                }
                if (node.isEnd) {
                    if (dfs(word, i + 1, visited)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void insert(String word) {
            Trie node = trie;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                int index = ch - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new Trie();
                }
                node = node.children[index];
            }
            node.isEnd = true;
        }
    }

    class Trie {
        Trie[] children;
        boolean isEnd;

        public Trie() {
            children = new Trie[26];
            isEnd = false;
        }
    }


}
