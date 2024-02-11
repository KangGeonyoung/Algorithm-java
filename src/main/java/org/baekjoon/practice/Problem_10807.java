package org.baekjoon.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Problem_10807 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int count = 0;
        List<Integer> numbers = new ArrayList<>();

        int N = kb.nextInt();
        for (int i = 0; i < N; i++) {
            numbers.add(kb.nextInt());
        }
        int V = kb.nextInt();

        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) == V) {
                count += 1;
            }
        }
        System.out.println(count);
    }
}
