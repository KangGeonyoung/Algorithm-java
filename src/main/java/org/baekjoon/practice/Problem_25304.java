package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_25304 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        long sum = 0;
        long X = kb.nextLong();
        int N = kb.nextInt();

        for (int i = 0; i < N; i++) {
            long a = kb.nextLong();
            long b = kb.nextInt();
            sum += a * b;
        }
        if(sum == X) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
