package leetcode.BinarySearch;

// 搜索旋转排序数组 https://leetcode-cn.com/problems/search-in-rotated-sorted-array/
// 先二分找最小值位置，确定旋转点，在根据大小在两边进行二分搜索
public class SearchRotateArray {
    // 寻找旋转排序数组中的最小值 https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/
    // 做的时候没想对 nums[mid]>nums[mid+1]有问题
    // 应该是比较mid和left或者right或者0 todo

    // mid<left 这种写法还需要单独判断一下
    public int findMin(int[] nums) {
        int left=0,right=nums.length-1;
        if(nums[0]<nums[nums.length-1]) return nums[0];
        while(left<right) {
            // 单独判断二分过程中left-right是不是单调队列
            if(nums[left]<nums[right]) return nums[left];
            int mid = (left+right)/2;
            if(nums[mid]<nums[left]) {
                right = mid;
            } else left =mid+1;
        }
        return nums[left];
    }

    // mid< right 这种写法更通用，不需要单独判断
    public int findMin2(int[] nums) {
        int left=0,right=nums.length-1;
        if(nums[0]<nums[nums.length-1]) return nums[0];
        while(left<right) {
            int mid = (left+right)/2;
            // 相等时，可能在左边也可能在右边，但是无论num[right]是不是最小值，都有一个和他相等的，所以忽略右端点
            if(nums[mid]<nums[right]) {
                right = mid;
            } else left =mid+1;
        }
        return nums[left];
    }

    // mid<0 这种写法也不需要单独判断
    public int findMin3(int[] nums) {
        int left=0,right=nums.length-1;
        if(nums[0]<nums[nums.length-1]) return nums[0];
        while(left<right) {
            int mid = (left+right)/2;
            if(nums[mid]<nums[0]) {
                right = mid;
            } else left =mid+1;
        }
        return nums[left];
    }

    // 寻找旋转排序数组中的最小值2 https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array-ii/solution/xun-zhao-xuan-zhuan-pai-xu-shu-zu-zhong-de-zui--16/
    // 允许重复数字
    // 第一种解法 二分，但是不能套模板，三种情况重新分析(这种只能找到最小值，因为重复元素，left不一定是旋转点）
    public int minArray(int[] numbers) {
        int len = numbers.length;
        if (len == 0) {
            return 0;
        }
        int left = 0;
        int right = len - 1;
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (numbers[mid] > numbers[right]) {
                // [3, 4, 5, 1, 2]，mid 以及 mid 的左边一定不是最小数字
                // 下一轮搜索区间是 [mid + 1, right]
                left = mid + 1;
            } else if (numbers[mid] == numbers[right]) {
                // 只能把 right 排除掉，下一轮搜索区间是 [left, right - 1]
                right = right - 1;
            } else {
                // 此时 numbers[mid] < numbers[right]
                // mid 的右边一定不是最小数字，mid 有可能是，下一轮搜索区间是 [left, mid]
                right = mid;
            }
        }
        // 最小数字一定在数组中，因此不用后处理
        return numbers[left];
    }

    //第二种解法：进一步分析，重复元素在开头和结尾同时出现时才会影响二段性，所以预处理去除，再套用二分模板（可以找到重复旋转数组中的旋转点）
    public int minArray2(int[] nums) {
        int n = nums.length;
        int l = 0, r = n - 1;
        while (l < r && nums[0] == nums[r]) r--;
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (nums[mid] >= nums[0]) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        return r + 1 < n ? nums[r + 1] : nums[0];
    }



    //搜索旋转排序数组：先二分找最小值位置，确定旋转点，在根据大小在两边进行二分搜索
    public int search(int[] nums, int target) {
        int left=0,right=nums.length-1;
        if(nums[0]<nums[nums.length-1]) left=right=0;
        while(left<right) {
            int mid = (left+right)/2;
            if(nums[mid]<=nums[right]) {
                right = mid;
            } else left =mid+1;
        }
        if(nums[nums.length-1]<target) {
            right = left-1;
            left = 0;
        } else {
            left =left;
            right =nums.length-1;
        }

        while(left<right) {
            int mid = (left+right)/2;
            if(nums[mid]>=target) right = mid;
            else left = mid+1;
        }
        return nums[left]==target?left:-1;
    }

    // 搜索旋转排序数组2 允许重复数字 https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/
    // 需要使用第二种恢复二段性的方法找重复旋转数组中的旋转点，再进行普通二分搜索
    public int search2(int[] nums, int t) {
        int n = nums.length;
        int l = 0, r = n - 1;
        while (l < r && nums[0] == nums[r]) r--;
        //用找最后一个元素的模板
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (nums[mid] >= nums[0]) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        int leftFind = findFirst(nums,0,r,t);
        int rightFind = r==nums.length-1?-1:findFirst(nums,r+1,nums.length-1,t);
        return leftFind==-1?rightFind:leftFind;
    }

    public int findFirst(int[] nums,int l,int r,int t) {
        int left = l,right=r;
        while(left<right) {
            int mid = (left+right)/2;
            if(nums[mid]>=t) right=mid;
            else left=mid+1;
        }
        return nums[left]==t?left:-1;
    }

    // 搜索旋转数组 https://leetcode-cn.com/problems/search-rotate-array-lcci/
    // 和上面这题基本一致，唯一区别在如果存在target，返回第一个target的索引，只需要将第二次二分搜索的模板改为找第一个出现的即可（）



    //寻找峰值 https://leetcode-cn.com/problems/find-peak-element/
    // 二分找比中点大的那边，一定能找到峰值点 代码是标准模板就不放了


}
