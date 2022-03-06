package leetcode.ClassicProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 计算右边小的数的个数  https://leetcode-cn.com/problems/count-of-smaller-numbers-after-self/
// 初看是用单调栈，细想发现不对，但是限制在单调栈上了，最后看答案是用分治归并
// 和逆序对那道题同理，一个升序归并一个是降序归并，归并过程中统计个数，不同的是这道题还需要引入索引数组来解决元素重复的问题
public class CalculateLessNumberInRight {
    // 前置题：数组中的逆序对 https://leetcode-cn.com/problems/shu-zu-zhong-de-ni-xu-dui-lcof/
    // 经典分治归并题目
    int[] temp;     // 避免每次归并都创建新的temp数组
    public int reversePairs(int[] nums) {
        temp = new int[nums.length];
        return mergeSort(nums,0,nums.length-1);
    }

    public int mergeSort(int[] nums, int l,int r) {
        // l可能大于r，不能只取等于
        if(l>=r) return 0;
        int mid=(l+r)/2;
        int res = mergeSort(nums,l,mid)+mergeSort(nums,mid+1,r);

        // 归并过程
        // 对于右边的每个数，左边剩下的数都比他大，count= mid-left+1 todo
        int left = l,right = mid+1,index=0;
        while(left<=mid && right<=r) {
            // 等于不能计数
            if(nums[left]<=nums[right]) {
                temp[index++] = nums[left++];
            } else {
                temp[index++] = nums[right++];
                res+=mid-left+1;
            }
        }

        while(left<=mid) {
            temp[index++] = nums[left++];
        }
        while(right<=r) {
            temp[index++] = nums[right++];
        }
        for (int k = 0; k < r - l + 1; ++k) {
            nums[k + l] = temp[k];
        }

        return res;
    }



    // 计算右侧小于当前元素的个数
    // 辅助数组
    int[] tempArr;
    // 辅助索引数组
    int[] tempIndex;
    // 原数组
    int[] nums;
    // 归并过程中索引的数组 一直保持 index[i]=i;
    int[] indexArray;
    // 结果数组，统计第几个索引的元素右侧有几个小于的个数
    int[] res;
    public List<Integer> countSmaller(int[] nums_) {
        nums = nums_;
        tempArr = new int[nums.length];
        tempIndex =new int[nums.length];
        indexArray = new int[nums.length];
        res = new int[nums.length];
        for(int i=0;i<nums.length;i++) indexArray[i] = i;
        mergeSort2(0,nums.length-1);
        List<Integer>  ans =new ArrayList<>();
        for(int i=0;i<res.length;i++) {
            ans.add(res[i]);
        }
        return ans;
    }

    public void mergeSort2(int left,int right) {
        if(left>=right) return;
        int mid = (left+right)/2;
        mergeSort2(left,mid);
        mergeSort2(mid+1,right);
        //归并
        int i=left,j = mid+1,index = 0;
        while(i<=mid &&j<=right) {
            if(nums[i]>nums[j]) {
                res[indexArray[i]]+=right-j+1;
                tempArr[index] = nums[i];
                tempIndex[index++] = indexArray[i++];
            } else {
                tempArr[index] = nums[j];
                tempIndex[index++] = indexArray[j++];
            }
        }

        while(i<=mid) {
            tempArr[index] = nums[i];
            tempIndex[index++] = indexArray[i++];
        }

        while(j<=right) {
            tempArr[index] = nums[j];
            tempIndex[index++] = indexArray[j++];
        }

        for(int k=0;k<right-left+1;k++) {
            nums[k+left] = tempArr[k];
            indexArray[k+left] = tempIndex[k];
        }
    }

    public int singleNonDuplicate(int[] nums) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int mid = (high - low) / 2 + low;
            if (nums[mid] == nums[mid ^ 1]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return nums[low];
    }

}
