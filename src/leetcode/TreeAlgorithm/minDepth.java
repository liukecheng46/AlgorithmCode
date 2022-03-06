package leetcode.TreeAlgorithm;

import com.sun.source.tree.Tree;

public class minDepth {
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
    int min = Integer.MAX_VALUE;
    public int minDepth(TreeNode root) {
        dfs(root,1);
        return min;
    }

    public void dfs(TreeNode root,int depth) {
        if(root.left ==null && root.right == null) min = Math.min(min,depth);
        if(root.left!=null) dfs(root.left,depth+1);
        if(root.right!=null) dfs(root.right,depth+1);
    }

    public int dfs2(TreeNode root) {
        if(root ==null) return 0;
        if(root.left ==null && root.right == null) return 1;

        int min = Integer.MAX_VALUE;
        if(root.left!=null) {
            min = Math.min(min,dfs2(root.left));
        }
        if(root.right!=null) {
            min = Math.min(min,dfs2(root.right));
        }

        return min+1;
    }
}
