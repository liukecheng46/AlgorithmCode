package leetcode.BinarySearch;

// 二分搜索答案范围，check函数进行判断的一类题目()
public class binarySearchAnswerRange {
    //制作m束花所需要的最少天数 https://leetcode-cn.com/problems/minimum-number-of-days-to-make-m-bouquets/
    // 在最小天数和最大天数之间二分，用findfirst的二分模板
    public int minDays(int[] bloomDay, int m, int k) {
        if(m*k>bloomDay.length) return -1;
        int left=Integer.MAX_VALUE,right=Integer.MIN_VALUE;
        for(int x:bloomDay) {
            left= Math.min(x, left);
            right = Math.max(x,right);
        }
        while(left<right) {
            int mid = (left+right)/2;
            if(check(bloomDay,m,k,mid)) right=mid;
            else left=mid+1;
        }
        return left;
    }

    public boolean check(int[] bloomDay,int m,int k, int day) {
        int count=0;
        int cur=0;
        for(int x:bloomDay) {
            if(day>=x) {
                cur++;
                if(cur==k) {
                    count++;
                    cur=0;
                }
            } else cur=0;
        }
        return count>=m;
    }


    // D天内送达包裹的能力 https://leetcode-cn.com/problems/capacity-to-ship-packages-within-d-days/
    public int shipWithinDays(int[] weights, int days) {
        int left = Integer.MIN_VALUE,right = 0;
        for(int weight:weights) {
            left = Math.max(left,weight);
            right+=weight;
        }

        while(left<right) {
            int mid = (left+right)/2;
            if(check2(weights,mid,days)) right=mid;
            else left=mid+1;
        }
        return left;
    }

    public boolean check2(int[] weights,int weight,int days) {
        int count=1;
        int cur=0;
        for(int w:weights) {
            if(cur+w<=weight) cur+=w;
            else {
                count++;
                cur=w;
            }
        }
        return count<=days;
    }
}
