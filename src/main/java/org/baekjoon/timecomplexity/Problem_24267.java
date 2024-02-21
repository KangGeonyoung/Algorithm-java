package org.baekjoon.timecomplexity;

import java.util.Scanner;

public class Problem_24267 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        long n = kb.nextLong();

        // 조합 사용
        System.out.println(n * (n - 1) * (n - 2) / 6);
        System.out.println(3);
    }
}
