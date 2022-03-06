package leetcode.dfsBrackTracePrune;

import java.util.ArrayList;
import java.util.List;

// 分割回文子串 https://leetcode-cn.com/problems/palindrome-partitioning/
// 回溯模板题，还可以优化一下： dp[i][j]预处理一下每个子串是否回文，这样每次调用check函数由O（n）降低至O（1）
public class PartitionPalindrome {
    List<List<String>> res;
    List<String> sub;
    public List<List<String>> partition(String s) {
        res = new ArrayList<>();
        sub = new ArrayList<>();
        dfs(s,0);
        return res;
    }

    public void dfs(String s, int start) {
        if(start == s.length()) res.add(new ArrayList<>(sub));
        for(int i= start+1;i<=s.length();i++) {
            String ss = s.substring(start,i);
            if(check(ss)) {
                sub.add(ss);
                dfs(s,i);
                sub.remove(sub.size()-1);
            }
        }
    }

    public boolean check(String s) {
        int l=0,r = s.length()-1;
        while(l<r) {
            if(s.charAt(l)!=s.charAt(r)) return false;
            l++;
            r--;
        }
        return true;
    }
}
