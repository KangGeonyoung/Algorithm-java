package org.baekjoon.priorityqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 우선순위 큐 알고리즘 - 백준: 11286번 문제 (실버 1)
 * - 우선순위 큐에 여러 가지 복합된 우선순위 만들기 문제
 * - new Comparator<Integer>(){ compare 오버라이드 }를 통해 복합적인 우선순위 조건을 커스텀 해준다.
 * - compare 함수에서 return o1 - o2는 항상 작은 값이 앞으로 오게 해준다.
 */
public class Problem_11286 {
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (Math.abs(o1) > Math.abs(o2)) {
                    return Math.abs(o1) - Math.abs(o2);
                } else if (Math.abs(o1) == Math.abs(o2)) {
                    return o1 - o2;
                } else {
                    return -1;
                }
            }
        });

        for (int i = 0; i < N; i++) {
            int number = Integer.parseInt(br.readLine());
            if (number != 0) {
                queue.add(number);
            } else {
                if (queue.isEmpty()) {
                    System.out.println(0);
                } else {
                    System.out.println(queue.poll());
                }
            }
        }
    }
}
