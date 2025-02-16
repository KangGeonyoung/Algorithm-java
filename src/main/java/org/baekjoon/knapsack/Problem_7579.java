package org.baekjoon.knapsack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Knapsack 알고리즘 - 백준: 7579번 문제 (골드 3)
 * - dp를 이용하고, 특정 무언가의 크기 최댓값이 유추해볼 수 있을 때 사용하는 알고리즘
 * - 해당 문제에서는 비용이 최대 10000이라는 것을 알 수 있기에 dp = new int[10001]을 선언해서 사용한다.
 * - knapsack은 뒤에서부터 값을 채워넣기 시작한다.
 * - 어려운 알고리즘이기 때문에 자주 풀어봐야할 것 같다.
 * https://yudaeng-log.tistory.com/52
 */
public class Problem_7579 {

    static int N, M;
    static int[] memory;
    static int[] cost;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        memory = new int[N + 1];
        cost = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            memory[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            cost[i] = Integer.parseInt(st.nextToken());
        }

        dp = new int[10001];
        int minCost = Integer.MAX_VALUE;

        for (int i = 1; i <= N; i++) {
            for (int j = 10000; j >= cost[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - cost[i]] + memory[i]);
                if (dp[j] >= M) {
                    minCost = Math.min(minCost, j);
                }
            }
        }

        System.out.println(minCost);
    }
}
