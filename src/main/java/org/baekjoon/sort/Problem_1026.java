package org.baekjoon.sort;

import java.io.*;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 정렬 알고리즘 - 백준: 1026번 (실버 4)
 * - 정렬할 때 PriorityQueue를 사용하면 좋은 것 같다.
 * - PriorityQueue에 Collections.reverseOrder()를 추가하면 자동으로 내림차순으로 정렬되면서 값이 추가된다.
 * - 그리고 문제에서 무언가 조건을 걸 때, 조건이 걸린 변수를 잘 이용해보자.
 */
public class Problem_1026 {
    static int N;
    static PriorityQueue<Integer> A = new PriorityQueue<>();
    static PriorityQueue<Integer> B = new PriorityQueue<>(Collections.reverseOrder());
    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // input 입력 받기
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        // A를 추가하면서 오름차순 정렬
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A.add(Integer.parseInt(st.nextToken()));
        }

        // B를 추가하면서 내림차순 정렬
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            B.add(Integer.parseInt(st.nextToken()));
        }

        // 함수 S 계산
        for (int i = 0; i < N; i++) {
            result += A.poll() * B.poll();
        }

        // 결과 출력
        bw.write(Integer.toString(result));
        bw.close();
    }
}
