package leetcode.StringAndRegex;


import java.util.regex.Pattern;

// https://leetcode-cn.com/problems/number-of-valid-words-in-a-sentence/
// 字符串模拟或者正则
// 知识点： 按一个或多个空格分割字符串(String.split("\\s+"))，判断数字，字母，正则
public class countValidWord {

    // 正则写法
    private static final Pattern PATTERN = Pattern.compile("^(([a-z]+[-]?[a-z]+)|([a-z]*))[!.,]?$");

    public int countValidWords(String sentence) {
        int cnt = 0;
        String[] tokens = sentence.split(" ");
        for (String token : tokens) {
            if (PATTERN.matcher(token).matches() && token.length() != 0) {
                cnt++;
            }
        }
        return cnt;
    }

    class Solution {
        public int countValidWords(String sentence) {
            int n = sentence.length();
            int l = 0, r = 0;
            int ret = 0;
            while (true) {
                while (l < n && sentence.charAt(l) == ' ') {
                    l++;
                }
                if (l >= n) {
                    break;
                }
                r = l + 1;
                while (r < n && sentence.charAt(r) != ' ') {
                    r++;
                }
                if (isValid(sentence.substring(l, r))) { // 判断根据空格分解出来的 token 是否有效
                    ret++;
                }
                l = r + 1;
            }
            return ret;
        }

        public boolean isValid(String word) {
            int n = word.length();
            boolean hasHyphens = false;
            for (int i = 0; i < n; i++) {
                //判断是否数字
                if (Character.isDigit(word.charAt(i))) {
                    return false;
                } else if (word.charAt(i) == '-') {
                    // 判断是否字母，最好用Character.isLowerCase()和Character.isUpperCase()
                    if (hasHyphens == true || i == 0 || i == n - 1 || !Character.isLetter(word.charAt(i - 1)) || !Character.isLetter(word.charAt(i + 1))) {
                        return false;
                    }
                    hasHyphens = true;
                } else if (word.charAt(i) == '!' || word.charAt(i) == '.' || word.charAt(i) == ',') {
                    if (i != n - 1) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

}
