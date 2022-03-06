package leetcode.dfsBrackTracePrune;

import java.util.*;

// 组合总和
// 可重复的选择数组中的数使和为target
// 经典回溯，有一点是可以重复选择元素，但是为了避免结果重复我们还是要在dfs函数中维护当前index
// 剪枝做法需要排序，这样在小于0时直接返回 减少回溯次数
public class combinationSum {


        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            int len = candidates.length;
            List<List<Integer>> res = new ArrayList<>();
            if (len == 0) {
                return res;
            }

            Deque<Integer> path = new ArrayDeque<>();
            dfs(candidates, 0, len, target, path, res);
            return res;
        }

        private void dfs(int[] candidates, int begin, int len, int target, Deque<Integer> path, List<List<Integer>> res) {
            // target 为负数和 0 的时候不再产生新的孩子结点
            if (target < 0) {
                return;
            }
            if (target == 0) {
                res.add(new ArrayList<>(path));
                return;
            }

            // 从begin开始搜索，避免重复结果
            for (int i = begin; i < len; i++) {
                path.addLast(candidates[i]);

                // 注意：由于每一个元素可以重复使用，下一轮搜索的起点依然是 i，这里非常容易弄错
                dfs(candidates, i, len, target - candidates[i], path, res);

                path.removeLast();
            }
        }



    // 组合总数2 ，数组有重复，但是结果不能有重复
    // 重点在去重，排序后 如果nums[i-1]==nums[i]则跳过当前dfs
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        int len = candidates.length;
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }

        Deque<Integer> path = new ArrayDeque<>();
        dfs2(candidates, 0, len, target, path, res);
        return res;
    }

    private void dfs2(int[] candidates, int begin, int len, int target, Deque<Integer> path, List<List<Integer>> res) {
        // target 为负数和 0 的时候不再产生新的孩子结点
        if (target < 0) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        // 从begin开始搜索，避免重复结果
        for (int i = begin; i < len; i++) {
            //  这里非常关键，和全排列 子集的去重写法不同但是本质相同 todo
            // candidates[i-1]==candidates[i]会对树枝和数层同时去重，但是题目意思是只需要数层去重，树枝需要保留重复
            // 树层去重，树枝可以重复（即每层只能第一个与上一层数相同-> i=begin，所以i>begin的都不行，这样就对数层去重但是树枝不去重）
            // 如果 i>0而不是begin，那么就要用一个vis数组，额外判断一下!vis[i-1]
//            https://leetcode-cn.com/problems/combination-sum-ii/solution/hui-su-suan-fa-jian-zhi-python-dai-ma-java-dai-m-3/225211
            if(i>begin&&candidates[i-1]==candidates[i]) continue;
            path.addLast(candidates[i]);
            dfs2(candidates, i+1, len, target - candidates[i], path, res);

            path.removeLast();
        }
    }

    // 组合总和3 没什么特点 普通回溯
    // 只是因为范围限定在1-9，可以用位运算枚举所有情况再判断
    List<List<Integer>> res;
    List<Integer> cur;
    public List<List<Integer>> combinationSum3(int k, int n) {
        int[] nums = new int[] {1,2,3,4,5,6,7,8,9};
        res  =new ArrayList<>();
        cur = new ArrayList<>();
        dfs3(nums,0,k,n);
        return res;
    }

    public void dfs3(int[] nums,int index,int count,int target) {
        if(target<0 || index >nums.length||count<0) return;
        if(target==0&&count==0) res.add(new ArrayList<>(cur));
        for(int i=index;i<nums.length;i++) {
            cur.add(nums[i]);
            dfs3(nums,i+1,count-1,target-nums[i]);
            cur.remove(cur.size()-1);
        }
    }

    // 组合总和4 范围变大，且只用输出个数
    // 所以dfs回溯肯定不行，考虑动态规划
    // dp[i]表示和为i这个数的组合个数
    public int combinationSum4(int[] nums, int target) {
        Set<Integer> st = new HashSet<>();
        for(int num: nums) st.add(num);
        int[] dp  =new int[target+1];
        for(int i =1;i<=target;i++) {
            if(st.contains(i)) dp[i] = 1;
            // 写的时候这里循环从0到i，太大了（target可以到1000）
//            for(int j=1;j<i;j++) {
//                if(dp[j]!=0 && st.contains(i-j)) {
//                    dp[i]+=dp[j];
//                }
//            }
            // 可以优化成遍历nums数组中的数（nums.length最大为200），这样不用遍历所有小于i的数也可以不用set
              for(int num: nums) {
                  if(i>num) dp[i] +=dp[i-num];
              }
        }
        return dp[target];
    }

    // 组合总和4 可以用记忆化递归 https://leetcode-cn.com/problems/combination-sum-iv/solution/fu-xue-ming-zhu-cong-ji-yi-hua-di-gui-tu-rqwy/
    // 记忆化递归和动态规划本质是互通的
    // 递归是自顶向下的计算方式（大问题->小问题），而动态规划是自底向上的计算方式（小问题->大问题）
    class Solution {
        Map<Integer, Integer> map = new HashMap<>();
        public int combinationSum4(int[] nums, int target) {
            return backtrack(nums, target);
        }

        private int backtrack(int[] nums, int remains){
            if(remains == 0)
                return 1;

            if(map.containsKey(remains))
                return map.get(remains);

            int res = 0;
            for(int i = 0; i < nums.length; i++){
                if(remains >= nums[i])
                    res += backtrack(nums, remains - nums[i]);
            }

            map.put(remains, res);
            return res;
        }
    }


    // 组合 https://leetcode-cn.com/problems/combinations/
    // 回溯题目 可以按照每个数字dfs（选还是不选） 也可以按照count数遍历（每一次从剩下的数选），按照count数遍历的递归栈深度会浅很多（每一次分枝数多）
        List<List<Integer>> res2;
        List<Integer> cur2;
        // 按照每个数字选不选来dfs 比较慢
        public List<List<Integer>> combine(int n, int k) {
            res2  =new ArrayList<>();
            cur2 = new ArrayList<>();
            dfs(n,1,k);
            return res2;
        }

        public void dfs(int n,int index,int count) {
            if(index>n+1) return;
            if(count==0) {
                res2.add(new ArrayList<>(cur2));
                return;
            }
            dfs(n,index+1,count);
            cur2.add(index);
            dfs(n,index+1,count-1);
            cur2.remove(cur2.size()-1);
        }

    //按照count数遍历（每一次从剩下的数选）
    class Solution2 {
        List<List<Integer>> result = new ArrayList<>();
        public List<List<Integer>> combine(int n, int k) {
            backtracking(n, k, 1, new ArrayList<>());
            return result;
        }
        void backtracking(int n, int k, int start, List<Integer> path) {
            if (k == path.size()) {
                result.add(new ArrayList<>(path));
                return;
            }
            //剪枝，如果剩下的数不够count size，直接跳过
            for (int i = start; i <= n - (k - path.size()) + 1; i++) {
                path.add(i);
                backtracking(n, k, i+1, path);
                path.remove(path.size()-1);
            }
        }
    }

}
