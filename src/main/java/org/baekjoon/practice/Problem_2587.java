package org.baekjoon.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Problem_2587 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        List<Integer> numbers = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            numbers.add(kb.nextInt());
            sum += numbers.get(i);
        }
        Collections.sort(numbers);
        int mean = sum / 5;
        int median = numbers.get(5/2);
        System.out.println(mean);
        System.out.println(median);
    }
}
