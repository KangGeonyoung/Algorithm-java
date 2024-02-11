package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_10813 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int N = kb.nextInt();
        int M = kb.nextInt();
        int[] basket = new int[N];

        for (int i = 0; i < N; i++) {
            basket[i] = i + 1;
        }

        for (int i = 0; i < M; i++) {
            int a = kb.nextInt() - 1;
            int b = kb.nextInt() - 1;
            int temp = basket[a];
            basket[a] = basket[b];
            basket[b] = temp;
        }

        for (int i = 0; i < N; i++) {
            System.out.print(basket[i] + " ");
        }
    }
}
