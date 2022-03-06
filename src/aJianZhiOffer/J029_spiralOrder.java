package aJianZhiOffer;

import java.util.LinkedList;
import java.util.List;

public class J029_spiralOrder {
    int[] res;
    int index;
    public int[] spiralOrder(int[][] matrix) {
        int n = matrix.length;
        if(n==0) return new int[] {};
        int m = matrix[0].length;
        res = new int[n*m];
        index=0;
        for(int k=0;k<=Math.min(n,m)/2;k++) {
            printOuterLayer(matrix,k,n,m);
        }
        if(index!=(n*m)) res[index++] = matrix[n/2][m/2];
        return res;
    }

    public void printOuterLayer(int[][] matrix,int k,int n,int m) {

        for(int i=k;i<m-k-1;i++) if(index!=n*m) res[index++] = matrix[k][i];
        for(int i=k;i<n-k-1;i++) if(index!=n*m) res[index++] = matrix[i][m-k-1];
        for(int i=m-k-1;i>k;i--) if(index!=n*m) res[index++] = matrix[n-k-1][i];
        for(int i=n-k-1;i>k;i--) if(index!=n*m) res[index++] = matrix[i][k];
    }


    // 这种不用k表示当前层数，而是用上下左右四个变量的，更易读 最好用这种写法
    private static List<Integer> spiralOrder2(int[][] matrix) {
        LinkedList<Integer> result = new LinkedList<>();
        if(matrix==null||matrix.length==0) return result;
        int left = 0;
        int right = matrix[0].length - 1;
        int top = 0;
        int bottom = matrix.length - 1;
        int numEle = matrix.length * matrix[0].length;
        while (numEle >= 1) {
            for (int i = left; i <= right && numEle >= 1; i++) {
                result.add(matrix[top][i]);
                numEle--;
            }
            top++;
            for (int i = top; i <= bottom && numEle >= 1; i++) {
                result.add(matrix[i][right]);
                numEle--;
            }
            right--;
            for (int i = right; i >= left && numEle >= 1; i--) {
                result.add(matrix[bottom][i]);
                numEle--;
            }
            bottom--;
            for (int i = bottom; i >= top && numEle >= 1; i--) {
                result.add(matrix[i][left]);
                numEle--;
            }
            left++;
        }
        return result;
    }
}
