package org.baekjoon.priorityqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Problem_1715_2 {

    static int N;
    static PriorityQueue<Integer> queue = new PriorityQueue<>();
    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            int number = Integer.parseInt(br.readLine());
            queue.add(number);
        }

        while (queue.size() >= 2) {
            Integer num1 = queue.poll();
            Integer num2 = queue.poll();

            int num3 = num1 + num2;
            result += num3;
            queue.add(num3);
        }

        System.out.println(result);
    }
}
