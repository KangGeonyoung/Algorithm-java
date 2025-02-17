package org.baekjoon.knapsack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Knapsack 알고리즘 - 백준: 9084번 문제 (골드 5)
 * - Knapsack 알고리즘에 점화식이 섞힌 문제이다.
 */
public class Problem_9084 {

    static int T;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = Integer.parseInt(br.readLine());
        for (int i = 0; i < T; i++) {
            // input 입력 받기
            int N = Integer.parseInt(br.readLine());
            int[] unit = new int[N];
            int M;

            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                unit[j] = Integer.parseInt(st.nextToken());
            }
            M = Integer.parseInt(br.readLine());

            int[] dp = new int[M + 1];
            dp[0] = 1;
            for (int j = 0; j < N; j++) {
                for (int k = unit[j]; k <= M; k++) {
                    dp[k] += dp[k - unit[j]];
                }
            }

            System.out.println(dp[M]);
        }
    }
}
