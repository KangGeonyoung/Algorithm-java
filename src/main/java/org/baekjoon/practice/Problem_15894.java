package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_15894 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        long N = kb.nextInt();

        long result = 3 * N + N;
        System.out.println(result);
    }
}
