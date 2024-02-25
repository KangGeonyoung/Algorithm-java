package org.baekjoon.bruteforce;

import java.util.Scanner;

public class Problem_1977 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int M = kb.nextInt();
        int N = kb.nextInt();
        int sum = 0;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i <= N; i++) {
            int calculate = i * i;
            if (M <= calculate && calculate <= N) {
                sum += calculate;
                if (min > calculate) {
                    min = calculate;
                }
            }
        }

        if (sum == 0) {
            System.out.println(-1);
        } else {
            System.out.println(sum);
            System.out.println(min);
        }
    }
}
