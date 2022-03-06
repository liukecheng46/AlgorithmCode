package aJianZhiOffer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class J032_levelOrder {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    Deque<TreeNode> dq;
    List<List<Integer>> res;
    List<Integer> cur;
    public List<List<Integer>> levelOrder(TreeNode root) {
        res = new ArrayList<>();
        dq = new ArrayDeque<>();
        cur = new ArrayList<>();
        if(root==null) return res;
        dq.offer(root);
        while(!dq.isEmpty()) {
            int size = dq.size();
            cur.clear();
            while(size-->0) {
                TreeNode front = dq.poll();
                cur.add(front.val);
                if(front.left!=null) dq.offer(front.left);
                if(front.right!=null) dq.offer(front.right);
            }
            res.add(new ArrayList<>(cur));
        }
        return res;
    }

    class Solution {
        Deque<TreeNode> dq;
        List<List<Integer>> res;
        List<Integer> cur;
        public List<List<Integer>> levelOrder(TreeNode root) {
            res = new ArrayList<>();
            dq = new ArrayDeque<>();
            cur = new ArrayList<>();
            if(root==null) return res;
            dq.offer(root);
            boolean flag = false;
            while(!dq.isEmpty()) {
                int size = dq.size();
                cur.clear();
                while(size-->0) {
                    TreeNode front = dq.poll();
                    if(flag) cur.add(0,front.val);
                    else cur.add(front.val);
                    if(front.left!=null) dq.offer(front.left);
                    if(front.right!=null) dq.offer(front.right);
                }
                flag=!flag;
                res.add(new ArrayList<>(cur));
            }
            return res;
        }
    }
}
