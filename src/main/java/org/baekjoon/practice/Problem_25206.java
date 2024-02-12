package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_25206 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        double totalGrade = 0;
        double totalScore = 0;
        String[] gradeList = {"A+", "A0", "B+", "B0", "C+", "C0", "D+", "D0", "F", "P"};
        double[] gradeScore = {4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.0, 0.0};

        for (int i = 0; i < 20; i++) {
            String name = kb.next();
            double score = kb.nextDouble();
            String grade = kb.next();

            if (!grade.equals("P")) {
                totalScore += score;
            }

            for (int j = 0; j < 10; j++) {
                if (grade.equals(gradeList[j])) {
                    totalGrade += score * gradeScore[j];
                }
            }
        }

        double result = totalGrade / totalScore;
        System.out.println(String.format("%.6f", result));
    }
}
