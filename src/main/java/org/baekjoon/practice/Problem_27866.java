package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_27866 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        String word = kb.next();
        int i = kb.nextInt() - 1;

        char letter = word.charAt(i);
        System.out.println(letter);
    }
}
