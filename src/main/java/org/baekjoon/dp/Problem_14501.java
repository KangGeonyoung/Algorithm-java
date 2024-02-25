package org.baekjoon.dp;

import java.util.Scanner;

public class Problem_14501 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int N = kb.nextInt();
        int[] time = new int[N];
        int[] pay = new int[N];
        int[] dp = new int[N + 1];

        for (int i = 0; i < N; i++) {
            time[i] = kb.nextInt();
            pay[i] = kb.nextInt();
        }

        for (int i = N - 1; i >= 0; i--) {
            int nextWorkDay = i + time[i];
            // 해당 날짜에 일을 할 수 있는지 검사
            if (nextWorkDay > N) {
                dp[i] = dp[i + 1];
            } else {
                // 해당 날짜에 일을 안할 경우: dp[i + 1], 일을 할 경우: pay[i] + dp[i + time[i]]
                dp[i] = Math.max(dp[i + 1], pay[i] + dp[i + time[i]]);
            }
        }
        System.out.println(dp[0]);
    }
}
