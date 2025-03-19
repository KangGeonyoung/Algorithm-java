package org.baekjoon.priorityqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

/**
 * 우선순위 큐 알고리즘 - 백준: 1927번 문제 (실버 2)
 * - 우선순위 큐 선언 방법을 잘 기억해 두기
 */
public class Problem_1927 {
    static int N;
    static PriorityQueue<Integer> queue = new PriorityQueue<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            int number = Integer.parseInt(br.readLine());
            if (number > 0) {
                queue.add(number);
            } else {
                if (queue.size() > 0) {
                    System.out.println(queue.poll());
                } else {
                    System.out.println(0);
                }
            }
        }
    }
}
