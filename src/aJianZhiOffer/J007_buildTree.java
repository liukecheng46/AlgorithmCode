package aJianZhiOffer;

import java.util.HashMap;
import java.util.Map;

// 重构二叉树
// 哈希表存索引，然后dfs建树
public class J007_buildTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    Map<Integer,Integer> preMap;
    Map<Integer,Integer> inMap;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        preMap = new HashMap<>();
        inMap = new HashMap<>();
        for(int i=0;i<preorder.length;i++) preMap.put(preorder[i],i);
        for(int i=0;i<inorder.length;i++) inMap.put(inorder[i],i);
        TreeNode res = dfs(preorder,inorder,0,preorder.length-1,0,inorder.length-1);
        return res;
    }

    public TreeNode dfs(int[] pre,int[] in,int pl,int pr,int il,int ir) {
        if(pl==pr || il==ir) return new TreeNode(pre[pl]);
        if(pl>pr || il>ir) return null;
        TreeNode root = new TreeNode(pre[pl]);
        int index = inMap.get(pre[pl]);
        root.left = dfs(pre,in,pl+1,pr-ir+index,il,index-1);
        root.right = dfs(pre,in,pr-ir+index+1,pr,index+1,ir);
        return root;
    }
}
