package leetcode;

//双指针贪心
//https://leetcode-cn.com/problems/container-with-most-water/
public class maxArea {
    public int maxArea(int[] height) {
        int n = height.length;
        int left = 0, right = n-1;
        int max = 0;
        while(left<right) {
            max = Math.max(max,(right-left)*Math.min(height[left],height[right]));
            if (height[left] <= height[right]) {
                ++left;
            }
            else {
                --right;
            }
        }
        return max;
    }
}
