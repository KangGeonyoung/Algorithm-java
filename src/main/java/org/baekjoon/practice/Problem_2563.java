package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_2563 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int[][] matrix = new int[100][100];

        int N = kb.nextInt();
        for (int i = 0; i < N; i++) {
            int X = kb.nextInt();
            int Y = kb.nextInt();
            for (int j = X; j < X + 10; j++) {
                for (int k = Y; k < Y + 10; k++) {
                    matrix[j][k] = 1;
                }
            }
        }

        int area = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (matrix[i][j] == 1) {
                    area += 1;
                }
            }
        }
        System.out.println(area);
    }
}
