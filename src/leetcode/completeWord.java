package leetcode;

//https://leetcode-cn.com/problems/shortest-completing-word/
public class completeWord {
    public static String shortestCompletingWord(String licensePlate, String[] words) {
        int[] origin = compute(licensePlate);
        String res = null;
        for (String word: words) {
            int[] cur = compute(word);
            boolean flag =true;
            for (int i=0;i<26;i++) {
                if(origin[i]>cur[i]) {
                    flag = false;
                }
            }
            if (flag && (res == null || res.length()>word.length())) res = word;
        }
        return res;
    }

    public static int[] compute(String string) {
        int[] res = new int[26];
        for (int i=0;i<string.length();i++) {
            if (Character.isLetter(string.charAt(i))) {
                res[Character.toLowerCase(string.charAt(i)) - 'a']++;
            }
        }
        return res;
    }

    public static String toLowerCase(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (ch >= 65 && ch <= 90) {
                ch += 32;
            }
            sb.append(ch);
        }
        return sb.toString();
    }
}
