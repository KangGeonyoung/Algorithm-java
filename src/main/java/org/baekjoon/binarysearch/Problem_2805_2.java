package org.baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 이분 탐색 알고리즘 - 백준: 2805번 (2) (실버 2)
 * - 내가 실수한 점
 *   - 계산의 범위를 보지 못하고 int형으로 설정한 것.
 *   - int형은 2*10^9까지 가능하지만, 2*10^9 범위 숫자에 사칙연산을 추가할 수도 있기 때문에 long 타입을 사용하는 것이 안전하다.
 */
public class Problem_2805_2 {

    static int N, M;
    static long[] tree;
    static long answer = 0;
    static long max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        tree = new long[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            tree[i] = Integer.parseInt(st.nextToken());
            max = Math.max(max, tree[i]);
        }

        search(M);
        System.out.println(answer);
    }

    public static void search(long findNum) {
        long start = 1;
        long end = max;
        long mid;

        while (start <= end) {
            mid = (start + end) / 2;

            if (getRemainTree(mid) >= findNum) {
                answer = mid;
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
    }

    public static long getRemainTree(long high) {
        long sum = 0;
        for (int i = 0; i < N; i++) {
            long remain = tree[i] - high;
            if (remain > 0) {
                sum += remain;
            }
        }
        return sum;
    }
}
