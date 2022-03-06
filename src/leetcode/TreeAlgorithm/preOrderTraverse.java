package leetcode.TreeAlgorithm;

import java.util.*;

//先序遍历 根左右（后序遍历 可以由先序遍历得来， 修改一下先序遍历 使得 根右左，再倒置一下就是左右根）
public class preOrderTraverse {
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

    //通用迭代解法：左边全部压栈，最后的节点出栈，如果有右孩子的话，重复上述过程（和中序遍历的迭代写法通用，改一下print位置）
    public void preOrderTraverse(TreeNode root) {
        Deque<TreeNode> treeStack = new ArrayDeque<>();
        while(root!=null | !treeStack.isEmpty()) {
            while(root!=null) {
                System.out.println(root.val);
                treeStack.offerFirst(root);
                root = root.left;
            }
            TreeNode top = treeStack.pollFirst();
            root = top.right;
        }
    }

    //后序遍历： 修改遍历顺序再reverse的后序遍历
    public List<Integer> postorderTraversal(TreeNode root) {
        Deque<TreeNode> treeStack = new ArrayDeque<>();
        List<Integer> res = new ArrayList<>();
        while(root!=null | !treeStack.isEmpty()) {
            while(root!=null) {
                res.add(root.val);
                treeStack.offerFirst(root);
                root = root.right;
            }
            TreeNode top = treeStack.pollFirst();
            root = top.left;
        }
        Collections.reverse(res);
        return res;
    }

    // 迭代解法2：前序遍历还有一种更简单的迭代写法，但是这种写法和中序遍历的迭代写法不通用
    // N叉树的前序遍历用第一种迭代解法是不行的，但是用这个可以 https://leetcode-cn.com/problems/n-ary-tree-preorder-traversal
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root==null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>(); // 入栈为中右左 出栈为中左右
        stack.add(root);
        while (!stack.isEmpty()) {
            TreeNode treeNode = stack.pop();
            result.add(treeNode.val);
            //修改顺序就变为后序遍历
            if (treeNode.right!=null) {
                stack.push(treeNode.right);
            }
            if (treeNode.left!=null) {
                stack.push(treeNode.left);
            }
        }
        return result;
    }

    // 解法3： 空间复杂度为O（1）的Morris遍历
    // 标准的莫里斯遍历写法 是在原有树结构上新增每个前驱节点（当前节点的左子节点的最右子节点）到当前节点的连接并且在后续遍历中删除，因此不会改变原有树结构
    // 构造的也是一个中序遍历的链表结构，但是在构造过程中根据根节点的访问次数按照前序遍历的顺序打印
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        TreeNode pre = null;
        while(root!=null) {
            //如果左节点不为空，就将当前节点连带右子树全部挂到左节点的最右子树下面
            if(root.left!=null) {
                pre = root.left;
                //添加一个pre.right!=root的条件，当重新遍历回来时，删除之前添加的从前驱节点到当前根节点的连接
                while(pre.right!=null && pre.right!=root) {
                    pre = pre.right;
                }
                //第一次遍历节点，此时前驱节点还未连接当前根节点，进行连接,并且根节点指向左孩子，前序遍历时先访问根节点再访问左子树，所以在这里访问根节点（第一次）
                if(pre.right==null) {
                    pre.right = root;
                    res.add(root.val);
                    root = root.left;
                    // 如果指向根节点，说明此时是第二次遍历，左子树已经访问完了，直接将root指向右孩子来访问右子树，此时需要删除之前创建的连接
                } else  {
                    pre.right = null;
                    root = root.right;
                }
                // 如果没有左孩子了，直接访问该节点，再将root指向右孩子来访问右子树
            } else {
                res.add(root.val);
                root = root.right;
            }
        }
        return res;
    }

    // 二叉树平铺为先序遍历的右向链表形式 https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list/solution/
    // 类似于改变原树结构的morrios遍历，只是是前序而不是中序
    // 对于每个根节点，将右子节点接到左子节点的最右节点，再将根节点右子节点指向左子节点，左子节点指向null
    public void flatten(TreeNode root) {
        TreeNode cur = root;
        while(cur!=null) {
            TreeNode r= cur.right;
            TreeNode l  = cur.left;
            if(l!=null) {
                while(l.right!=null) l =l.right;
                l.right = r;
                cur.right = cur.left;
                cur.left = null;
            }
            cur = cur.right;
        }
    }
}
