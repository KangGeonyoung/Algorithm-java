package org.baekjoon.prefixsum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 누적 합 알고리즘 - 백준: 1806번 (골드 4)
 * - start, end와 같은 투 포인터를 사용하여 합을 구하면 된다.
 * - https://moonsbeen.tistory.com/223
 */
public class Problem_1806 {

    static int N, S;
    static int[] num;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        num = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        int start = 0;
        int end = 0;
        int sum = 0;
        int min = Integer.MAX_VALUE - 1;

        while (start <= N && end <= N) {
            if (sum >= S && min > end - start) {
                min = end - start;
            }
            if (sum < S) {
                sum += num[end];
                end += 1;
            } else {
                sum -= num[start];
                start += 1;
            }
        }

        if (min == Integer.MAX_VALUE - 1) {
            System.out.println(0);
        } else {
            System.out.println(min);
        }
    }
}
