package leetcode.ClassicProblem;

//大数乘法，转为字符串进行操作
public class StringMultiply {
    //优化竖式乘法 https://leetcode-cn.com/problems/multiply-strings/solution/you-hua-ban-shu-shi-da-bai-994-by-breezean/
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int[] res = new int[num1.length()+num2.length()];
        for(int i=num1.length()-1;i>=0;i--) {
            int n1=num1.charAt(i)-'0';
            for(int j=num2.length()-1;j>=0;j--) {
                int n2 = num2.charAt(j)-'0';
                int mul = n1*n2;
                //这里很关键，低位可能是十位数（包含上次循环的进位，所以和mul直接相加，高位加上相加后十位数的部分，低位只取个位数的部分，进位后的高位会在下一次循环中处理）
                res[i+j+1]+=mul;
                res[i+j]+=res[i+j+1]/10;
                res[i+j+1]=res[i+j+1]%10;
            }
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            //只有可能第一个为0
            if (i == 0 && res[i] == 0) continue;
            ans.append(res[i]);
        }
        return ans.toString();
    }


    //大数相加，转化为字符串相加  经典模板
    public String addStrings(String num1, String num2) {
        int n = num1.length() - 1, m = num2.length() - 1;
        int flag = 0;
        StringBuilder res = new StringBuilder();
        while (n >= 0 || m >= 0||flag==1) {
            int u1 = n >= 0 ? num1.charAt(n) - '0' : 0;
            int u2 = m >= 0 ? num2.charAt(m) - '0' : 0;
            int sum = u1 + u2;
            sum += flag;
            flag =sum/10;
            res.append(sum%10);
            n--;
            m--;
        }
        return res.reverse().toString();
    }
}
