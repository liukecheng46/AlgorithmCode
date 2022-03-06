package leetcode.ClassicProblem;

// 最长同值路径
// https://leetcode-cn.com/problems/longest-univalue-path/
// 求非自顶向下的路径 经典模板问题，这种核心思想都是 dfs(root.left)+dfs(root.right)+root
public class longestUnivaluePath {
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

    int max;
    public int longestUnivaluePath(TreeNode root) {
        if(root == null) return 0;
        dfs(root);
        return max;
    }

    public int dfs(TreeNode root) {
        if(root == null) return 0;
        int l = dfs(root.left), r = dfs(root.right);
        if(root.left!=null) l = root.left.val == root.val? l+1:0;
        if(root.right!=null) r = root.right.val == root.val? r+1:0;
        max= Math.max(max,l+r);
        return Math.max(l,r);


    }

    int res2;
    public int sumNumbers(TreeNode root) {
        dfs2(root,0);
        return res2;
    }

    public void dfs2(TreeNode root,int val) {
        if(root == null) return;
        val=val*10+root.val;
        if(root.left==null && root.right==null) res2+=val;
        dfs2(root.left,val);
        dfs2(root.right,val);
    }
}
