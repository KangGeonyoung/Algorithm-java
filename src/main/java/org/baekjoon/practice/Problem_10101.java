package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_10101 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int angle_1 = kb.nextInt();
        int angle_2 = kb.nextInt();
        int angle_3 = kb.nextInt();

        if (angle_1 + angle_2 + angle_3 != 180) {
            System.out.println("Error");
            return;
        }
        if (angle_1 == angle_2 && angle_2 == angle_3) {
            System.out.println("Equilateral");
            return;
        }
        if (angle_1 != angle_2 && angle_2 != angle_3 && angle_1 != angle_3) {
            System.out.println("Scalene");
            return;
        }
        if (angle_1 == angle_2 || angle_2 == angle_3 || angle_1 == angle_3) {
            System.out.println("Isosceles");
            return;
        }
    }
}
