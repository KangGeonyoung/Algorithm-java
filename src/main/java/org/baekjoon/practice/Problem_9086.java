package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_9086 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int T = kb.nextInt();

        for (int i = 0; i < T; i++) {
            String str = kb.next();
            char first = str.charAt(0);
            char last = str.charAt(str.length() - 1);
            System.out.print(first);
            System.out.println(last);
        }
    }
}
