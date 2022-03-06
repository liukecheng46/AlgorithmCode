package leetcode;

import java.util.Arrays;

//leetcode475
public class findRadius {
    public static int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int n = houses.length;
        int m = heaters.length;
        int radius = 0;
        int index = 0;


        for (int i=0;i<n;i++) {
            while(houses[i]> heaters[index] && index<m-1) {
                index++;
            }
            int cur;

            if(index>0) {
                cur = Math.min(Math.abs(heaters[index]-houses[i]),Math.abs(heaters[index-1]-houses[i]));
            } else {
                cur = Math.abs(heaters[index]-houses[i]);
            }
            radius = Math.max(radius,cur);
        }
        return radius;
    }


}
