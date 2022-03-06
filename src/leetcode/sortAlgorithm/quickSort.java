package leetcode.sortAlgorithm;

import java.util.LinkedList;
import java.util.Random;

//单指针 双指针 三指针
// 版本 1：基本单指针***：把等于切分元素的所有元素分到了数组的同一侧，可能会造成递归树倾斜；
// 版本 2：双指针***：把等于切分元素的所有元素等概率地分到了数组的两侧，避免了递归树倾斜，递归树相对平衡；
// 版本 3：三指针***：聚拢pivot重复元素，把等于切分元素的所有元素挤到了数组的中间，在有很多元素和切分元素相等的情况下，递归区间大大减少。 写法和双指针不同，荷兰国旗问题就是用这个思路
// 版本4 ： 迭代写法
//快速选择 ： 选择第k个数，每次递归调用一次partition过程，但是只继续递归符合提交的一边，时间复杂度为O(N)，算法导论有证明
public class quickSort {
    public int[] quickSort(int[] arr) {
        int n = arr.length;
        threePointQuickSort(arr, 0, n - 1);
        return arr;
    }

    public void BasicQuickSort(int[] arr, int left, int right) {
        Random random = new Random();
        int baseIndex = random.nextInt(right - left + 1) + left;
//        将基准值放到最右边，然后从左边开始遍历
        swap(arr, right, baseIndex);
        int pivot = arr[right];
        int index = left;
        for (int i = left; i <= right - 1; i++) {
//            等于pivot的值都在右侧，可能造成不平衡
            if (arr[i] < pivot) {
                swap(arr, index, i);
                index++;
            }
        }
        swap(arr, index, right);
        BasicQuickSort(arr, left, index - 1);
        BasicQuickSort(arr, index + 1, right);

    }

    public void twoPointQuickSort(int[] arr, int left, int right) {
        Random random = new Random();
        int baseIndex = random.nextInt(right - left + 1) + left;
        swap(arr, left, baseIndex);
        int pivot = arr[left];
        int i = left, j = right;
        while (i < j) {
//            子while循环也要判断i<j, 并且要么带等于号要么在交换后都要++(防止i和j都与pivot相同而进入死循环)
            while (i < j && arr[j] >= pivot) j--;
            while (i < j && arr[i] <= pivot) i++;
            swap(arr, i, j);
//            i++;
//            j++;
        }
        swap(arr, left, j);
        twoPointQuickSort(arr, left, j - 1);
        twoPointQuickSort(arr, j + 1, right);

    }

    public void threePointQuickSort(int[] arr, int left, int right) {
        Random random = new Random();
        int baseIndex = random.nextInt(right - left + 1) + left;
        swap(arr, left, baseIndex);
        int pivot = arr[left];
        int lt = left, i = left + 1, j = right;
        while (i <= j) {
            if (arr[i] > pivot) {
                swap(arr, i, j);
//                这里只用j--，不用i++，因为换过来的可能也大于pivot
                j--;
            } else if (pivot == arr[i]) {
                i++;
            } else {
                swap(arr, lt, i);
                lt++;
//                这里需要i++，因为从前面换过来的一定小于pivot；
                i++;
            }
        }
//        基准值在循环中基于lt和i交换，最终时已经在lt到i之间，所以不需要swap回来
//        swap(arr,left,lt);
        twoPointQuickSort(arr, left, lt - 1);
        twoPointQuickSort(arr, j + 1, right);
    }


    public void swap(int[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }


    // 迭代写法 待掌握 todo
    public int[] sortArray(int[] nums) {
        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(nums.length - 1);
        stack.push(0);
        while (!stack.isEmpty()) {
            int left = stack.pop();
            int right = stack.pop();

            if (left < right) {
                int pivotIdx = partition(nums, left, right);
                stack.push(pivotIdx - 1);
                stack.push(left);
                stack.push(right);
                stack.push(pivotIdx + 1);
            }

        }
        return nums;
    }

    public int partition(int[] nums, int left, int right) {
        if (left >= right) return left;

        int pivot = nums[left];

        while (left < right) {
            while (left < right && pivot <= nums[right]) right--;
            nums[left] = nums[right];
            while (left < right && pivot >= nums[left]) left++;
            nums[right] = nums[left];
        }
        nums[left] = pivot;

        return left;
    }

}
