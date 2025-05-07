package org.baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 이분 탐색 알고리즘 - 백준: 2512번 (실버 2)
 * - 최대 조건을 충족하는 값을 찾는 문제라면 이분 탐색으로 풀어볼만 하다.
 */
public class Problem_2512 {

    static int N, M;
    static int[] askMoney;
    static int max = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        askMoney = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            askMoney[i] = Integer.parseInt(st.nextToken());
            max = Math.max(max, askMoney[i]);
        }
        M = Integer.parseInt(br.readLine());

        int result = search(M);
        System.out.println(result);
    }

    public static int search(int findNum) {
        int start = 1;
        int end = max;
        int mid;
        int result = 0;

        while (start <= end) {
            mid = (start + end) / 2;
            int totalMoney = calculateMoney(mid);

            if (totalMoney <= findNum) {
                result = mid;
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return result;
    }

    public static int calculateMoney(int lastMoney) {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            if (askMoney[i] >= lastMoney) {
                sum += lastMoney;
            } else {
                sum += askMoney[i];
            }
        }
        return sum;
    }
}
