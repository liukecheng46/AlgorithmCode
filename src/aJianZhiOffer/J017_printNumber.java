package aJianZhiOffer;

import java.util.ArrayList;
import java.util.List;

public class J017_printNumber {
    public int[] printNumbers(int n) {
        int count= 0;
        int cur = 9;
        while(n-->0) {
            count+=cur;
            cur*=10;
        }
        int[] res =new int[count];
        for(int i=0;i<count;i++) {
            res[i] = i+1;
        }
        return res;
    }

    // 考虑大数情况，用递归解决
    List<String> res;
    char[] num = new char[] {'0','1','2','3','4','5','6','7','8','9'};
    StringBuilder cur;
    public List<String> printNumbers2(int n) {
        res = new ArrayList<>();
        cur = new StringBuilder();
        for(int i=1;i<=n;i++) {
            dfs(0,i);
        }
        return res;
    }

    public void dfs(int index,int n) {
        if(index==n) {
            res.add(cur.toString());
            return;
        }
        for(char c:num) {
            if(index==0 && c=='0') continue;
            cur.append(c);
            dfs(index+1,n);
            cur.deleteCharAt(cur.length()-1);
        }
    }
}
