package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_2566 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int[][] matrix = new int[9][9];
        int max = Integer.MIN_VALUE;
        int row = 0;
        int col = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                matrix[i][j] = kb.nextInt();
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                    row = i + 1;
                    col = j + 1;
                }
            }
        }

        System.out.println(max);
        System.out.println(row + " " + col);
    }
}
