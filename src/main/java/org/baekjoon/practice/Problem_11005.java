package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_11005 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        long N = kb.nextLong();
        long B = kb.nextLong();
        String result = "";

        while (N != 0) {
            long r = N % B;
            N /= B;
            if (r > 9) {
                result = (char) (r + 55) + result;
            } else {
                result = r + result;
            }
        }
        System.out.println(result);
    }
}
