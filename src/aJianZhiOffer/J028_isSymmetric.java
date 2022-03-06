package aJianZhiOffer;

public class J028_isSymmetric {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    public boolean isSymmetric(TreeNode root) {
        // 注意特例
        if(root==null) return true;
        return dfs(root.left,root.right);
    }

    public boolean dfs(TreeNode a,TreeNode b) {
        if(a==null&&b==null) return true;
        //一个为空的情况要单独判断，不然会NPE
        if(a==null||b==null) return false;
        if(a.val==b.val) {
            return dfs(a.left,b.right)&&dfs(a.right,b.left);
        }
        return false;
    }
}
