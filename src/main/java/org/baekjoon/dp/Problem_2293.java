package org.baekjoon.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * DP 알고리즘 - 백준: 2293번 문제 (골드 4)
 * - dp 문제에서 주어진 요소들을 중복으로 사용할 수 있다면 dp[k-i][j]를 사용,
 *   중복이 허용되지 않는다면 dp[k-i][j-1]을 사용하면 된다.
 * - 보통 어려운 dp문제는 2차원 배열을 그려서 설계를 먼저 해보고 구현하면 편하다.
 */
public class Problem_2293 {
    static int n, k;
    static int[] money;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        // 동전의 가치 입력 받기
        money = new int[n+1];
        for (int i = 1; i < n + 1; i++) {
            st = new StringTokenizer(br.readLine());
            money[i] = Integer.parseInt(st.nextToken());
        }

        // dp 초기값 설정
        dp = new int[k + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            dp[0][i] = 1;
        }
        for (int i = 0; i < k + 1; i++) {
            dp[i][0] = 0;
        }

        // dp 계산
        for (int i = 1; i < k + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (i - money[j] >= 0) {
                    dp[i][j] = dp[i][j - 1] + dp[i - money[j]][j];
                } else {
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }

        // 결과 출력
        System.out.println(dp[k][n]);
    }
}
