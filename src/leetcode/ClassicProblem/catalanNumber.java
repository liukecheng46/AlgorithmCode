package leetcode.ClassicProblem;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

// 卡特兰数相关问题
// 原理及公式推导详见notion笔记
public class catalanNumber {
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

    // 不同二叉搜索树的个数 https://leetcode-cn.com/problems/unique-binary-search-trees/solution/bu-tong-de-er-cha-sou-suo-shu-by-leetcode-solution/
    public int numTrees(int n) {
        int[] catalan = new int[n+1];
        catalan[0] = 1;
        catalan[1] = 1;
        for(int i=2;i<=n;i++) {
            for(int j=0;j<i;j++) {
                catalan[i]+=catalan[j]*catalan[i-j-1];;
            }
        }
        return catalan[n];
    }

    // 不同二叉搜索树的个数2 https://leetcode-cn.com/problems/unique-binary-search-trees-ii/
    // 打印所有情况
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> catalan = new ArrayList<>();
        if(n==0) return catalan;
        return dfs(1,n);
    }

    //递归函数每次返回当前范围的情况下可以构成子树的所有情况
    public List<TreeNode> dfs(int down,int upper) {
        List<TreeNode> res = new ArrayList<>();
        if(down>upper) {
            //空也需要加null，不然无法进入下面的双重循环
            res.add(null);
            return res;
        }
        if(down==upper) {
            TreeNode tree = new TreeNode(down);
            res.add(tree);
            return res;
        }
        for(int i=down;i<=upper;i++) {
            List<TreeNode> left = dfs(down,i-1);
            List<TreeNode> right = dfs(i+1,upper);
            for (TreeNode leftTree : left) {
                for (TreeNode rightTree : right) {
                    TreeNode root = new TreeNode(i);
                    root.left = leftTree;
                    root.right = rightTree;
                    //加入到最终结果中
                    res.add(root);
                }
            }
        }
        return res;
    }

    // 括号生成（生成所有情況） https://leetcode-cn.com/problems/generate-parentheses/solution/gua-hao-sheng-cheng-by-leetcode-solution/
    // 不同于构造二叉搜索树的所有情况，数组形式的括号生成所有情况有简单的回溯dfs方法，
    // 也可以用dfs函数返回小n的所有情况，调用卡特兰数公式生成当前的所有情况

    //解法一：正常回溯dfs
    List<String> res;
    public List<String> generateParenthesis(int n) {
        res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        dfs(sb,n,0,0);
        return res;
    }

    public void dfs(StringBuilder sb,int n,int left,int right) {
        if(left==n && right==n) {
            res.add(sb.toString());
            return;
        }
        if(left<right) return;
        if(left<=n) {
            sb.append("(");
            dfs(sb,n,left+1,right);
            sb.deleteCharAt(sb.length()-1);
        }
        if(right<=n) {
            sb.append(")");
            dfs(sb,n,left,right+1);
            sb.deleteCharAt(sb.length()-1);
        }
    }

    // 解法二：构造所有二叉搜索树相同的dfs，dfs返回较小n(括号对数)的所有情况，调用卡特兰数公式生成当前的所有情况
    // 用记忆化记忆小n的所有情况进行剪枝
    class Solution3 {
        ArrayList[] cache = new ArrayList[100];

        public List<String> generate(int n) {
            //记忆化
            if (cache[n] != null) {
                return cache[n];
            }
            ArrayList<String> ans = new ArrayList<String>();
            if (n == 0) {
                ans.add("");
            } else {
                for (int c = 0; c < n; ++c) {
                    for (String left: generate(c)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("(");
                        sb.append(left);
                        sb.append(")");
                        for (String right: generate(n - 1 - c)) {
                            sb.append(right);
                            ans.add(sb.toString());
                            // stringBuilder回溯时删除最后一个字符串
                            int len =right.length();
                            while(len--> 0) {
                                sb.deleteCharAt(sb.length()-1);
                            }
                        }
                    }
                }
            }
            cache[n] = ans;
            return ans;
        }

        public List<String> generateParenthesis(int n) {
            return generate(n);
        }
    }
}
