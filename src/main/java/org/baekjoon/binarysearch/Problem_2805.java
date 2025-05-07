package org.baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 이분 탐색 알고리즘 - 백준: 2805번 (실버 2)
 * - 이분탐색을 할 때, start는 mid + 1, end는 mid - 1로 설정하는 게 보통 풀이 방식이다.
 * - 내가 실수한 점
 *   - cutting 값이 정확하게 findNum이 될거라고 단정지은 것
 *   - 문제에서 최소 findNum을 갖는 높이 설정 값이라고 했기 때문에 cutting >= findNum 으로 설정했어야 했다.
 */
public class Problem_2805 {

    static int N, M;
    static int[] tree;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        tree = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            tree[i] = Integer.parseInt(st.nextToken());
            max = Math.max(max, tree[i]);
        }

        long result = search(M);
        System.out.println(result);
    }

    public static long search(long findNum) {
        long start = 0;
        long end = max;
        long mid = 0;
        long result = 0;

        while (start <= end) {
            mid = (start + end) / 2;
            long cutting = getCutting(mid);

            if (cutting >= findNum) {
                result = mid;
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return result;
    }

    public static long getCutting(long high) {
        long sum = 0;
        for (int i = 0; i < tree.length; i++) {
            long cutting = tree[i] - high;
            if (cutting > 0) {
                sum += cutting;
            }
        }
        return sum;
    }
}
