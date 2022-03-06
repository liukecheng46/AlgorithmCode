package aJianZhiOffer;

public class J055_isBalanced {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // 写的时候也觉得这种O（n^2）的写法不太行，可以从getdepth入手，在获取的同时比较，用某个特殊值表示已经false
    public boolean isBalanced(TreeNode root) {
        if(root==null) return true;
        if(Math.abs(getDepth(root.left)-getDepth(root.right))<=1) {
            return isBalanced(root.left) && isBalanced(root.right);
        } else return false;
    }

    public int getDepth(TreeNode root) {
        if(root==null) return 0;
        return Math.max(getDepth(root.left),getDepth(root.right))+1;
    }


    // O（n）解法 todo
    public boolean isBalanced2(TreeNode root) {
        return height(root) >= 0;
    }

    public int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        } else {
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }
}
