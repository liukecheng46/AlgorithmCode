package aJianZhiOffer;

import com.sun.source.tree.Tree;

public class J055_MaxDepth {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    int max;
    public int maxDepth(TreeNode root) {
        max =1;
        dfs(root,1);
        return max;
    }

    public void dfs(TreeNode root, int depth) {
        if(root.left==null&&root.right==null) max = Math.max(max,depth);
        if(root.left!=null) dfs(root.left,depth+1);
        if(root.right!=null) dfs(root.right,depth+1);
    }
}
