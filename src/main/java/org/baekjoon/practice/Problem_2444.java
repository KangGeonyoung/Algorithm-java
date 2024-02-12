package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_2444 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int N = kb.nextInt();
        int repeat = 2 * N - 1;

        for (int i = 1; i <= repeat; i = i + 2) {
            String result = "";
            String letter = "";
            for (int j = 0; j < i; j++) {
                letter += "*";
            }

            int space = (repeat - i) / 2;
            for (int j = 0; j < space; j++) {
                result += " ";
            }
            result += letter;

            System.out.println(result);
        }

        for (int i = repeat - 2; i > 0; i = i - 2) {
            String result = "";
            String letter = "";
            for (int j = 0; j < i; j++) {
                letter += "*";
            }

            int space = (repeat - i) / 2;
            for (int j = 0; j < space; j++) {
                result += " ";
            }
            result += letter;

            System.out.println(result);
        }
    }
}
