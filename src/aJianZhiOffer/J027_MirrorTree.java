package aJianZhiOffer;

public class J027_MirrorTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    public TreeNode mirrorTree(TreeNode root) {
        return dfs(root);
    }

    public TreeNode dfs(TreeNode root) {
        if(root==null) return null;
        TreeNode left = dfs(root.right);
        TreeNode right =dfs(root.left);
        root.left = left;
        root.right= right;
        return root;
    }
}
