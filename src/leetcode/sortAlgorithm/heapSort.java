package leetcode.sortAlgorithm;

//堆排序
// 建堆复杂度o(n),n-1次调整 每一次调整复杂度o(logn)，
// 空间复杂度为o（1），因为是数组原地调整
public class heapSort {
    public class Solution {

        public int[] sortArray(int[] nums) {
            int len = nums.length;
            // 将数组整理成堆
            heapify(nums);

            // 循环不变量：区间 [0, i] 堆有序
            for (int i = len - 1; i >= 1; ) {
                // 把堆顶元素（当前最大）交换到数组末尾
                swap(nums, 0, i);
                // 逐步减少堆有序的部分
                i--;
                // 下标 0 位置下沉操作，使得区间 [0, i] 堆有序
                siftDown(nums, 0, i);
            }
            return nums;
        }

        /**
         * 将数组整理成堆（堆有序）
         *
         * @param nums
         */
        private void heapify(int[] nums) {
            int len = nums.length;
            // 只需要从 i = (len - 1) / 2 这个位置开始逐层下移
            for (int i = (len - 1) / 2; i >= 0; i--) {
                siftDown(nums, i, len - 1);
            }
        }


        /**
         * @param nums
         * @param k    当前下沉元素的下标
         * @param end  [0, end] 是 nums 的有效部分
         *  大顶堆：左右子节点中大的那个如果比父节点大，那么swap（从上到下）
         */
        private void siftDown(int[] nums, int k, int end) {

            while (2 * k + 1 <= end) {
//                左孩子
                int j = 2 * k + 1;
                if (j + 1 <= end && nums[j + 1] > nums[j]) {
                    j++;
                }
                if (nums[j] > nums[k]) {
                    swap(nums, j, k);
                } else {
                    break;
                }
                k = j;
            }
        }

        private void swap(int[] nums, int index1, int index2) {
            int temp = nums[index1];
            nums[index1] = nums[index2];
            nums[index2] = temp;
        }
    }
}
