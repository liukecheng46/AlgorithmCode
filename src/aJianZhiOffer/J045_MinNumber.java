package aJianZhiOffer;

import java.util.Arrays;

//没想到这个贪心，两个不等长的x和y比较应该是比较x+y和y+x todo
public class J045_MinNumber {
    public String minNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for(int i = 0; i < nums.length; i++)
            strs[i] = String.valueOf(nums[i]);
        Arrays.sort(strs, (x, y) -> (x + y).compareTo(y + x));
        StringBuilder res = new StringBuilder();
        for(String s : strs)
            res.append(s);
        return res.toString();
    }
}
