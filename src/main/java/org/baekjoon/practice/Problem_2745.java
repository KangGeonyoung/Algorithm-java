package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_2745 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        char[] N = kb.next().toCharArray();
        int B = kb.nextInt();
        long sum = 0;

        int[] newN = new int[N.length];

        for (int i = 0; i < N.length; i++) {
            if (65 <= N[i] && N[i] <= 90) {
                newN[i] = N[i] - 55;
            } else {
                newN[i] = Integer.parseInt(String.valueOf(N[i]));
            }
        }

        for (int i = 0; i < newN.length; i++) {
            sum += newN[newN.length - i - 1] * Math.pow(B, i);
        }

        System.out.println(sum);
    }
}
