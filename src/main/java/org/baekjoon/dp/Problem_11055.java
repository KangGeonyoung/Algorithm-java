package org.baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * DP 알고리즘 - 백준: 11055번 문제 (실버 2)
 * - 부분 증가 수열의 최대 합을 구하는 문제이다.
 * - 이전 부분 증가 수열 구간의 수열 구간을 구하는 문제는 seq_asc[j] + 1을 사용한 반면, 이 문제에서는 seq_asc[j] + seq[i]를 사용한다.
 * - += 부호를 조심히 사용하자.
 */
public class Problem_11055 {

    static int N;
    static int[] seq;
    static int[] seq_asc;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        seq = new int[N];
        seq_asc = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            seq[i] = Integer.parseInt(st.nextToken());
        }

        ascending();

        int max = Integer.MIN_VALUE + 1;
        for (int i = 0; i < N; i++) {
            max = Math.max(max, seq_asc[i]);
        }

        System.out.println(max);
    }

    public static void ascending() {
        for (int i = 0; i < N; i++) {
            seq_asc[i] = seq[i];

            for (int j = 0; j < i; j++) {
                if (seq[j] < seq[i] && seq_asc[i] < seq_asc[j] + seq[i]) {
                    seq_asc[i] = seq_asc[j] + seq[i];
                }
            }
        }
    }
}
