package leetcode;

import com.sun.source.tree.Tree;

import java.util.*;

//https://leetcode-cn.com/problems/even-odd-tree/
//层次 z字形遍历变种
public class EvenOddTree {
    public static class TreeNode {
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

    public static boolean isEvenOddTree(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        boolean flag = true;
        while (!q.isEmpty()) {
            int n = q.size();
            int temp = flag ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//            先将下一层的入队
            for (int i = 0; i < n; i++) {
                TreeNode top = q.poll();
                int cur = top.val;
                if (flag && (cur % 2 == 0 || cur <= temp)) return false;
                if (!flag && (cur % 2 != 0 || cur >= temp)) return false;
                temp = cur;
                if (top.left != null) q.offer(top.left);
                if (top.right != null) q.offer(top.right);

            }
            flag = !flag;
        }
        return true;
    }

}
