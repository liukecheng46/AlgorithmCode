package leetcode;

//字符串处理
//https://leetcode-cn.com/problems/day-of-the-year/
public class dayofYear {
    static int[] nums = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static int[] f = new int[13];
    static {
        for (int i = 1; i <= 12; i++) f[i] = f[i - 1] + nums[i - 1];
    }
    public int dayOfYear(String date) {
        String[] ss = date.split("-");
        int y = Integer.parseInt(ss[0]), m = Integer.parseInt(ss[1]), d = Integer.parseInt(ss[2]);
        boolean isLeap = (y % 4 == 0 && y % 100 != 0) || y % 400 == 0;
        int ans = m > 2 && isLeap ? f[m - 1] + 1 : f[m - 1];
        return ans + d;
    }

}
