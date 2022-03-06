package leetcode.ClassicProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

// 判断是否二叉平衡树 https://leetcode-cn.com/problems/validate-binary-search-tree/solution/yan-zheng-er-cha-sou-suo-shu-by-leetcode-solution/
// 当时做的时候想的是dfs返回以root为根节点的子树的最大值和最小值(由下至上)，这样除非自定义返回类不然挺麻烦，但是可以在dfs参数上加上最大和最小值限制（由上至下），这种上至下和下至上切换的递归思想很关键，需要掌握
// 也用中序遍历维护pre和cur进行比较 这个很好想到
public class isValidBST {
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
        public boolean isValidBST(TreeNode root) {
            return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }

        //由上至下，维护当前节点可能的最小最大值，超过则不是BST
        public boolean isValidBST(TreeNode node, long lower, long upper) {
            if (node == null) {
                return true;
            }
            if (node.val <= lower || node.val >= upper) {
                return false;
            }
            return isValidBST(node.left, lower, node.val) && isValidBST(node.right, node.val, upper);
        }

    int pre = Integer.MIN_VALUE;
    int cnt;
    int max = 1;
    List<Integer> res;
    public int[] findMode(TreeNode root) {
        res  =new ArrayList<>();
        dfs(root);
        int[] mode = new int[res.size()];
        for (int i = 0; i < res.size(); ++i) {
            mode[i] = res.get(i);
        }
        return mode;
    }

    public void dfs(TreeNode root) {
        if(root==null) return;
        dfs(root.left);
        if(pre == root.val) {
            cnt++;
        } else {
            cnt=1;
            pre = root.val;
        }
        if(cnt == max) {
            res.add(root.val);
        }
        if(cnt>max) {
            max = cnt;
            res.clear();
            res.add(root.val);
        }
        dfs(root.right);
    }

    class Solution {
        TreeNode parent;
        int _p;
        int _q;
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            _p=p.val;
            _q=q.val;
            dfs(root);
            return parent;
        }

        public HashSet<Integer> dfs(TreeNode root) {
            HashSet<Integer> set = new HashSet<>();
            if(root ==null) return set;
            HashSet<Integer> setLeft = dfs(root.left);
            HashSet<Integer> setRight = dfs(root.right);
            set.addAll(setLeft);
            set.addAll(setRight);
            set.add(root.val);
            if(set.contains(Integer.MIN_VALUE)) return new HashSet<>();
            if(set.contains(_p) && set.contains(_q)) {
                parent = root;
                HashSet<Integer> set2 = new HashSet<>();
                set2.add(Integer.MIN_VALUE);
                return set2;
            }
            return set;
        }
    }

}
