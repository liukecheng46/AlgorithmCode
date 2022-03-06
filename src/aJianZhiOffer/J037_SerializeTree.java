package aJianZhiOffer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


// 用dfs序列化的话 反序列化时需要把string处理成一个列表，传引用给dfs（因为不能使用全局变量来存储当前dfs到的索引）
// 所以其实最好还是用bfs来进行序列化和反序列化 todo
public class J037_SerializeTree {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int x) {
            val = x;
        }
    }

    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        dfs(root,sb);
        // 去除最后一个分割符，
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public void dfs(TreeNode root,StringBuilder sb) {
        if(root==null) {
            sb.append("#,");
            return ;
        }
        sb.append(root.val).append(",");
        dfs(root.left,sb);
        dfs(root.right,sb);
    }

    // 要转化为一个List<String>传引用来递归调用，因为不能用全局变量存当前操作的索引位置
    public TreeNode rdeserialize(List<String> dataList) {
        if (dataList.get(0).equals("None")) {
            dataList.remove(0);
            return null;
        }

        TreeNode root = new TreeNode(Integer.valueOf(dataList.get(0)));
        dataList.remove(0);
        root.left = rdeserialize(dataList);
        root.right = rdeserialize(dataList);

        return root;
    }




    //bfs写法
    public class Codec {
        public String serialize(TreeNode root) {
            if(root == null) return "[]";
            StringBuilder res = new StringBuilder("[");
            Queue<TreeNode> queue = new LinkedList<>() {{ add(root); }};
            while(!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if(node != null) {
                    res.append(node.val + ",");
                    queue.add(node.left);
                    queue.add(node.right);
                }
                else res.append("null,");
            }
            res.deleteCharAt(res.length() - 1);
            res.append("]");
            return res.toString();
        }

        public TreeNode deserialize(String data) {
            if(data.equals("[]")) return null;
            String[] vals = data.substring(1, data.length() - 1).split(",");
            TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
            Queue<TreeNode> queue = new LinkedList<>() {{ add(root); }};
            int i = 1;
            while(!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if(!vals[i].equals("null")) {
                    node.left = new TreeNode(Integer.parseInt(vals[i]));
                    queue.add(node.left);
                }
                i++;
                if(!vals[i].equals("null")) {
                    node.right = new TreeNode(Integer.parseInt(vals[i]));
                    queue.add(node.right);
                }
                i++;
            }
            return root;
        }
    }
}
