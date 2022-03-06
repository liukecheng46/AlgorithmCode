package aJianZhiOffer;

import com.sun.source.tree.Tree;

public class J054_KthLargest {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

        int k;
        int res;
        public int kthLargest(TreeNode root, int k) {
            k=k;
            dfs(root);
            return res;
        }

        public void dfs(TreeNode root) {
            if(root==null ||k==0) return ;
            dfs(root.right);
            k--;
            if(k==0) res=root.val;
            dfs(root.left);
        }
}
