package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_5073 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int[] line = new int[3];

        while (true) {
            int max = 0;
            int maxIndex = 0;
            int sum = 0;
            line[0] = kb.nextInt();
            line[1] = kb.nextInt();
            line[2] = kb.nextInt();

            // EOF 검사
            if (line[0] == 0 && line[1] == 0 && line[2] == 0) {
                break;
            }

            // max 찾기
            for (int i = 0; i < 3; i++) {
                max = Math.max(max, line[i]);
                maxIndex = i;
            }

            // invalid 검사
            for (int i = 0; i < 3; i++) {
                if (i == maxIndex) {
                    continue;
                }
                sum += line[i];
            }
            if (max >= sum) {
                System.out.println("Invalid");
                continue;
            }

            // 세 변의 길이가 모두 같은 경우
            if (line[0] == line[1] && line[1] == line[2] && line[0] == line[2]) {
                System.out.println("Equilateral");
                continue;
            }

            // 두 변의 길이만 같은 경우
            if (line[0] == line[1] || line[1] == line[2] || line[0] == line[2]) {
                System.out.println("Isosceles");
                continue;
            }

            // 세 변의 길이가 모두 다른 경우
            if (line[0] != line[1] && line[1] != line[2] && line[0] != line[2]) {
                System.out.println("Scalene");
                continue;
            }
        }
    }
}
