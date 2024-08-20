package org.baekjoon.dp;

import java.io.*;
import java.util.StringTokenizer;

/**
 * DP 알고리즘 - 백준: 12865번 문제
 * - 목표로 지정된 값(이 문제에서는 K)을 dp 2차원 배열의 row로 지정해보자.
 * - 문제에서 주어진 값을 dp 2차원 배열의 col로 지정해보자.
 * - DP 문제에서는 이전 값을 중요시 한다.
 * - 주로 Math.max(이전 값, 현재 위치에서 계산된 새로운 값) 이런 형식으로 dp 값이 생성된다.
 * - 결과는 dp 배열의 마지막 값이다.
 */
public class Problem_12865 {

    static int N, K;
    static int[] W;
    static int[] V;

    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // N, K 입력 받기
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());  // K 무게만큼 W를 담을 수 있음

        // W, V 입력 받기
        W = new int[N + 1];
        V = new int[N + 1];
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine());
            W[i] = Integer.parseInt(st.nextToken());
            V[i] = Integer.parseInt(st.nextToken());
        }

        // dp 초기 설정
        dp = new int[K + 1][N + 1];
        for (int i = 0; i < K + 1; i++) {
            dp[i][0] = 0;
        }
        for (int j = 0; j < N + 1; j++) {
            dp[0][j] = 0;
        }

        // dp 값 채우기
        for (int i = 1; i < K + 1; i++) {  // 배낭의 무게를 1씩 늘리면서 탐색
            for (int j = 1; j < N + 1; j++) {  // 아이템 개수만큼
                // 배낭에 들어갈 수 있는 경우,
                // V[j] : 이전 아이템을 포기하고 현재 아이템을 넣어서 얻는 가치
                // dp[i - W[j]][j - 1] : 현재 아이템을 넣고 남은 무게(i - W[j])의 dp에서 현재 아이템 위치의 이전 위치(j - 1)를 살피기 (너무 어려움..)
                // -> 위 2개를 더한 가치값과 이전 가치값을 비교해서 더 높은 값을 현재 dp에 대입
                if (W[j] <= i) {
                    dp[i][j] = Math.max(dp[i][j - 1], (V[j] + dp[i - W[j]][j - 1]));
                } else {  // 배낭에 들어갈 수 없는 경우, 이전 dp 값으로 채워주기
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }

        // 결과 출력
        int result = dp[K][N];
        bw.write(Integer.toString(result));
        bw.close();
    }
}
