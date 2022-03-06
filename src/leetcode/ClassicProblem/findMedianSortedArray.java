package leetcode.ClassicProblem;

// 寻找两个有序数组的中位数 https://leetcode-cn.com/problems/median-of-two-sorted-arrays/submissions/
// 可以拓展为寻找两个有序数组中第k小的数， 或 n个有序数组中第k小的数（感觉应该可以）
// 还有一种高端解法，有空可以看看
public class findMedianSortedArray {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            double median = getKthSmallestNumInSortedArray(nums1, nums2, midIndex + 1);
            return median;
        } else {
            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
            double median = (getKthSmallestNumInSortedArray(nums1, nums2, midIndex1 + 1) + getKthSmallestNumInSortedArray(nums1, nums2, midIndex2 + 1)) / 2.0;
            return median;
        }

    }

    public double getKthSmallestNumInSortedArray(int[] nums1, int[] nums2, int k) {
        int n = nums1.length, m = nums2.length;
        int index1 = 0, index2 = 0;
        int remainK = k;
        while (true) {
            if (index1 == n ) return nums2[index2 + remainK - 1];
            if (index2 == m ) return nums1[index1 + remainK - 1];
            if (remainK == 1) return Math.min(nums1[index1], nums2[index2]);
            int cur = remainK / 2;
            int newIndex1 = Math.min(n - 1, index1+cur-1);
            int newIndex2 = Math.min(m-1,index2+cur-1);
            if(nums1[newIndex1]<=nums2[newIndex2]) {
                // 接下来两行要+1
                remainK-=newIndex1-index1+1;
                index1 = newIndex1+1;
            } else {
                remainK-=newIndex2-index2+1;
                index2 = newIndex2+1;
            }
        }
    }
}
