package leetcode.GameTheory;

import java.util.HashMap;
import java.util.Map;

// 猫和老鼠 https://leetcode-cn.com/problems/cat-and-mouse/solution/pythonjavajavascriptgo-zui-da-zui-xiao-b-fyt8/
// MiniMax算法模板（记忆化dfs拓展一下）
public class CatAndMouse {
    class Solution {
        // 记忆化map，map<回合数，map<当前猫鼠位置的唯一表示,结果>>
        private Map<Integer, Map<Integer, Integer>> cache;
        private int[][] graph;
        public int catMouseGame(int[][] graph_) {
            cache = new HashMap<>();
            graph = graph_;
            int ans = minMax(0, 1, 2);
            if(ans == -1)
                return 1;
            else if(ans == 1)
                return 2;
            return 0;
        }

        //i表示第几回合，m表示老鼠位置，c表示猫位置
        private int minMax(int i, int m, int c){
            if(i > 2 * graph.length)
                return 0;
            if(m == 0)
                return -1;
            if(c == m)
                return 1;
            Map<Integer, Integer> memo = cache.getOrDefault(i, new HashMap<Integer, Integer>());
            // 当前猫鼠位置的唯一表示方法
            int key = m * graph.length + c;
            if(memo.containsKey(key))
                return memo.get(key);
            int res = i % 2 == 0 ? 1 : -1;
            //老鼠回合
            if(i % 2 == 0){
                for(int nxt: graph[m]){
                    res = Math.min(res, minMax(i + 1, nxt, c));
                    if(res == -1)
                        break;
                }
            }else{
                //猫的回合
                for(int nxt: graph[c]){
                    if(nxt != 0){
                        res = Math.max(res, minMax(i + 1, m, nxt));
                        if(res == 1)
                            break;
                    }
                }
            }
            //记录当前棋局的唯一标志和结果
            memo.put(key, res);
            //记录第几步的所有棋局
            cache.put(i, memo);
            return res;
        }
    }

}
