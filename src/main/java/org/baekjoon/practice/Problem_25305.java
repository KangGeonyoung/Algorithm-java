package org.baekjoon.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Problem_25305 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int N = kb.nextInt();
        int k = kb.nextInt();
        List<Integer> grade = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            grade.add(kb.nextInt());
        }
        Collections.sort(grade, Collections.reverseOrder());
        System.out.println(grade.get(k - 1));
    }
}
