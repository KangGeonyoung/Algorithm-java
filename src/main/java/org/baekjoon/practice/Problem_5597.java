package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_5597 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int[] numbers = new int[30];

        for (int i = 0; i < 28; i++) {
            numbers[kb.nextInt() - 1] = 1;
        }

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != 1) {
                System.out.println(i + 1);
            }
        }
    }
}
