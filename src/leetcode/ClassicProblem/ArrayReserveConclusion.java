package leetcode.ClassicProblem;

// 数组覆盖逻辑 总结 todo
public class ArrayReserveConclusion {
    // 移除元素 https://leetcode-cn.com/problems/remove-element/
    // 删除所有等于给定值的数，并且将不等于的数原地移动到数组前面 （适合移动元素较多的样例）
    // 标准解法，不会改变相对顺序
    public int removeElement(int[] nums, int val) {
        int n = nums.length;
        int left = 0,right = 0;
        while(right<n) {
            if (nums[right] != val) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
            }
            right++;
        }
        return left;
    }

    // 我自己的写法，可以将val全移动到最后，但是对于一些特殊情况，left的值不对
    public int removeElement2(int[] nums, int val) {
        int n = nums.length;
        int left=0,right = n-1;
        while(left<right) {
            while(left<right && nums[right]==val) right--;
            while(left<right && nums[left]!=val) left++;
            int temp  =nums[left];
            nums[left]= nums[right];
            nums[right] = temp;
        }
        return left+1;
    }

    // 优化后的双指针方法，和我的方法思路一致，但是写法不同，规避了特殊情况（适合移除元素较少的情况)
    // 且这种方法会改变前面元素的相对顺序
    public int removeElement3(int[] nums, int val) {
        int left = 0;
        int right = nums.length;
        while (left < right) {
            if (nums[left] == val) {
                int temp  =nums[left];
                nums[left]= nums[right-1];
                nums[right-1] = temp;
                right--;
            } else {
                left++;
            }
        }
        return left;
    }


    // 删除重复元素 https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/
    // 双指针 直接进行覆盖
    public int removeDuplicate(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int left=1,right=1;
        while(right<nums.length) {
            // 这里也可以改成如下形式，并且可以归化为允许k次重复的通解
            // nums[right] != nums[left-1]
            if(nums[right] != nums[right-1]) {
                nums[left++] = nums[right];
            }
            right++;
        }
        return left;
    }


    // 删除重复元素2 https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii/
    // 允许重复两次
    // 我的做法是加一个count计数
    // 官方做法是nums[left - 2] != nums[right]
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int left=1,right=1,count=1;
        while(right<nums.length) {
            if(nums[right] != nums[right-1]) {
                nums[left++] = nums[right];
                count=1;
            } else {
                if(count<2) {
                    nums[left++] = nums[right];
                    count++;
                }
            }
            right++;
        }
        return left;
    }

    // 移动零 https://leetcode-cn.com/problems/move-zeroes/
    // 要求相对有序，所以不能用优化双指针，只能用标准解法；并且可以用swap代替单纯赋值来再进一步优化
        public void moveZeroes(int[] nums) {
            int n = nums.length, left = 0, right = 0;
            while (right < n) {
                if (nums[right] != 0) {
                    swap(nums, left, right);
                    left++;
                }
                right++;
            }
        }

        public void swap(int[] nums, int left, int right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }

}
