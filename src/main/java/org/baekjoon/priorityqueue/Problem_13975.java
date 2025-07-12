package org.baekjoon.priorityqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 우선순위 큐 알고리즘 - 백준: 13975번 문제 (골드 4)
 * - 항상 long 타입인지 int 타입인지 조심하자.
 */
public class Problem_13975 {

    static int T;
    static int K;
    static PriorityQueue<Long> files = new PriorityQueue<>();
    static long result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = Integer.parseInt(br.readLine());

        for (int i = 0; i < T; i++) {
            result = 0;
            files.clear();

            // input 입력 받기
            K = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < K; j++) {
                files.add(Long.parseLong(st.nextToken()));
            }

            // 파일 합치기
            while (!files.isEmpty()) {
                if (files.size() == 1) {
                    break;
                }
                long first = files.poll();
                long second = files.poll();
                long sum = first + second;

                result += sum;
                files.add(sum);
            }

            System.out.println(result);
        }

        /**
         * 40 30 30 50
         *
         * 30 30 40 50
         * 60 40 50 -> 60
         * 90 60 -> 90
         * -> 150
         */
    }
}
