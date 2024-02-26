package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int A = kb.nextInt();
        int B = kb.nextInt();
        int goalA = A;
        int count;

        count = 0;
        // B의 뒷자리가 1이 될때까지 2로 나누기
        // 1이 나왔다면 그다음부터는 A에 2를 곱하기
        while (B % 10 != 1) {
            B /= 2;
            count += 1;
            System.out.println("B = " + B);
        }

        String wanted_A;
        if (B % 10 == 1) {  // B 관련 연산이 최소 1번 이상 발생했다면, 제일 뒤에 있는 1을 제외하고 잘라준다.
            String tempB = Integer.toString(B);
            wanted_A = tempB.substring(0, tempB.length() - 1);
            goalA = Integer.parseInt(wanted_A);
            System.out.println("goalA = " + goalA);
            count += 1;
        }

        while (A < goalA) {
            A *= 2;
            count += 1;
            System.out.println("current A = " + A);
        }

        if (A == goalA) {
            System.out.println(count + 1);
        } else {
            System.out.println(-1);
        }
    }
}