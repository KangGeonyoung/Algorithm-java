package org.baekjoon.dfs;

import java.io.*;
import java.util.StringTokenizer;

/**
 * DFS 알고리즘 - 백준: 9095번 (실버 3)
 * - DFS 문제를 풀 때는 항상 점화식을 유추해 보자.
 * - 너무 복잡하게 생각하지 말고 연관성을 찾아보자.
 */
public class Problem_9095 {

    static int T;
    static int n;
    static int[] dp;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // 테스트 케이스 T 입력 받기
        st = new StringTokenizer(br.readLine());
        T = Integer.parseInt(st.nextToken());

        // dp 값 세팅
        dp = new int[11];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;

        // T 만큼 반복
        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());

            for (int j = 4; j <= n; j++) {
                dp[j] = dp[j-1] + dp[j-2] + dp[j-3];
            }
            bw.write(Integer.toString(dp[n]));
            bw.newLine();
        }

        bw.close();
    }
}
