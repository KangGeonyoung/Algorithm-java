package org.baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 이분 탐색 알고리즘 - 백준: 1654번 (실버 2)
 * - 이분탐색을 할 때, 문제의 성격에 따라 start의 초기 설정 값이 달라진다.
 * - 배열 같은 경우에는 start를 0으로 잡지만, 자르기 문제에서는 보통 start를 1로 잡는다.
 */
public class Problem_1654 {

    static int K, N;
    static int[] line;
    static long max = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        line = new int[K];
        for (int i = 0; i < K; i++) {
            line[i] = Integer.parseInt(br.readLine());
            max = Math.max(max, line[i]);
        }

        long result = search(N);
        System.out.println(result);
    }

    public static long search(int findNum) {
        long start = 1;
        long end = max;
        long mid;
        long result = 0;

        while (start <= end) {
            mid = (start + end) / 2;
            long lineCnt = getLineCnt(mid);

            if (lineCnt >= findNum) {
                result = mid;
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return result;
    }

    public static long getLineCnt(long unit) {
        long sum = 0;
        for (int i = 0; i < K; i++) {
            sum += line[i] / unit;
        }
        return sum;
    }
}
