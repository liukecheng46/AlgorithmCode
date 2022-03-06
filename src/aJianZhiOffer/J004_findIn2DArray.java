package aJianZhiOffer;

// 二维数组中的查找
// 掌握O(n)解法
public class J004_findIn2DArray {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if(matrix.length==0) return false;
        int n = matrix.length,m = matrix[0].length;
        int x = n-1,y=0;
        while(x>=0 && y<=m-1) {
            if(matrix[x][y]>target) {
                x--;
            } else if(matrix[x][y]<target) {
                y++;
            } else return true;
        }
        return false;
    }
}
