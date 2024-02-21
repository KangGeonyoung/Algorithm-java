package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_2501 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        int N = kb.nextInt();
        int K = kb.nextInt();
        int[] result = new int[N];
        int output = 0;

        for (int i = 0; i < N; i++) {
            if (N % (i + 1) == 0) {
                result[output] = (i + 1);
                output += 1;
            }
        }

        System.out.println(result[K - 1]);
    }
}
