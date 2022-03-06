package leetcode.TreeAlgorithm;

import java.util.*;

// 层次遍历+Z字形遍历
public class LevelOrderTraverse {
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

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> curLevel = new ArrayList<>();
            while (size-- > 0) {
                TreeNode cur = q.poll();
                curLevel.add(cur.val);
                if (cur.left != null) {
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }
            res.add(curLevel);
        }
        return res;
    }

    // Z字形遍历 需要掌握双端队列的最优版本
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int level = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            // 这里也可以用双端队列，奇数层offerlast,偶数层offerfirst，这样避免Collections.reverse的O（n）时间
            List<Integer> curLevel = new ArrayList<>();
            while (size-- > 0) {
                TreeNode cur = q.poll();
                if ((level & 1) == 1) {
                    curLevel.add(0,cur.val);
                } else {
                    curLevel.add(cur.val);
                }
                if (cur.left != null) {
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }
//            if ((level & 1) == 1) Collections.reverse(curLevel);
            res.add(curLevel);
            level++;
        }
        return res;
    }

    // 从下到上的层次遍历 https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        Deque<List<Integer>> res = new LinkedList<>();
        if (root == null) return new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> curLevel = new ArrayList<>();
            while (size-- > 0) {
                TreeNode cur = q.poll();
                curLevel.add(cur.val);
                if (cur.left != null) {
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }
            res.offerFirst(curLevel);
        }
        return new ArrayList<>(res);
    }

    // 二叉树的垂直遍历，就是层次遍历+标记x坐标 https://www.jiuzhang.com/solution/binary-tree-vertical-order-traversal/
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();
        if (root == null) {
            return results;
        }
        Map<Integer, List<Integer>> map = new TreeMap<Integer, List<Integer>>();
        Queue<Integer> qCol = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        qCol.offer(0);

        while(!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            int col = qCol.poll();
            if(!map.containsKey(col)) {
                map.put(col, new ArrayList<Integer>(Arrays.asList(curr.val)));
            } else {
                map.get(col).add(curr.val);
            }
            if(curr.left != null) {
                queue.offer(curr.left);
                qCol.offer(col - 1);
            }
            if(curr.right != null) {
                queue.offer(curr.right);
                qCol.offer(col + 1);
            }
        }
        for(int n : map.keySet()) {
            results.add(map.get(n));
        }
        return results;
    }
}
