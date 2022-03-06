package leetcode;

import java.util.Arrays;

// 旋转图像 https://leetcode-cn.com/problems/rotate-image/
// 自外向内翻转的思路想到了
// 还有一种数学解法，有点像手摇算法： 先水平轴翻转矩阵，再对角线翻转矩阵
public class rotate {
    public void rotate(int[][] matrix) {
        for(int i=0;i<(matrix.length+1)/2;i++) {
            dfsOuterLayer(matrix,i);
        }
    }

    public void dfsOuterLayer (int[][] matrix, int n) {
        for(int i=0;i<matrix.length-2*n-1;i++) {
            rotateCircle(matrix,n,i+n);
        }
    }

    public void rotateCircle (int[][] matrix, int x, int y) {
        int n = matrix.length;
        int temp = matrix[x][y];
        matrix[x][y] = matrix[n-y-1][x];
        matrix[n-y-1][x] = matrix[n-x-1][n-y-1];
        matrix[n-x-1][n-y-1] = matrix[y][n-x-1];
        matrix[y][n-x-1] = temp;
    }


    // 数学解法：因为顺时针90度后  matrix[col][n-row-1] = matrix[row][col]
    // 所以我们先水平翻转：matrix[row][col] = matrix[n−row−1][col]
    // 再对角线翻转： matrix[col][n-row-1] = matrix[n−row−1][col] 所以一起就是 matrix[col][n-row-1] = matrix[row][col]
    class Solution {
        public void rotate(int[][] matrix) {
            int n = matrix.length;
            // 水平翻转
            for (int i = 0; i < n / 2; ++i) {
                for (int j = 0; j < n; ++j) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[n - i - 1][j];
                    matrix[n - i - 1][j] = temp;
                }
            }
            // 主对角线翻转
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < i; ++j) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
        }
    }
}
