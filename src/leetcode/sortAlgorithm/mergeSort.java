package leetcode.sortAlgorithm;

//https://leetcode-cn.com/problems/sort-an-array/solution/fu-xi-ji-chu-pai-xu-suan-fa-java-by-liweiwei1419/
//小区间使用插入排序(插入排序在接近有序的数组快，在小数组也很快)
//判断子数组是否有序，有序则跳过
//迭代写法： 合并时还是需要用到辅助数组，所以空间不是O（1） https://zhuanlan.zhihu.com/p/159636662
//原地归并 手摇算法内存反转优化合并过程 时间复杂度O(nlog2n) 空间复杂度O(1) https://blog.csdn.net/ACdreamers/article/details/24244643
//链表的归并 sortList。java
//k个链表数组的归并 KListNodeMerge.java
////外部归并externel mergesort： https://zhuanlan.zhihu.com/p/343986766
public class mergeSort {
    int[] temp;

    public int[] mergeSort(int[] arr) {
        temp = new int[arr.length];
        sort(arr, 0, arr.length - 1);
        return arr;
    }

    public void sort(int[] arr, int left, int right) {
//        等号即一个元素时也直接返回,这里可能大于，所以不能只取等于
        if (left >= right) {
            return;
        }
        int mid = (left + right) >> 1;
        sort(arr, left, mid);
        sort(arr, mid + 1, right);
        // 如果数组的这个子区间本身有序，无需合并
        if (arr[mid] <= arr[mid + 1]) {
            return;
        }

        merge(arr, left, mid, right);
    }

    //    归并
    public void merge(int[] arr, int left, int mid, int right) {
        int i = left, j = mid + 1, index = 0;
        while (i <= mid && j <= right) {
//            如果不加等于号就丢失了稳定性-相同元素原来靠前的排序以后依然靠前
            if (arr[i] <= arr[j]) {
                temp[index++] = arr[i++];
            } else {
                temp[index++] = arr[j++];
            }
        }
        while (i <= mid) {
            temp[index++] = arr[i++];
        }
        while (j <= right) {
            temp[index++] = arr[j++];
        }

//        使在下个merge时arr子区间有序
        for (int k = 0; k < right - left + 1; ++k) {
            arr[k + left] = temp[k];
        }
    }

//    迭代版本的归并排序(还是要使用辅助数组，所以不是O（1）)
    public int[] sortIterate(int arr[])
    {
        int n = arr.length;
        temp = new int[n];
        int curr_size; //标识当前合并的子数组的大小，从 1 已知到 n/2
        int left_start; //标识当前要合并的子数组的起点
        for (curr_size = 1; curr_size <= n-1; curr_size = 2*curr_size)
        {
            for (left_start = 0; left_start < n-1; left_start += 2*curr_size)
            {
                int mid = Math.min(left_start + curr_size - 1, n-1);

                int right_end = Math.min(left_start + 2*curr_size - 1, n-1);

                merge(arr, left_start, mid, right_end);
            }
        }
        return arr;
    }

}
