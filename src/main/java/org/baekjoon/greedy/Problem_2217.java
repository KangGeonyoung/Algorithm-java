package org.baekjoon.greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Problem_2217 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int N = kb.nextInt();
        List<Integer> lopes = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            lopes.add(kb.nextInt());
        }
        Collections.sort(lopes);

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            int weight = lopes.get(i) * (N - i);
            max = Math.max(max, weight);
        }
        System.out.println(max);
    }
}
