package aJianZhiOffer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

// 最小的k个数
// 快速选择或者堆排序
public class J040_LeastKNumbers {
    public int[] getLeastNumbers(int[] arr, int k) {
        if(k>=arr.length) return arr;
        quickSelect(arr,k,0,arr.length-1);
        return Arrays.copyOfRange(arr,0,k);
    }

    public void quickSelect(int[] arr,int k, int l,int r) {
        Random random = new Random();
        int randomIndex = random.nextInt(r-l+1);
        swap(arr,l,l+randomIndex);
        int pivot = arr[l];
        int i=l,j = r;
        while(i<j) {
            while(i<j && arr[j]>=pivot) j--;
            while(i<j&& arr[i] <=pivot) i++;
            swap(arr,i,j);
        }
        swap(arr,l,i);
        //
        if(i<k) quickSelect(arr,k,i+1,r);
        if(i>k) quickSelect(arr,k,l,i-1);
        if(i==k) return;

    }

    public void swap(int[] arr,int l,int r) {
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r]  =temp;
    }



    // priorityqueue默认是升序，可以把头看做是堆顶，那么头就是最小的，所以默认是小根堆，要设置为大根堆需要num2-num1，这样堆顶（头）是最大的元素
    public int[] getLeastNumbers2(int[] arr, int k) {
        int[] vec = new int[k];
        if (k == 0) { // 排除 0 的情况
            return vec;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(new Comparator<Integer>() {
            public int compare(Integer num1, Integer num2) {
                return num2 - num1;
            }
        });
        for (int i = 0; i < k; ++i) {
            queue.offer(arr[i]);
        }
        for (int i = k; i < arr.length; ++i) {
            if (queue.peek() > arr[i]) {
                queue.poll();
                queue.offer(arr[i]);
            }
        }
        for (int i = 0; i < k; ++i) {
            vec[i] = queue.poll();
        }
        return vec;
    }
}
