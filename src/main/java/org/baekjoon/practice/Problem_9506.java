package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_9506 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        int n = kb.nextInt();
        while (n != -1) {
            int sum = 0;
            int[] numbers = new int[n];
            int count = 0;

            // 약수 찾기
            for (int i = 0; i < n; i++) {
                if (n % (i + 1) == 0 && n != (i + 1)) {
                    numbers[count] = (i + 1);
                    sum += numbers[count];
                    count += 1;
                }
            }

            // 완전수 판별
            if (sum == n) {
                System.out.print(n + " = ");
                for (int i = 0; i < count; i++) {
                    System.out.print(numbers[i]);
                    if ((i+1) < count) {
                        System.out.print(" + ");
                    }
                }
                System.out.println();
            } else {
                System.out.println(n + " is NOT perfect.");
            }

            n = kb.nextInt();
        }
    }
}
