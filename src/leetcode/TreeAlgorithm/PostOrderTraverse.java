package leetcode.TreeAlgorithm;

import java.util.*;

public class PostOrderTraverse {
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
        // 迭代写法 和中序遍历的迭代写法相比多了一个prev来判断右子节点是否被访问过
        public List<Integer> postorderTraversal(TreeNode root) {
            Deque<TreeNode> treeStack = new ArrayDeque<>();
            List<Integer> res = new ArrayList<>();
            TreeNode prev = null;
            while(root!=null | !treeStack.isEmpty()) {
                while(root!=null) {
                    treeStack.offerFirst(root);
                    root = root.right;
                }
                //此处是peek，因为左右根，所以不能先poll
                TreeNode top = treeStack.peek();
                if( top.right ==null || top.right == prev) {
                    // 右子节点为空且或者右子节点已经被访问了，那么我们就访问当前节点并且出栈
                    treeStack.pop();
                    res.add(top.val);
                    prev = top;
                } else {
                    // 右子节点不为空且右子节点还没有被访问，我们以右子节点为根节点重复迭代步骤1
                    root = top.right;
                }
            }
            return res;
        }

}
