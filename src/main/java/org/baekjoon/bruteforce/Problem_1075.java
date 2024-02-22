package org.baekjoon.bruteforce;

import java.util.Scanner;

public class Problem_1075 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        long N = kb.nextLong();
        long F = kb.nextLong();

        // 뒷자리 2개 삭제 후, 0으로 채우기
        N = N / 100 * 100;

        for (int i = 0; i < 100; i++) {
            if (N % F == 0) {
                System.out.printf("%02d", i);
                break;
            }
            N += 1;
        }
    }
}
