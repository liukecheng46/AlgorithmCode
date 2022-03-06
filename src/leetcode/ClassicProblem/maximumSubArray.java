package leetcode.ClassicProblem;

//leetcode53 最大连续子数组和的分治写法，很经典
// 原题用动规做最好，并且可推及到不能连续的情况（leetcode198）
// 但是分治的解法 思想很重要，可以推及到很多问题
public class maximumSubArray {
    class Solution {
        public int maxSubArray(int[] nums) {
            return divideConquer(nums,0,nums.length-1);
        }

        public int divideConquer(int[] nums,int low,int high) {
            //递归出口
            if (low>=high){
                return nums[low];
            }
            //三种情况，第一种：数组在左区间
            //第二种：数组在又区间
            //第三种：数组横跨左右区间
            //前两种递归求解即可
            //第三种情况以中间位置向两周扩散，找到最大值
            int mid = low+(high-low)/2;
            int left = divideConquer(nums,low,mid-1);
            int right = divideConquer(nums,mid+1,high);
            int maxMid = nums[mid];
            int curMId = nums[mid];
            for (int i = mid-1;i>=low;i--){
                curMId += nums[i];
                if (maxMid<curMId) maxMid = curMId;
            }
            curMId = maxMid;
            for (int i = mid+1;i<=high;i++){
                curMId += nums[i];
                if (maxMid<curMId) maxMid = curMId;
            }
            //4.返回三种情况中的最大值
            return Math.max(Math.max(left,right),maxMid);
        }
    }
}
