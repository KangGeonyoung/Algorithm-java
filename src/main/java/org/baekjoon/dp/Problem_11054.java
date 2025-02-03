package org.baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * DP 알고리즘 - 백준: 11054번 문제 (골드 4)
 * - 부분 증가 수열과 부분 감소 수열을 구하는 문제이다.
 * - {부분 증가 수열 + 부분 감소 수열}의 Max = 최고 정점을 찍고 내려오는 수열 = 가장 긴 수열
 * - 더하는 과정에서 같은 원소가 한번 중복되기 때문에 -1을 해줘야 수열 길이가 나온다.
 * - https://st-lab.tistory.com/136
 */
public class Problem_11054 {

    static int N;
    static int[] seq;
    static int[] seq_asc;
    static int[] seq_desc;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        seq = new int[N];
        seq_asc = new int[N];
        seq_desc = new int[N];

        for (int i = 0; i < N; i++) {
            seq[i] = Integer.parseInt(st.nextToken());
        }

        ascending();
        descending();

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            max = Math.max(max, seq_asc[i] + seq_desc[i]);
        }

        System.out.println(max - 1);
    }

    public static void ascending() {
        for (int i = 0; i < N; i++) {
            seq_asc[i] = 1;

            for (int j = 0; j < i; j++) {
                if (seq[j] < seq[i] && seq_asc[i] < seq_asc[j] + 1) {
                    seq_asc[i] = seq_asc[j] + 1;
                }
            }
        }
    }

    public static void descending() {
        for (int i = N - 1; i >= 0; i--) {
            seq_desc[i] = 1;

            for (int j = N - 1; j > i; j--) {
                if (seq[j] < seq[i] && seq_desc[i] < seq_desc[j] + 1) {
                    seq_desc[i] = seq_desc[j] + 1;
                }
            }
        }
    }
}
