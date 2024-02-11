package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_10810 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int N = kb.nextInt();
        int M = kb.nextInt();
        int[] basket = new int[N];

        for (int i = 0; i < M; i++) {
            int a = kb.nextInt() - 1;
            int b = kb.nextInt();
            int c = kb.nextInt();

            for (int j = a; j < b; j++) {
                basket[j] = c;
            }
        }

        for (int i = 0; i < N; i++) {
            System.out.print(basket[i] + " ");
        }
    }
}
