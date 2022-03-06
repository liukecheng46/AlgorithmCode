package aJianZhiOffer;

// 替换空格
public class J005_ReplaceSpace {
    public String replaceSpace(String s) {
        char[] ss = s.toCharArray();
        StringBuilder res = new StringBuilder();
        for(int i=ss.length-1;i>=0;i--) {
            if(ss[i]!=' ') res.insert(0,ss[i]);
            else res.insert(0,"%20");
        }
        return res.toString();
    }
}
