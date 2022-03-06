package aJianZhiOffer;

import java.util.Stack;

public class J033_verifyPostorder {
    // 验证后序遍历是否合法 https://leetcode-cn.com/problems/er-cha-sou-suo-shu-de-hou-xu-bian-li-xu-lie-lcof/solution/di-gui-he-zhan-liang-chong-fang-shi-jie-jue-zui-ha/
    // 第一个解法和判断出栈顺序是否合法一样
    // 第二个解法是O（n）的单调栈解法，需要掌握 todo
    public boolean verifyPostorder(int[] postorder) {
        Stack<Integer> stack = new Stack<>();
        int root = Integer.MAX_VALUE;
        for(int i = postorder.length - 1; i >= 0; i--) {
            // 如果左边的比root大
            if(postorder[i] > root) return false;
            while(!stack.isEmpty() && stack.peek() > postorder[i])
                root = stack.pop();
            stack.add(postorder[i]);
        }
        return true;
    }
}
