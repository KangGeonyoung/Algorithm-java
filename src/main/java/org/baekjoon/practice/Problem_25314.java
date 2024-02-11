package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_25314 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        String result = "";
        int N = kb.nextInt();
        int repeat = N / 4;

        for (int i = 0; i < repeat; i++) {
            result += "long ";
        }
        result += "int";

        System.out.println(result);
    }
}
