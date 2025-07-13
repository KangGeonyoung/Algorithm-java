package org.baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 이분 탐색 알고리즘 - 백준: 6236번 (실버 1)
 * - 내가 실수한 점
 *   - 오늘 사용할 돈만큼 충분히 가지고 있다면 통과시켰어야 했음
 *   - 돈이 모자르다면, 인출해서 오늘 쓸 돈을 차감시켰어야 했는데 누락해 버림
 */
public class Problem_6236 {

    static int N, M;
    static List<Long> plans = new ArrayList<>();
    static long maxMoney = 0;
    static long totalMoney = 0;
    static long result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            long oneDay = Long.parseLong(br.readLine());
            maxMoney = Math.max(oneDay, maxMoney);
            totalMoney += oneDay;
            plans.add(oneDay);
        }

        long start = maxMoney;
        long end = totalMoney;
        while (start <= end) {
            long mid = (start + end) / 2;

            if (isPossible(mid)) {
                result = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        System.out.println(result);
    }

    public static boolean isPossible(long amount) {
        long cnt = 1;
        long possible = amount;

        for (int i = 0; i < N; i++) {
            long today = plans.get(i);
            if (possible - today >= 0) {
                possible -= today;
            } else {
                cnt += 1;
                possible = amount;
                possible -= today;
            }
        }

        return cnt <= M;
    }
}
