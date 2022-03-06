package leetcode.ClassicProblem;


import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 子集  https://leetcode-cn.com/problems/subsets/
// 多解法大赏
public class SubSet {

    // 解法一： 位运算，假设数组有5位，那就从0枚举到2^5，每个数二进制上的第几个1表示第几个数存在
    // 这个位运算的方法需要掌握
    public List<List<Integer>> subsets(int[] nums) {
        int n = nums.length;
        int total = 1 << n;
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            List<Integer> sub = new ArrayList<>();
            //遍历所有位，看当前数i这个位是否为1
            for (int j = 0; j < n; j++) {
                // 当前这个位为1就把这个索引上的数加入答案
                if (((i >> j) & 1) == 1) sub.add(nums[j]);
            }
            res.add(sub);
        }
        return res;
    }

    // 解法二： DFS回溯
    List<List<Integer>> res;

    public List<List<Integer>> subsets2(int[] nums) {
        res = new ArrayList<>();
        dfs(nums, 0, new ArrayList<>());
        return res;
    }

    public void dfs(int[] nums, int index, List<Integer> subSet) {
        if (index == nums.length) {
            res.add(new ArrayList<>(subSet));
            return;
        }
        subSet.add(nums[index]);
        dfs(nums, index + 1, subSet);
        subSet.remove(subSet.size() - 1);
        dfs(nums, index + 1, subSet);
    }

    // 解法3：类似于动态规划，dfs返回取前i个值时的res集合
    // 也可以直接用一个for循环，按照动态规划来写
    public List<List<Integer>> subsets3(int[] nums) {
        return dfs(nums, nums.length);
    }

    public List<List<Integer>> dfs(int[] nums, int index) {
        if (index == 0) return new ArrayList<>();
        if (index == 1) {
            List<List<Integer>> res = new ArrayList<>();
            res.add(new ArrayList<>());
            res.add(new ArrayList<>(nums[0]));
            return res;
        }
        List<List<Integer>> lastRes = dfs(nums, index - 1);
        List<List<Integer>> res = new ArrayList<>();
        for (List<Integer> sub : lastRes) {
            res.add(new ArrayList<>(sub));
            sub.add(nums[index - 1]);
            res.add(new ArrayList<>(sub));
        }
        return res;
    }


    // 子集2，有重复情况 https://leetcode-cn.com/problems/subsets-ii/
    // 核心思想-排序： 借助排序后相同元素相邻进行去重， 时间复杂度 nlogn< 2^n，所以不影响
    // 排序后 若发现没有选择上一个数，且当前数字与上一个数相同，则可以跳过当前生成的子集。
    List<Integer> t = new ArrayList<Integer>();
    List<List<Integer>> ans = new ArrayList<List<Integer>>();
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        dfs(false, 0, nums);
        return ans;
    }

    public void dfs(boolean choosePre, int cur, int[] nums) {
        if (cur == nums.length) {
            ans.add(new ArrayList<Integer>(t));
            return;
        }
        dfs(false, cur + 1, nums);
        //也是数层去重，树枝不去重
        if (!choosePre && cur > 0 && nums[cur - 1] == nums[cur]) {
            return;
        }
        t.add(nums[cur]);
        dfs(true, cur + 1, nums);
        t.remove(t.size() - 1);
    }

}
