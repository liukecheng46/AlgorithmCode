package leetcode.ClassicProblem;

import java.util.ArrayList;

//约瑟夫环 todo
//掌握递推公式推导过程
//https://leetcode-cn.com/problems/yuan-quan-zhong-zui-hou-sheng-xia-de-shu-zi-lcof/solution/jian-zhi-offer-62-yuan-quan-zhong-zui-ho-dcow/
public class JosephusCircle {
    public int lastRemaining(int n, int m) {
        return recursive(n, m);
    }

    // 递归写法
    int recursive(int n, int m) {
        if (n == 1) return 0;
        int x = recursive(n - 1, m);
        return (x + m) % n;
    }

    //也可以用ArrayList模逆链表进行删除，但是O（mn）时间复杂度很差。可以解释为什么用ArrayList而不用LinkedList（内存连续所以寻址快），发散对比下这两个基本容器
    public int lastRemaining2(int n, int m) {
        ArrayList<Integer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        int idx = 0;
        while (n > 1) {
            idx = (idx + m - 1) % n;
            list.remove(idx);
            n--;
        }
        return (0);
    }

    // 基于数学公式使用动态规划反推
    // f(n) = (f(n-1)+m)%m
    public int lastRemaining3(int n, int m) {
        int x = 0;
        for (int i = 2; i <= n; i++) {
            x = (x + m) % i;
        }
        return x;
    }

}
