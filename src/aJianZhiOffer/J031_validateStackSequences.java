package aJianZhiOffer;


import java.util.HashMap;
import java.util.Map;

public class J031_validateStackSequences {
    Map<Integer, Integer> OrderMap = new HashMap<>();

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        for (int i = 0; i < pushed.length; i++) {
            OrderMap.put(pushed[i], i);
        }
        return dfs(pushed, popped, 0,popped.length - 1);
    }

    public boolean dfs(int[] pushed, int[] popped,int beginIndex, int lastIndex) {
        if(beginIndex>=lastIndex) return true;
        int lastPop = popped[lastIndex];
        int firstPushedBeforeIndex;
        for (firstPushedBeforeIndex = lastIndex - 1; firstPushedBeforeIndex >= beginIndex; firstPushedBeforeIndex--) {
            //找到第一个在最后出栈数之前入栈的数
            if (OrderMap.get(popped[firstPushedBeforeIndex]) < OrderMap.get(lastPop)) break;
        }
        int i = firstPushedBeforeIndex;
        while(i>=beginIndex) {
            // 在出栈顺序中，这个数之前出栈的数不能有入栈顺序在最后出栈数之后的。
            if (OrderMap.get(popped[i]) > OrderMap.get(lastPop)) return false;
            i--;
        }
        // 继续判断在最后出栈数之前和之后的序列是否正确
        return dfs(pushed,popped,beginIndex,firstPushedBeforeIndex) && dfs(pushed,popped,firstPushedBeforeIndex+1,lastIndex-1);
    }


    // 正常写法
    public boolean validateStackSequences2(int[] pushed, int[] popped) {
        int[] stack = new int[pushed.length];
        int top = 0, j = 0;
        for(int i = 0; i < pushed.length; i++){
            stack[top] = pushed[i]; // push

            while(j < popped.length && top >= 0 && stack[top] == popped[j]){
                top--; // pop
                j++;
            }
            top++;
        }

        return j==popped.length;
    }
}
