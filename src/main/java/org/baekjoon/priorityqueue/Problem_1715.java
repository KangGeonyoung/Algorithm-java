package org.baekjoon.priorityqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

/**
 * 우선순위 큐 알고리즘 - 백준: 1715번 문제 (골드 4)
 * - 우선순위가 사용되는 규칙을 찾아보자
 */
public class Problem_1715 {
    static int N;
    static PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            queue.add(Integer.parseInt(br.readLine()));
        }

        while (queue.size() != 1) {
            int first = queue.poll();
            int second = queue.poll();
            int sum = first + second;

            result += sum;
            queue.add(sum);
        }

        System.out.println(result);
    }
}
