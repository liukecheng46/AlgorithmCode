package leetcode.ClassicProblem;


import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

// 水壶问题 https://leetcode-cn.com/problems/water-and-jug-problem/
// 很经典的问题，有记忆化bfs和数学双解
public class waterProblem {
    // 因为数据很大，所以dp和记忆化dfs都会爆内存，需要使用记忆化bfs来节省内存（提前出队）
    // 主要是状态空间很难想出来：
    //把 X 壶的水灌进 Y 壶，直至灌满或倒空；
    //把 Y 壶的水灌进 X 壶，直至灌满或倒空；
    //把 X 壶灌满；
    //把 Y 壶灌满；
    //把 X 壶倒空；
    //把 Y 壶倒空。
    class Solution {
        public boolean canMeasureWater(int x, int y, int z) {
            Deque<int[]> stack = new LinkedList<int[]>();
            stack.push(new int[]{0, 0});
            Set<Long> seen = new HashSet<Long>();
            while (!stack.isEmpty()) {
                if (seen.contains(hash(stack.peek()))) {
                    stack.pop();
                    continue;
                }
                seen.add(hash(stack.peek()));

                int[] state = stack.pop();
                int remain_x = state[0], remain_y = state[1];
                if (remain_x == z || remain_y == z || remain_x + remain_y == z) {
                    return true;
                }
                // 把 X 壶灌满。
                stack.push(new int[]{x, remain_y});
                // 把 Y 壶灌满。
                stack.push(new int[]{remain_x, y});
                // 把 X 壶倒空。
                stack.push(new int[]{0, remain_y});
                // 把 Y 壶倒空。
                stack.push(new int[]{remain_x, 0});
                // 把 X 壶的水灌进 Y 壶，直至灌满或倒空。
                stack.push(new int[]{remain_x - Math.min(remain_x, y - remain_y), remain_y + Math.min(remain_x, y - remain_y)});
                // 把 Y 壶的水灌进 X 壶，直至灌满或倒空。
                stack.push(new int[]{remain_x + Math.min(remain_y, x - remain_x), remain_y - Math.min(remain_y, x - remain_x)});
            }
            return false;
        }

        public long hash(int[] state) {
            return (long) state[0] * 1000001 + state[1];
        }

        // 数学
        // 因为两个瓶子不可能同时不满，所以给某一个瓶子灌满的操作等价于于从初始状态给一个瓶子灌满或者给两个瓶子灌满
        // 所以无论如何操作，每一次操作都等价于给两个瓶子里的水同时加减x或y的水 ->ax+by=z
        // 根据数据，这个公式有解的充要条件是z是x和y的gcd的倍数
        public boolean canMeasureWater2(int x, int y, int z) {
            if (x + y < z) {
                return false;
            }
            if (x == 0 || y == 0) {
                return z == 0 || x + y == z;
            }
            return z % gcd(x, y) == 0;
        }

        public int gcd(int a,int b) {
            return b==0?a:gcd(b,a%b);
        }
    }
    }

