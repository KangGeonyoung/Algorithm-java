package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_19532 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int a = kb.nextInt();
        int b = kb.nextInt();
        int c = kb.nextInt();
        int d = kb.nextInt();
        int e = kb.nextInt();
        int f = kb.nextInt();
        int x = 0;
        int y = 0;

        for (int i = -999; i < 1000; i++) {
            for (int j = -999; j < 1000; j++) {
                if (a * i + b * j == c && d * i + e * j == f) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        System.out.println(x + " " + y);
    }
}
