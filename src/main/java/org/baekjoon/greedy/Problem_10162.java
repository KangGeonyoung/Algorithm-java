package org.baekjoon.greedy;

import java.util.Scanner;

public class Problem_10162 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int T = kb.nextInt();
        int A = 0;  // 300초
        int B = 0;  // 60초
        int C = 0;  // 10초

        A = T / 300;
        T %= 300;

        B = T / 60;
        T %= 60;

        C = T / 10;
        T %= 10;

        if (T != 0) {
            System.out.println(-1);
        } else {
            System.out.println(A + " " + B + " " + C);
        }
    }
}
