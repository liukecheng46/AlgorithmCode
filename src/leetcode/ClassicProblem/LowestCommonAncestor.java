package leetcode.ClassicProblem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 二叉树的最近公共祖先
// 自己做的时候想到的是从下至上，dfs函数返回以输入节点为root的子树所包含的值的哈希表，但是会超时（因为递归入栈出栈对象是哈希表）
// 后来看了下前置题 二叉搜索树的最近公共祖先，有了另一种思路，dfs返回某个值是否在以输入节点为root的子树中。自上至下搜索，如果两个值都在一边，那就继续搜这边，一直到两个值不在一边时停止
// 上面的这个解法需要重复dfs来判断是否在子树中，还可以进行优化
// todo
public class LowestCommonAncestor {
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

    //解法1 最简单易懂的，但是有重复dfs
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root ==null) return null;
        if(dfs(root.left,p.val)&&dfs(root.left,q.val)) return lowestCommonAncestor(root.left,p,q);
        if(dfs(root.right,p.val)&&dfs(root.right,q.val)) return lowestCommonAncestor(root.right,p,q);
        return root;
    }

    //查找某个值是否出现在root为根的子树中，会重复进行
    public boolean dfs(TreeNode root,int val) {
        if(root ==null) return false;
        if(root.val ==val) return true;
        return dfs(root.left,val)||dfs(root.right,val);
    }



    //解法2 去除重复判断的优化版本 O(n)  https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/solution/236-er-cha-shu-de-zui-jin-gong-gong-zu-xian-hou-xu/
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor2(root.left, p, q);
        TreeNode right = lowestCommonAncestor2(root.right, p, q);
        if(left == null && right == null) return null; // 1.
        if(left == null) return right; // 3.
        if(right == null) return left; // 4.
        return root; // 2. if(left != null and right != null)
    }


    // 解法3 用哈希表存储每个值对应的父节点，再从p遍历到根节点，将经过节点存入set，最后从q往上遍历，第一个在set中的节点即为结果 todo
    // 这种解法可以算这类问题通解(扩展到N叉树时，前面两种解法就失去一般性了)，还可以解LeetCode 1257 最小公共区域
    Map<Integer, TreeNode> parent = new HashMap<Integer, TreeNode>();
    Set<Integer> visited = new HashSet<Integer>();

    public void dfs(TreeNode root) {
        if (root.left != null) {
            parent.put(root.left.val, root);
            dfs(root.left);
        }
        if (root.right != null) {
            parent.put(root.right.val, root);
            dfs(root.right);
        }
    }

    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root);
        while (p != null) {
            visited.add(p.val);
            p = parent.get(p.val);
        }
        while (q != null) {
            if (visited.contains(q.val)) {
                return q;
            }
            q = parent.get(q.val);
        }
        return null;
    }


    TreeNode pre = new TreeNode();
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        TreeNode newRoot = new TreeNode();
        dfs(newRoot,root1,root2);
        return newRoot;

    }

    public void dfs(TreeNode root,TreeNode root1, TreeNode root2) {
        if(root1==null & root2==null) {
            root = null;
            return;
        }
        int val1 = root1==null?0:root1.val;
        int val2 = root2==null?0:root2.val;
        root.val = val1+val2;

        TreeNode left = new TreeNode();
        TreeNode right = new TreeNode();
        root.left = left;
        root.right = right;
        if(root1==null&&root2!=null) {
            dfs(root.left,null,root2.left);
            dfs(root.right,null,root2.right);
            return;
        }
        if(root1!=null&&root2==null) {
            dfs(root.left,root1.left,null);
            dfs(root.right,root1.right,null);
            return;
        }
        dfs(root.left,root1.left,root2.left);
        dfs(root.right,root1.right,root2.right);
        return;
    }
}
