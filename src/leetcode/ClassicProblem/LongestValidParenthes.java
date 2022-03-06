package leetcode.ClassicProblem;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

// 最长有效括号 https://leetcode-cn.com/problems/longest-valid-parentheses/solution/zui-chang-you-xiao-gua-hao-by-leetcode-solution/
// 经典题目 多解法 todo
public class LongestValidParenthes {
    // 动态规划 dp[i]表示以i结尾的最长有效括号子串
    // s[i]=) 且s[i−1]=(： dp[i]=dp[i−2]+2
    // s[i]=) 且s[i−1]=)： dp[i]=dp[i−1]+dp[i−dp[i−1]−2]+2  这个很难想到
    public int longestValidParentheses(String s) {
        int maxans = 0;
        int[] dp = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }


    // 栈
    // 这个解法更加反常识，比动态规划还难想
    public int longestValidParentheses2(String s) {
        int maxans = 0;
        Deque<Integer> stack = new LinkedList<Integer>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }

    // 贪心思想扫两遍 todo
    // O（1）空间
    // 有点双指针的感觉在里面，我做的时候就想用双指针，但是做不出来
    public int longestValidParentheses3(String s) {
        int left = 0, right = 0, maxlength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * right);
            } else if (right > left) {
                left = right = 0;
            }
        }
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * left);
            } else if (left > right) {
                left = right = 0;
            }
        }
        return maxlength;
    }

    //  最妙的一种解法
    //  用栈模拟一遍，将所有无法匹配的括号的位置全部置1
    //  例如: "()(()"的mark为[0, 0, 1, 0, 0]
    //  再例如: ")()((())"的mark为[1, 0, 0, 1, 0, 0, 0, 0]
    //  经过这样的处理后, 此题就变成了寻找最长的连续的0的长度
    public int longestValidParentheses4(String s) {
        if(s==null || s.length()==0) return 0;
        int len = s.length();
        Stack<Integer> stack = new Stack<>();
        char[] array = s.toCharArray();
        int[] mark = new int[len]; //合法的括号下标标为1
        for(int i=0; i<len; i++){
            if(array[i]=='('){
                stack.push(i);
                // 右括号判断是否匹配
            }else{
                if(!stack.isEmpty() && array[stack.peek()]=='('){
                    mark[stack.pop()] = 1;
                    mark[i] = 1;
                }
            }
        }
        int max = 0;
        int cur = 0;
        //求最长连续1的长度
        for(int i:mark){
            if(i==1){
                cur ++;
                max = Math.max(max,cur);
            }else{
                cur = 0;
            }
        }
        return max;
    }

}
