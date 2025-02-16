package org.baekjoon.knapsack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Knapsack 알고리즘 - 백준: 12865번 문제(3) (골드 5)
 * - 이전과 다른 풀이를 통해 풀었다.
 * - maxCost와 같이 정답이 되는 값의 초기값을 잘 확인하자.
 * - 생각없이 Integer MAX_VALUE나 MIN_VALUE를 넣으면 틀릴 수도 있다. -> 답의 디폴트가 0일수도 있기 때문이다.
 */
public class Problem_12865_3 {

    static int N, K;
    static int[] W;
    static int[] V;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        W = new int[N + 1];
        V = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            W[i] = Integer.parseInt(st.nextToken());
            V[i] = Integer.parseInt(st.nextToken());
        }

        dp = new int[K + 1];
        int maxCost = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = K; j >= W[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - W[i]] + V[i]);
                maxCost = Math.max(maxCost, dp[j]);
            }
        }

        System.out.println(maxCost);
    }
}
