package leetcode.TreeAlgorithm;

import java.util.*;

//中序遍历
public class inOrderTraverse {
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

    // 通用迭代解法：左边全部压栈，最后的节点出栈，如果有右孩子的话，重复上述过程
    public void inOrderTraverse(TreeNode root) {
        Deque<TreeNode> treeStack = new ArrayDeque<>();
        while(root!=null | !treeStack.isEmpty()) {
            while(root!=null) {
                treeStack.offerFirst(root);
                root = root.left;
            }
            TreeNode top = treeStack.pollFirst();
            System.out.println(top.val);
            root = top.right;
        }
    }


    // 还有一种O（1）空间复杂度的莫里斯遍历
    // 莫里斯遍历本质其实是生成一个二叉树的中序遍历的链表结构
    // 第一种写法：很容易理解，但是会改变原有树结构最后生成一个中序遍历的链表，这种改变原有结构的方法没有区分第一次和第二次遍历根节点，因此不能用于前序遍历
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        TreeNode pre = null;
        while(root!=null) {
            //如果左节点不为空，就将当前节点连带右子树全部挂到
            //左节点的最右子树下面
            if(root.left!=null) {
                pre = root.left;
                while(pre.right!=null) {
                    pre = pre.right;
                }
                pre.right = root;
                //将root指向root的left
                TreeNode tmp = root;
                root = root.left;
                //改变原有树结构
                tmp.left = null;
                //左子树为空，则打印这个节点，并向右边遍历
            } else {
                res.add(root.val);
                root = root.right;
            }
        }
        return res;
    }

    // 标准的莫里斯遍历写法 是在原有树结构上新增每个前驱节点（当前节点的左子节点的最右子节点）到当前节点的连接并且在后续遍历中删除，因此不会改变原有树结构，但是相对第一种写法更难理解
    // 也可以用于前序遍历
    public List<Integer> inorderTraversal2(TreeNode root) {
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
                //第一次遍历节点，此时前驱节点还未连接当前根节点，进行连接,并且根节点指向左孩子
                if(pre.right==null) {
                    pre.right = root;
                    root = root.left;
                    // 如果指向根节点，说明此时是第二次遍历，左子树已经访问完了，因此访问该节点（中序遍历先访问左子树），并将root指向右孩子来访问右子树，此时需要删除之前创建的连接
                } else  {
                    pre.right = null;
                    res.add(root.val);
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



    // 从前序和中序遍历中重构二叉树 https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal
    // 做的时候大致的递归思路想到了，但是很多优化的地方没有。
    // 首先 这种构造的题目dfs需要返回TreeNode
    // 对于dfs函数，找前序数组和的第一个数在中序数组的位置，切割数组，左右孩子等于使用切割后的数组再调用dfs
    // 优化细节：1.找前序数组的数在中序数组的位置，用哈希表优化到O(1)   2.每次都用Arrays.copyofRange切割数组会开辟很多不必要的空间，dfs传参加入前序和中序数组的起止索引来模拟切割
    class Solution {
        private Map<Integer, Integer> indexMap;

        public TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_right, int inorder_left, int inorder_right) {
            //终止条件判定很关键，前序数组大小为0时停止（无法再构造根节点）
            if (preorder_left > preorder_right) {
                return null;
            }

            // 前序遍历中的第一个节点就是根节点
            int preorder_root = preorder_left;
            // 找到在中序遍历中的索引位置
            int inorder_root = indexMap.get(preorder[preorder_root]);

            TreeNode root = new TreeNode(preorder[preorder_root]);
            // 得到左子树中的节点数目
            int size_left_subtree = inorder_root - inorder_left;
            // 递归地构造左子树，并连接到根节点
            // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
            root.left = myBuildTree(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree, inorder_left, inorder_root - 1);
            // 递归地构造右子树，并连接到根节点
            // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
            root.right = myBuildTree(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right, inorder_root + 1, inorder_right);
            return root;
        }

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            int n = preorder.length;
            // 构造哈希映射，帮助我们快速定位根节点
            indexMap = new HashMap<Integer, Integer>();
            for (int i = 0; i < n; i++) {
                indexMap.put(inorder[i], i);
            }
            return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
        }
    }


    // 从中序和后序遍历中重构二叉树 https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/submissions/
    // 递归同理，放一个简洁版代码
    class Solution2 {

        HashMap<Integer,Integer> memo = new HashMap<>();
        int[] post;

        public TreeNode buildTree(int[] inorder, int[] postorder) {
            for(int i = 0;i < inorder.length; i++) memo.put(inorder[i], i);
            post = postorder;
            TreeNode root = buildTree(0, inorder.length - 1, 0, post.length - 1);
            return root;
        }

        public TreeNode buildTree(int is, int ie, int ps, int pe) {
            if(pe < ps) return null;

            int root = post[pe];
            int ri = memo.get(root);

            TreeNode node = new TreeNode(root);
            node.left = buildTree(is, ri - 1, ps, ps + ri - is - 1);
            node.right = buildTree(ri + 1, ie, ps + ri - is, pe - 1);
            return node;
        }
    }

    // 序列化和反序列化二叉搜索树 https://leetcode-cn.com/problems/serialize-and-deserialize-bst/solution/li-yong-liao-er-cha-sou-suo-shu-xing-zhi-2wno/
    // 序列化和反序列化二叉树需要把null节点也序列化，依此来判定树结构 https://leetcode-cn.com/problems/serialize-and-deserialize-binary-tree/
    // 但是二叉搜索树是有序的，所以可以利用这个性质，不需要序列化null节点
    // 和从前序和中序遍历中重构二叉树同理，区别是二叉搜索树只需要前序遍历即可重构
    // 注意：单独使用前序遍历和后序遍历加标记null节点的序列化字符串是可以直接重构二叉树的，但是中序遍历不行，因为中序遍历没法确定根节点，会有不同的树有同样的序列化结果
    class Solution3 {
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            dfs_s(root, sb);
            return sb.toString();
        }

        void dfs_s(TreeNode cur, StringBuilder sb){
            // 空结点不序列化
            if(cur == null) return;
            sb.append(cur.val + " ");
            dfs_s(cur.left, sb);
            dfs_s(cur.right, sb);
        }

        public TreeNode deserialize(String data) {
            if(data.length() == 0) return null;
            List<Integer> list = new ArrayList<>();
            for(String str : data.split(" ")){
                list.add(Integer.parseInt(str));
            }
            return dfs_d(list, 0, list.size() - 1);
        }

        TreeNode dfs_d(List<Integer> list, int l, int r){
            if(l > r) return null;
            // 根节点
            TreeNode root = new TreeNode(list.get(l));
            int k = l + 1;
            // 找分界点
            while(k <= r && list.get(k) < list.get(l)) k++;
            root.left = dfs_d(list, l + 1, k - 1);
            root.right = dfs_d(list, k, r);
            return root;
        }
    }

}
