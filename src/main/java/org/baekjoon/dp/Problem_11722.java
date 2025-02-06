package org.baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * DP 알고리즘 - 백준: 11722번 문제 (실버 2)
 * - 부분 감소 수열의 긴 부분 길이를 찾는 문제이다.
 */
public class Problem_11722 {

    static int N;
    static int[] seq;
    static int[] seq_desc;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        seq = new int[N];
        seq_desc = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            seq[i] = Integer.parseInt(st.nextToken());
        }

        descending();

        int max = 1;
        for (int i = 0; i < N; i++) {
            max = Math.max(max, seq_desc[i]);
        }

        System.out.println(max);
    }

    public static void descending() {
        for (int i = N - 1; i >= 0 ; i--) {
            seq_desc[i] = 1;

            for (int j = N - 1; j > i; j--) {
                if (seq[j] < seq[i] && seq_desc[i] < seq_desc[j] + 1) {
                    seq_desc[i] = seq_desc[j] + 1;
                }
            }
        }
    }
}
