package leetcode.ClassicProblem;

import com.sun.source.tree.Tree;

//二叉树的最大路径和 https://leetcode-cn.com/problems/binary-tree-maximum-path-sum/
// 非自顶向下的又一道模板题
public class maxPathSum {
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

    int res = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        dfs(root);
        return res;
    }

    public int dfs(TreeNode root) {
        if(root==null) return 0;
        // 这里可以先判断左右和0，避免下面的res做4次比较
        // int left = Math.max(dfs(root.left)),right = Math.max(dfs(root.right));
        // res = left + right + root.val
        // return Math.max(left,right)+root.val
        int left = dfs(root.left),right = dfs(root.right);

        res = Math.max(res,Math.max(left+right+root.val,Math.max(left+root.val,Math.max(right+root.val,root.val))));
        return Math.max(left+root.val,Math.max(right+root.val,root.val));
    }
}
