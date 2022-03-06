package aJianZhiOffer;

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class J034_pathSum {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    List<List<Integer>> res;
    List<Integer> cur;
    public List<List<Integer>> pathSum(TreeNode root, int target) {
        res = new ArrayList<>();
        cur = new ArrayList<>();
        dfs(root,target);
        return res;
    }

    public void dfs(TreeNode root,int target) {
        if(root==null) return;
        cur.add(root.val);
        if(root.left==null &&root.right==null && target==root.val) {
            res.add(new ArrayList<>(cur));
        }
        dfs(root.left,target-root.val);
        dfs(root.right,target-root.val);
        cur.remove(cur.size()-1);
    }
}
