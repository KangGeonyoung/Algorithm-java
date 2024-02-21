package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_2720 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        int T = kb.nextInt();
        for (int i = 0; i < T; i++) {
            int cent = kb.nextInt();
            int quarter = cent / 25;
            cent = cent % 25;

            int dime = cent / 10;
            cent = cent % 10;

            int nickel = cent / 5;
            cent = cent % 5;

            int penny = cent / 1;

            System.out.println(quarter + " " + dime + " " + nickel + " " + penny);
        }
    }
}
