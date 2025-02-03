package org.baekjoon.dp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * DP 알고리즘 - 백준: 2294번 문제 (골드 5)
 * - 가치 합이 k원이 되도록 만드는 문제면, k개만큼의 dp 배열이 필요하고, 해당 배열을 처음부터 k까지 모두 채워줘야 한다.
 * - 처음 dp를 초기화할 때는 Integer.MAX_VALUE - 1로 하는 게 좋다. -> -1을 하는 이유는 오버플로우 방지를 위해서이다.
 * - 점화식을 찾는 게 가장 중요한데 이건 문제를 많이 풀어야 감이 오는 것 같다.
 */
public class Problem_2294 {
    static int n, k;
    static int[] money;
    static int[] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        money = new int[k + 1];
        dp = new int[k + 1];

        for (int i = 1; i <= n; i++) {
            money[i] = Integer.parseInt(br.readLine());
        }

        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = money[i]; j <= k; j++) {
                dp[j] = Math.min(dp[j], dp[j - money[i]] + 1);
            }
        }

        if (dp[k] == Integer.MAX_VALUE - 1) {
            System.out.println(-1);
        } else {
            System.out.println(dp[k]);
        }
    }
}
