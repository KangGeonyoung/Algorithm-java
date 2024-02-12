package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_10988 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        String word = kb.next();
        int result = 1;
        int endPoint = word.length();

        for (int i = 0; i < endPoint / 2; i++) {
            if (word.charAt(i) != word.charAt(endPoint - i - 1)) {
                result = 0;
                break;
            }
        }
        System.out.println(result);
    }
}
