package leetcode;


import java.util.Arrays;

// https://leetcode-cn.com/problems/the-number-of-weak-characters-in-the-game/solution/you-xi-zhong-ruo-jiao-se-de-shu-liang-by-3d2g/
// 二维属性，找有没有严格大于他的
// 反着来，维护两边都大的。先按攻击降序排，攻击相同就按防御升序排，维护最大防御，因为相同攻击防御是升序，所以当前防御如果小于最大防御那么他的攻击一定小，符合要求
public class numberOfWeakCharacters {
    public int numberOfWeakCharacters(int[][] properties) {
        Arrays.sort(properties, (o1, o2) -> {
            return o1[0] == o2[0] ? (o1[1] - o2[1]) : (o2[0] - o1[0]);
        });
        int maxDef = 0;
        int ans = 0;
        for (int[] p : properties) {
            if (p[1] < maxDef) {
                ans++;
            } else {
                maxDef = p[1];
            }
        }
        return ans;
    }
}
