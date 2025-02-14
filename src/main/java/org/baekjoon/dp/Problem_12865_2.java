package org.baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * DP 알고리즘 - 백준: 12865번 문제(2) (골드 5)
 * - 여러 개의 요소를 이용하여 최대합 또는 최소합을 만드는 문제면, 2차원 배열을 사용하자.
 * - 요소를 중복으로 사용할 수 있다면 dp[i - weight[j]][j] 이렇게 사용할 수 있지만, 중복 사용이 허용되지 않는다면 dp[i - weight[j]][j - 1]를 사용해야 한다.
 * - dp는 참 어렵다..
 */
public class Problem_12865_2 {

    static int N, K;
    static int[] weight;
    static int[] value;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dp = new int[K + 1][N + 1];
        weight = new int[N + 1];
        value = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            weight[i] = Integer.parseInt(st.nextToken());
            value[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= K; i++) {
            for (int j = 1; j <= N; j++) {
                if (i - weight[j] >= 0) {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - weight[j]][j - 1] + value[j]);
                } else {
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }

        System.out.println(dp[K][N]);
    }
}
