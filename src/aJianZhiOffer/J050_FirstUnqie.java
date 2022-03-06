package aJianZhiOffer;

public class J050_FirstUnqie {
    public char firstUniqChar(String s) {
        int[] arr = new int[26];
        char[] chars = s.toCharArray();
        for (char ch : chars){
            arr[ch -'a'] ++;
        }
        for (char c:chars){
            if (arr[c-'a'] == 1){
                return c;
            }
        }
        return ' ';
    }
}
