package leetcode.sortAlgorithm;

import java.util.Arrays;
import java.util.Random;

// 快速选择第k个小的数，快排改一下，时间复杂度是O(N)，证明在算法导论
public class quickSelect {

    public int quickSelect(int[] arr,int k) {
        //等于时也直接返回
        if(k>= arr.length) return -1;
        return quickPartition(arr,k,0,arr.length-1);
    }

//    找第k小
    public int quickPartition(int[] arr, int k,int left, int right) {
        Random random = new Random();
        int baseIndex = random.nextInt(right - left + 1) + left;
        swap(arr, left, baseIndex);
        int pivot = arr[left];
        // 这里i要取left而不是left+1,
        int i = left, j = right;
        while (i < j) {
//            子while循环也要判断i<j, 并且要带等号
            while (i < j && arr[j] >= pivot) j--;
            while (i < j && arr[i] <= pivot) i++;
            swap(arr, i, j);
            // 这里一定不能加
//            i++;
//            j++;
        }
        swap(arr, left, j);
        // 这里的+1和-1一定要加，不然在k等于数组长度的时候会无限循环
        if(j>k) quickPartition(arr, k,left, j - 1);
        if(j<k) quickPartition(arr, k,j + 1, right);
        return arr[k];
    }

    //    返回前k小的数组
    public int[] quickPartition2(int[] arr, int k,int left, int right) {
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
        if(j>k) quickPartition2(arr, k,left, j - 1);
        if(j<k) quickPartition2(arr, k,j + 1, right);
        return  Arrays.copyOf(arr, k);
    }

    public void swap(int[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }
}
