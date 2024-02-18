package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_14215 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int max = 0;
        int maxIndex = 0;
        int[] line = new int[3];

        line[0] = kb.nextInt();
        line[1] = kb.nextInt();
        line[2] = kb.nextInt();

        // max 찾기
        for (int i = 0; i < 3; i++) {
            int previousMax = max;
            max = Math.max(max, line[i]);

            if (previousMax != max) {
                maxIndex = i;
            }
        }

        // 세변의 길이에 대한 valid 검사
        int sum = 0;
        boolean isValid = false;
        for (int i = 0; i < 3; i++) {
            if (i == maxIndex) {
                continue;
            }
            sum += line[i];
        }
        if (max >= sum) {
            isValid = false;
        } else {
            isValid = true;
        }

        int result;
        if (isValid) {
            result = line[0] + line[1] + line[2];
        } else {
            result = sum * 2 - 1;
        }
        System.out.println(result);
    }
}
