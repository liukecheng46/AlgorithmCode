package leetcode;

import java.util.ArrayList;
import java.util.List;

public class panCake {
    public List<Integer> pancakeSort(int[] arr) {
        List<Integer> res = new ArrayList<>();
        int n = arr.length;
        int lastIndex = n-1;
        while(lastIndex>=1) {
            int cur=-1;
            for(int i=lastIndex;i>=0;i--) {
                if(arr[i]==lastIndex+1) {
                    cur=i;
                    break;
                }
            }
            reverse(arr,cur);
            res.add(cur+1);
            reverse(arr,lastIndex);
            res.add(lastIndex+1);
            lastIndex--;
        }
        return res;
    }

    public void reverse(int[] arr,int k) {
        int left=0,right=k;
        while(left<right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}
