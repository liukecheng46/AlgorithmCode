package leetcode.dfsBrackTracePrune;

import java.util.*;

// 全排列
public class Premute {
    // 全排列 基本回溯
    // 需要加一个used[]数组来判断是否使用
    class Solution2 {
        List<List<Integer>> res;
        List<Integer> cur;
        boolean[] used;

        public List<List<Integer>> permute(int[] nums) {
            used = new boolean[nums.length];
            res = new ArrayList<>();
            cur = new ArrayList<>();
            dfs(nums, nums.length);
            return res;
        }

        public void dfs(int[] nums, int count) {
            if (count == 0) {
                res.add(new ArrayList<>(cur));
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                if (!used[i]) {
                    cur.add(nums[i]);
                    used[i] = true;
                    dfs(nums, count - 1);
                    used[i] = false;
                    cur.remove(cur.size() - 1);
                }
            }
        }
    }


    // 全排列 还有一种另类解法
    // 固定first位置，每次都从first开始和后面所有数交换位置并且进入下一个dfs
    class Solution {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> res = new ArrayList<List<Integer>>();

            List<Integer> output = new ArrayList<Integer>();
            for (int num : nums) {
                output.add(num);
            }

            int n = nums.length;
            backtrack(n, output, res, 0);
            return res;
        }

        public void backtrack(int n, List<Integer> output, List<List<Integer>> res, int first) {
            // 所有数都填完了
            if (first == n) {
                res.add(new ArrayList<Integer>(output));
            }
            for (int i = first; i < n; i++) {
                Collections.swap(output, first, i);

                // 继续递归填下一个数
                backtrack(n, output, res, first + 1);

                Collections.swap(output, first, i);
            }
        }
    }

    // 全排列2 有重复元素，递归树层不能重复
    // 经典办法，sort之后判断nums[i]==nums[i-1] && !vis[i-1]
    // 掌握为什么vis[i-1]和!vis[i-1]都可以，区分树枝去重和数层去重
    // 这道题还可以用找下一个排列的方法来做，sort后每次找下一个大于的排列，一直到找不到为止
    List<List<Integer>> res;
    List<Integer> cur;
    boolean[] used;

    public List<List<Integer>> permuteUnique(int[] nums) {
        used = new boolean[nums.length];
        res = new ArrayList<>();
        cur = new ArrayList<>();
        Arrays.sort(nums);
        dfs(nums, nums.length);
        return res;
    }

    public void dfs(int[] nums, int count) {
        if (count == 0) {
            res.add(new ArrayList<>(cur));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            // 这里used[i-1]和used[i-1]都可以，但是性能差距很大，因为一个是树枝去重一个是数层去重，详见notion笔记
            // if(used[i] || (i>0&&nums[i]==nums[i-1]&&used[i-1])) continue;
            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) continue;
            cur.add(nums[i]);
            used[i] = true;
            dfs(nums, count - 1);
            used[i] = false;
            cur.remove(cur.size() - 1);

        }
    }


    //    排列序列 https://leetcode-cn.com/problems/permutation-sequence/
    //   复杂模拟 找规律
    public String getPermutation(int n, int k) {
        // 注意：相当于在 n 个数字的全排列中找到下标为 k - 1 的那个数，因此 k 先减 1
        k--;

        int[] factorial = new int[n];
        factorial[0] = 1;
        // 先算出所有的阶乘值
        for (int i = 1; i < n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }
        // 用一个列表模拟删除已使用元素，也可以用一个used[]辅助数组来标记已使用元素
        List<Integer> nums = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            nums.add(i);
        }

        StringBuilder stringBuilder = new StringBuilder();

        // i 表示剩余的数字个数，初始化为 n - 1
        for (int i = n - 1; i >= 0; i--) {
            int index = k / factorial[i];
            stringBuilder.append(nums.remove(index));
            k -= index * factorial[i];
        }
        return stringBuilder.toString();
    }

    // 下一个全排列 https://leetcode-cn.com/problems/next-permutation/solution/xia-yi-ge-pai-lie-by-leetcode-solution/
    // 找规律题目，样例就给3位，没看出来规律
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void reverse(int[] nums, int start) {
        int left = start, right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    //字母大小全排列 https://leetcode-cn.com/problems/letter-case-permutation/
    // 基本回溯
    List<String> list = new ArrayList();

    public List<String> letterCasePermutation(String s) {
        getStr(0, s, new StringBuffer());
        return list;
    }

    public void getStr(int index, String s, StringBuffer sb) {
        if (index == s.length()) {
            list.add(sb.toString());
            return;
        }
        char ch = s.charAt(index);
        sb.append(ch);
        getStr(index + 1, s, sb);
        sb.deleteCharAt(sb.length() - 1);

        // 判断是否是字符，如果是，则转换大小写
        if (Character.isLowerCase(ch)) ch = Character.toUpperCase(ch);
        else if (Character.isUpperCase(ch)) ch = Character.toLowerCase(ch);
        if (!Character.isDigit(ch)) {
            sb.append(ch);
            getStr(index + 1, s, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

}
