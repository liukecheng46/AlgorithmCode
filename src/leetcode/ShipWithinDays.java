package leetcode;

import java.util.Arrays;

// 二分查找上下限
// https://leetcode-cn.com/problems/capacity-to-ship-packages-within-d-days/
public class ShipWithinDays {
    public int shipWithinDays(int[] weights, int days) {
        int right = Arrays.stream(weights).sum();
        int left = Arrays.stream(weights).max().getAsInt();
        while(left<right) {
            int mid = (left+right)>>1;
            if(check(weights,days,mid)) right = mid;
            else left = mid+1;
        }
        return right;
    }

    public boolean check(int[] weights,int days,int mid) {
        int current = mid;
        for(int i=0;i<weights.length;i++) {
            if(current-weights[i]>=0) {
                current-=weights[i];
            } else {
                current = mid;
                days--;
                current-=weights[i];
            }
            if(days==0) return false;
        }
        return true;
    }
}
