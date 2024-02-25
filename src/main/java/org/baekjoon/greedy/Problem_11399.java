package org.baekjoon.greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Problem_11399 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int N = kb.nextInt();
        List<Integer> P = new ArrayList<>();
        int time = 0;

        for (int i = 0; i < N; i++) {
            P.add(kb.nextInt());
        }

        Collections.sort(P);
        for (int i = 0; i < N; i++) {
            int sum = 0;
            for (int j = 0; j <= i; j++) {
                sum += P.get(j);
            }
            time += sum;
        }

        System.out.println(time);
    }
}
