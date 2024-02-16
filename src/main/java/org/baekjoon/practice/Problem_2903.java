package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_2903 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int point = 2;

        int N = kb.nextInt();
        for (int i = 0; i < N; i++) {
            point += Math.pow(2, i);
        }
        int totalPoint = point * point;

        System.out.println(totalPoint);
    }
}
