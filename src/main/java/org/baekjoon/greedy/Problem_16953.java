package org.baekjoon.greedy;

import java.util.Scanner;

public class Problem_16953 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int A = kb.nextInt();
        int B = kb.nextInt();
        int count = 0;

        while (A < B) {
            if (B % 10 == 1) {  // 숫자 끝에 1이 존재한다면
                B = B / 10;
                count += 1;
                continue;
            }
            if (B % 2 == 0) {  // 2로 나눌 수 있는 숫자라면
                B /= 2;
                count += 1;
            } else {
                break;
            }
        }
        if (A == B) {
            System.out.println(count + 1);
        } else {
            System.out.println(-1);
        }
    }
}
