package leetcode.ClassicProblem;

import java.sql.Array;
import java.util.*;

// x数之和 + 连续子数组之和 问题总结
public class twoSumConclusion {
    // 两数之和 且只有一个结果
    // 用哈希表存下所有值，优化到O（n）
    // 排序+双指针的做法是O（nlogn）在两数之和没有哈希表好
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            // 一次遍历过程中比较并放入map,每个数只和前面的数进行比较，避免重复比较
            if (map.containsKey(target - nums[i])) {
                res[0] = map.get(target - nums[i]);
                res[1] = i;
            }
            map.put(nums[i], i);
        }
        return res;
    }

    // 和为k的连续子数组 且包含负数 https://leetcode-cn.com/problems/subarray-sum-equals-k/
    // 如果没有负数，滑动窗口可以
    // 有负数的话，借用两数之和哈希表的思路 O（n）。 但是存的不是每个值，而是前缀和 -> sum[i,j] = pre[j] - pre[i-1] = k;
    // 且不像两数之和只有一个解，对于多解的情况，map还需要统计每一个值x的个数（有多少个pre[i]=x）
    // 路径总和3也是这个思路，但是是对于树而言
    public int subarraySum(int[] nums, int k) {
        int count = 0, pre = 0;
        HashMap<Integer, Integer> mp = new HashMap<>();
        //很关键，对于每个pre[i] = k, 可以直接使用前i个数，即pre[i]-pre[0]一定是可以的，map[0]要提前置1来考虑这个情况
        mp.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            pre += nums[i];
            if (mp.containsKey(pre - k)) {
                count += mp.get(pre - k);
            }
            mp.put(pre, mp.getOrDefault(pre, 0) + 1);
        }
        return count;
    }

    // 统计优美子数组 找奇数个数为k的连续子数组的个数   https://leetcode-cn.com/problems/count-number-of-nice-subarrays/
    // 和相同的二元子数组 和这题相同 https://leetcode-cn.com/problems/binary-subarrays-with-sum/
    // 可以用滑动窗口，计数细节：维护当前滑动窗口最左边的1和它左边的第一个1的位置，每次right指针+1,都有left2-left1个子数组符合要求
    // 或者处理一下，将奇数为1，偶数0， 将题目转化为和为k的连续子数组，记录前缀和pre[]， 对于每个pre[i]，找是否有pre[j] = pre[i] - k, 又因为只有1和0，所以可以用nums长的数组代替哈希表
    public int numberOfSubarrays(int[] nums, int k) {
        int n = nums.length;
        int left1 = 0, left2 = 0, right = 0;
        int sum1 = 0, sum2 = 0;
        int ret = 0;
        while (right < n) {
            sum1 += nums[right]&1;
            while (left1 <= right && sum1 > k) {
                sum1 -= nums[left1]&1;
                left1++;
            }
            sum2 += nums[right]&1;
            while (left2 <= right && sum2 >= k) {
                sum2 -= nums[left2]&1;
                left2++;
            }
            ret += left2 - left1;
            right++;
        }
        return ret;
    }

    // 统计优美子数组
    // 将题目转化为和为k的连续子数组
    public int numberOfSubarrays2(int[] nums, int k) {
        int curPreSum = 0;
        int res = 0;
        // 奇数为1，偶数为0，所以最大presum为length，用数组替代map，索引为和，存储个数 -> preSumMap[sum] = count;
        int[] preSumMap = new int[nums.length + 1];
        //对于每个pre[i] = k, 可以直接使用前i个数，即pre[i]-pre[0]一定是可以的，所以preSumMap[0]要提前置1考虑这个情况
        preSumMap[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            curPreSum += nums[i] & 1;
            preSumMap[curPreSum]++;
            if (curPreSum >= k) {
                res += preSumMap[curPreSum - k];
            }
        }
        return res;
    }

    // 和为k的倍数的连续子数组（区间大于等于2） https://leetcode-cn.com/problems/continuous-subarray-sum/
    // 利用同余定理，优化至和为k的连续子数组 -> 如果pre[i]和pre[j]除k的余数相同，那么pre[i]-pre[j]可以被k整除，所以只需要存储pre[]除k的余数
    public boolean checkSubarraySum(int[] nums, int k) {
        int preSum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];
            int n = preSum % k;
            if (n == 0 && i > 0) {
                return true;
            }
            Integer index = map.get(n);
            if (index == null) {
                map.put(n, i);
            } else if (i - index >= 2) { // 保证子数组长度大于2
                return true;
            }
        }
        return false;
    }

    // 和可被K整除的连续子数组 和k倍数那题内核一致（同余定理+presum），区别在于需要返回数量并且没有区间最短要求
    // https://leetcode-cn.com/problems/subarray-sums-divisible-by-k/
    public int subarraysDivByK(int[] nums, int k) {
        Map<Integer, Integer> record = new HashMap<Integer, Integer>();
        //presum统计个数都需要初始化mp(0,1)代表Pre[i]-pre[0]这种情况
        record.put(0, 1);
        int sum = 0, ans = 0;
        for (int elem : nums) {
            sum += elem;
            // 注意 Java 取模的特殊性，当被除数为负数时取模结果为负数，需要纠正
            int modulus = (sum % k + k) % k;
            int same = record.getOrDefault(modulus, 0);
            ans += same;
            record.put(modulus, same + 1);
        }
        return ans;
    }

    // 乘积小于K的连续子数组（都是正数） https://leetcode-cn.com/problems/subarray-product-less-than-k/
    // 没有负数，所以可以使用滑动窗口，这题需要返回所有子数组的个数，难点在于如何进行计数
    // 右指针每移动一步，统计包括右指针所在数的区间个数 = right-left+1） -> [right] [right,right-1] [right,right-1,right-2].....
    //  right-left+1的切入点是思维要放在区间的右边往左边延伸，例如区间[1, 2, 3, 4]满足要求，固定住right(4)的点，可选区间右[4]、[4, 3]、[4, 3, 2]、[4, 3, 2, 1]即为数组的长度，
    //  也就是right-left+1。而right是递增的，此时[1, 2, 3]的区间已经处理完（[3]、[3, 2]、[3、2、1]）。如果从left为切入点，就会有[1, 2, 3, 4]和[1, 2, 3]都有[1]，是重复了的错乱思维。
    // 也可以用来统计都是正数的情况下 和小于k的连续子数组个数
    // 找小于某个数的连续子数组个数都是用这种计数，还可以用来 来找某个区间或者正好等于某xx的子数组个数
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1) return 0;
        int prod = 1, ans = 0, left = 0;
        for (int right = 0; right < nums.length; right++) {
            prod *= nums[right];
            while (prod >= k) prod /= nums[left++];
            ans += right - left + 1;
        }
        return ans;
    }


    // 三数之和 且可能有多个结果
    // 排序加双指针（固定第一个，找两数之和） 复杂度O（n^2）
    // 也可以用哈希表存所有两数之和，再遍历 复杂度O（n^2）但是空间复杂度为O（n^2），且多个结果需要去重，去重细节很难想完全
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList();
        int len = nums.length;
        if (nums == null || len < 3) return ans;
        Arrays.sort(nums); // 排序
        for (int i = 0; i < len; i++) {
            if (nums[i] > 0) break; // 如果当前数字大于0，则三数之和一定大于0，所以结束循环
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 去重
            int L = i + 1;
            int R = len - 1;
            while (L < R) {
                int sum = nums[i] + nums[L] + nums[R];
                if (sum == 0) {
                    ans.add(Arrays.asList(nums[i], nums[L], nums[R]));
                    while (L < R && nums[L] == nums[L + 1]) L++; // 去重
                    while (L < R && nums[R] == nums[R - 1]) R--; // 去重
                    L++;
                    R--;
                } else if (sum < 0) L++;
                else if (sum > 0) R--;
            }
        }
        return ans;
    }

    // 三数之和
    // 哈希表写法
    public List<List<Integer>> threeSumHashMap(int[] nums) {
        // 前置条件
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        // 结果
        List<List<Integer>> result = new ArrayList<>();
        // 哈希表，记录每个数字在数组中的位置，由于不能重复，所以不用担心数组中有重复数字会对哈希表内容有影响
        Map<Integer, Integer> map = new HashMap<>();
        int length = nums.length;
        // 排序——去重，满足从左到右遍历时，得到的数字a<=b<=c
        Arrays.sort(nums);
        // 将数字加入哈希表
        for (int i = 0; i < length; i++) {
            map.put(nums[i], i);
        }
        int target = 0;
        Integer third;
        List<Integer> add;
        // 遍历获得的第一个数字
        for (int first = 0; first < length; first++) {
            // 需要和上一次枚举的数不相同
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // 要使三数之和为0，所以目标数为第一个数字取反
            target = -nums[first];
            // 遍历获得的第二个数字
            for (int second = first + 1; second < length; second++) {
                // 需要和上一次枚举的数不相同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                // 尝试从哈希表中获取第三个数字（target - nums[second]），若存在，并且第三个数字需要在第二个数字右侧
                if ((third = map.get(target - nums[second])) != null) {
                    if (third > second) {
                        // 符合条件，添加进列表中
                        add = new ArrayList<>();
                        add.add(nums[first]);
                        add.add(nums[second]);
                        add.add(nums[third]);
                        result.add(add);
                    }
                    // 要满足找到的数字a≤b≤c，所以当第二项b>c时，退出当前循环（再继续查找也只会出现b>c，而此组3个数肯定已存在于列表中）
                    else {
                        break;
                    }
                }
            }
        }
        return result;
    }


    // 四数之和
    // 三数之和上加一层循环 O(n^3)，也可以用两层哈希表扫两次->保存a+b,遍历c+d O(n^2)
    // 还有一个区别是和不为0了，所以不能用if (nums[i] > 0) break 来剪枝（-5+-4+-3=-12）
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 去重
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue; // 去重
                int n = j + 1, m = nums.length - 1;
                while (n < m) {
                    int sum = nums[i] + nums[j] + nums[n] + nums[m];
                    if (sum < target) {
                        n++;
                    } else if (sum > target) {
                        m--;
                    } else {
                        res.add(Arrays.asList(nums[i], nums[j], nums[n], nums[m]));
                        while (n < m && nums[n] == nums[n + 1]) n++; // 去重
                        while (n < m && nums[m] == nums[m - 1]) m--; // 去重
                        n++;
                        m--;
                    }
                }
            }
        }
        return res;
    }

    // 四数之和
    // 可以用两层哈希表扫两次（保存a+b,遍历c+d） 时间复杂度降低到O（n^2），但是实现非常麻烦
    public List<List<Integer>> fourSumHash(int[] nums, int target) {
        HashMap<Integer, List<List<Integer>>> t = new HashMap<>();
        HashSet<List<Integer>> ret = new HashSet<>();
        Arrays.sort(nums);
        int n = nums.length;

        int start = 0;
        int end = n - 1;
        // 第一次记录从后往前扫，来通过[2,2,2,2,2]的案例
        for (int i = end; i >= start + 1; i--) {
            if (i < end && nums[i] == nums[i + 1]) continue; // 去重
            for (int j = i - 1; j >= start; j--) {
                if (j < i - 1 && nums[j] == nums[j + 1]) continue; // 去重
                int s = nums[i] + nums[j];
                List<Integer> tmp = new ArrayList<>();
                tmp.add(i);
                tmp.add(j);
                if (t.containsKey(s)) {
                    List<List<Integer>> tmp1 = t.get(s);
                    tmp1.add(tmp);
                    t.put(s, tmp1);
                } else {
                    List<List<Integer>> tmp1 = new ArrayList<>();
                    tmp1.add(tmp);
                    t.put(s, tmp1);
                }
            }
        }

        start = 0;
        end = n - 1;
        for (int i = start; i <= end - 1; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 去重
            for (int j = i + 1; j <= end; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue; // 去重
                int s = target - (nums[i] + nums[j]);
                if (t.containsKey(s)) {
                    for (List<Integer> pairs : t.get(s)) {
                        int k = pairs.get(0);
                        int l = pairs.get(1);
                        // j<l可以去除相同四个数，排列不同的情况，或者也可以在ret.add(tmp)前把tmp sort一下去重
                        if (j < l) {
                            List<Integer> tmp = new ArrayList<>();
                            tmp.add(nums[i]);
                            tmp.add(nums[j]);
                            tmp.add(nums[k]);
                            tmp.add(nums[l]);
                            ret.add(tmp);
                        }
                    }
                }
            }
        }
        return new ArrayList<>(ret);
    }

    // 四数之和2  还是保存a+b,遍历c+d O（n^2）
    // 区别是给了4个数组，而不是在一个数组里，不需要考虑重复的情况，并且返回个数而不是所有数字，编码难度相比四数之和在一个数组里的哈希表解法低了几个量级
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> countAB = new HashMap<Integer, Integer>();
        for (int u : A) {
            for (int v : B) {
                countAB.put(u + v, countAB.getOrDefault(u + v, 0) + 1);
            }
        }
        int ans = 0;
        for (int u : C) {
            for (int v : D) {
                if (countAB.containsKey(-u - v)) {
                    ans += countAB.get(-u - v);
                }
            }
        }
        return ans;
    }



}
