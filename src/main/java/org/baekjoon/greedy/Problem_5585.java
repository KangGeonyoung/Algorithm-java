package org.baekjoon.greedy;

import java.util.Scanner;

public class Problem_5585 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int payment = kb.nextInt();
        int change = 1000 - payment;
        int unit = 0;

        unit += change / 500;
        change %= 500;

        unit += change / 100;
        change %= 100;

        unit += change / 50;
        change %= 50;

        unit += change / 10;
        change %= 10;

        unit += change / 5;
        change %= 5;

        unit += change / 1;

        System.out.println(unit);
    }
}
