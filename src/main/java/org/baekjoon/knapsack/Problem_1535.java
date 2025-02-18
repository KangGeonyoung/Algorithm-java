package org.baekjoon.knapsack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Knapsack 알고리즘 - 백준: 1535번 문제 (실버 2)
 * - 문제에서 구하려는 값을 dp[i] = k 중에 k로 두고, i는 변화하는 수치로 두자.
 */
public class Problem_1535 {

    static int N;
    static int[] hp;
    static int[] joy;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        hp = new int[N];
        joy = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            hp[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            joy[i] = Integer.parseInt(st.nextToken());
        }

        dp = new int[101];
        int maxCost = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 99; j >= hp[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - hp[i]] + joy[i]);
                maxCost = Math.max(maxCost, dp[j]);
            }
        }

        System.out.println(maxCost);
    }
}
