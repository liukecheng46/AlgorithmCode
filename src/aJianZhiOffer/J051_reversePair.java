package aJianZhiOffer;

public class J051_reversePair {
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    public int mergeSort(int[] nums, int left, int right) {
        if (left >= right) return 0;
        int mid = (left + right) / 2;
        int count = mergeSort(nums, left, mid) + mergeSort(nums, mid + 1, right);
        int l = left, r = mid + 1;
        int[] temp = new int[right - left + 1];
        int index = 0;
        int cur = 0;
        while (l <= mid && r <= right) {
            if (nums[l] > nums[r]) {
                temp[index++] = nums[r++];
                cur += mid-l+1;
            } else {
                temp[index++] = nums[l++];
            }
        }
        while (r <= right) {
            temp[index++] = nums[r++];
        }
        while (l <= mid) {
            temp[index++] = nums[l++];
        }

        for (int k = 0; k < right - left + 1; ++k) {
            nums[k + left] = temp[k];
        }
        return count + cur;
    }
}
