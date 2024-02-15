package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_10798 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        String[][] words = new String[5][15];

        for (int i = 0; i < 5; i++) {
            String word = kb.next();
            for (int j = 0; j < word.length(); j++) {
                words[i][j] = String.valueOf(word.charAt(j));
            }
        }

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 5; j++) {
                if (words[j][i] != null) {
                    System.out.print(words[j][i]);
                }
            }
        }
    }
}
