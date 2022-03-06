package leetcode.ClassicProblem;


import java.util.*;

// 二叉树的路径和问题
// 自顶向下或者非自顶向下两种大类 https://leetcode-cn.com/problems/smallest-string-starting-from-leaf/solution/yi-pian-wen-zhang-jie-jue-suo-you-er-cha-10sk/
public class PathSumConclusion {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //路径总和1 遍历
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return targetSum == root.val;
        if (hasPathSum(root.left, targetSum - root.val)) return true;
        if (hasPathSum(root.right, targetSum - root.val)) return true;
        return false;
    }


    //路径总和2 遍历+回溯输出所有结果
    public class Solution {
        List<List<Integer>> res;
        LinkedList<Integer> path;

        public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            res = new ArrayList<>();
            path = new LinkedList<>();
            dfs(root, targetSum);
            return res;
        }

        public void dfs(TreeNode root, int sum) {
            if (root == null) return;
            path.offerLast(root.val);
            // 这里的new非常关键，不然存的都是同一份path的内存地址
            if (root.left == null && root.right == null && sum == root.val) res.add(new LinkedList<>(path));
            dfs(root.left, sum - root.val);
            dfs(root.right, sum - root.val);
            path.pollLast();

        }
    }

    //  二叉树的所有路径  字符串的回溯-Stringbuilder
    // https://leetcode-cn.com/problems/binary-tree-paths/
    public class Solution2 {
        List<String> res = new LinkedList<>();
        StringBuilder temp = new StringBuilder();

        public List<String> binaryTreePaths(TreeNode root) {
            dfs(root);
            return res;
        }

        public void dfs(TreeNode root) {
            if (root == null) return;
            temp.append(root.val);
            if (root.left == null && root.right == null) {
                res.add(new String(temp));
                return;
            }
            temp.append("->");
            binaryTreePaths(root.left);
            binaryTreePaths(root.right);
            temp.deleteCharAt(temp.length() - 1);
            temp.deleteCharAt(temp.length() - 1);
            int valLength = String.valueOf(root.val).length();
            while (valLength-- > 0) {
                temp.deleteCharAt(temp.length() - 1);
            }
        }
    }


    // 路径总和3
    // 双重dfs，每个点都dfs找是否可以等于sum，感觉没办法用记忆化来优化，不过数据量1000，双重dfs可以过 O(n^2)
    // 最优方法：这道题和找连续子数组等于k是相同原理的，都是用前缀和加哈希表来优化， 遍历时维护从root到当前节点的前缀和，并且都存入hashmap，对于任意节点a,找哈希表是否存在 pre[root,a]-pre[root,b]=sum[b,a] 复杂度O(n)
    class Solution4 {
        int res;
        public int pathSum(TreeNode root, int targetSum) {
            if(root == null) return 0;
            //dfs遍历，遍历到每个点继续判断
            dfs(root,targetSum);
            return res;
        }

        public void dfs(TreeNode root, int targetSum) {
            if(root == null) return;
            dfs2(root,targetSum);
            dfs(root.left,targetSum);
            dfs(root.right,targetSum);
        }

        public void dfs2(TreeNode root, int targetSum) {
            if(root == null) return;
            if(root.val == targetSum) res++;
            dfs2(root.left,targetSum-root.val);
            dfs2(root.right,targetSum-root.val);
        }
    }

    // 路径总和3 前缀和哈希表优化
    int res;
    HashMap<Integer,Integer> map = new HashMap<>();
    public int pathSum3(TreeNode root, int targetSum) {
        if(root ==null) return 0;
        // 前缀和加哈希表的模板 要记得加上map(0,1)；
        map.put(0,1);
        dfs(root,targetSum,0);
        return res;
    }

    public void dfs(TreeNode root, int targetSum,int pre) {
        if(root ==null) return;
        pre+=root.val;
        if(map.containsKey(pre-targetSum)) res+= map.get(pre-targetSum);
        map.put(pre,map.getOrDefault(pre,0)+1);
        dfs(root.left,targetSum,pre);
        dfs(root.right,targetSum,pre);
        // map这里记得回溯,pre是基本类型不是传引用所以不用回溯
        map.put(pre,map.getOrDefault(pre,0)-1);
    }



}
