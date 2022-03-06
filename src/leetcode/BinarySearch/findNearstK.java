package leetcode.BinarySearch;

import java.util.ArrayList;
import java.util.List;

// 找个k个最接近的元素 https://leetcode-cn.com/problems/find-k-closest-elements/
// 二分找到第一个小于的数 再向两边扩展，边界条件很麻烦,不wa看错误样例很难写完全
// 还有另一种check函数的写法：找左边界，因为k固定，所以右边界也已知，比较 x-num[mid] 和 num[mid+k]-x（不能比较绝对值，因为可能有负数）
// 如果<=的话，说明x在mid的左边或者在中间离mid近的位置，左边界需要往左移，则设right=mid，不然left=mid+1，转化为普通二分模板 TODO
public class findNearstK {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int size = arr.length;

        int left = 0;
        int right = size - k;

        while (left < right) {
            // int mid = left + (right - left) / 2;
            int mid = (left + right) >>> 1;
            // 尝试从长度为 k + 1 的连续子区间删除一个元素
            // 从而定位左区间端点的边界值
            if (x - arr[mid] >arr[mid + k] - x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            res.add(arr[i]);
        }
        return res;
    }
}
