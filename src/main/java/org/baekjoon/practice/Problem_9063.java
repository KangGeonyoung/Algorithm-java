package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_9063 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int x_max = Integer.MIN_VALUE;
        int y_max = Integer.MIN_VALUE;
        int x_min = Integer.MAX_VALUE;
        int y_min = Integer.MAX_VALUE;

        int N = kb.nextInt();
        for (int i = 0; i < N; i++) {
            int x = kb.nextInt();
            int y = kb.nextInt();

            x_max = Math.max(x_max, x);
            x_min = Math.min(x_min, x);

            y_max = Math.max(y_max, y);
            y_min = Math.min(y_min, y);
        }
        System.out.println((x_max - x_min) * (y_max - y_min));
    }
}
