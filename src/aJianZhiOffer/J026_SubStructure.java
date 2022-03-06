package aJianZhiOffer;

public class J026_SubStructure {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if(B==null) return false;
        return dfs(A,B);
    }

    public boolean dfs(TreeNode A,TreeNode B) {
        if(A==null) return false;
        if(A.val == B.val) {
            if(isSub(A,B)) return true;
        }
        if(dfs(A.left,B)) return true;
        if(dfs(A.right,B)) return true;
        return false;
    }

    public boolean isSub(TreeNode A, TreeNode B) {
        if(A==null || A.val!=B.val) return false;
        if(B.left!=null) {
            if(!isSub(A.left,B.left)) return false;
        }
        if(B.right!=null) {
            if(!isSub(A.right,B.right)) return false;
        }
        return true;
    }
}
