package leetcode;

import java.util.Arrays;

public class numFriendRequests {
    public int numFriendRequests(int[] ages) {
        Arrays.sort(ages);
        int n = ages.length, ans = 0;
        int left = 0,right = 0;
        for (int age:ages) {
            if(age<15) {
                continue;
            }
            while(ages[left]<= age*0.5 +7) {
                left++;
            }
            while(right+1<n && ages[right]< age) {
                right++;
            }
            right--;
            ans += right - left;
        }
        return ans;
    }

}
