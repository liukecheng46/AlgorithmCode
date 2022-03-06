package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

//贪心+单调栈+字符串处理
//https://leetcode-cn.com/problems/remove-k-digits/
public class removeKdigits {
    public String removeKdigits(String num, int k) {
        int n =num.length();
        Deque<Character> stack = new ArrayDeque<>();
        for(int i=0;i<n;i++) {
            while(!stack.isEmpty() && k>0 && stack.peek()>num.charAt(i)) {
                stack.poll();
                k--;
            }
            //0的特殊情况
            if(num.charAt(i) =='0' && stack.isEmpty()) continue;
            stack.push(num.charAt(i));
        }

        //如果k没用完（数组递增如12345）
        while(k-->0) {
            stack.poll();
        }

        if(stack.isEmpty()) return "0";

        StringBuilder res = new StringBuilder();
        while(!stack.isEmpty()) {
            res.append(stack.pollLast());
        }
        return res.toString();
    }
}
