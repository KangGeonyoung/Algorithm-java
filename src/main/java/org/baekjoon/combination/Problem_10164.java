package org.baekjoon.combination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 조합 알고리즘 - 백준: 10164 (실버 2)
 * - 서브 테스크 문제이다. (점수가 매겨지는 문제)
 * - 내가 부족했던 점
 *   - 모든 경로를 List에 모아서 마지막에 특정 숫자가 포함되는 경로들을 카운팅 했다.
 *   - 처음에는 56점이 나왔는데, 문제는 시간초과였던 것 같다.
 *   - 가능한 경로를 모두 모아서 판단하는 방식이 아닌, 실시간으로 판단해서 카운팅 해주는 것이 시간 절약이 된다.
 *   - List에 add하는 연산 자체가 시간에 부담이 되기 때문에 조심해야 한다.
 *   - dfs의 매개변수를 통해 이전의 상태값을 가져가며 활용하는 방법을 더 익혀야 할 것 같다.
 */
public class Problem_10164 {

    static int N, M, K;
    static int[][] map;

    static int[] direction_row = {0, 1};  // 우-하
    static int[] direction_col = {1, 0};
    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        int number = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = number;
                number += 1;
            }
        }

        dfs(0, 0, map[0][0] == K);
        System.out.println(result);
    }

    public static void dfs(int row, int col, boolean foundK) {
        // 종료 조건
        if (row == N - 1 && col == M - 1) {
            if (K == 0 || foundK) {
                result += 1;
            }
            return;
        }

        // 다음으로 갈 지점 계산
        for (int i = 0; i < 2; i++) {
            int next_row = row + direction_row[i];
            int next_col = col + direction_col[i];

            if (isValid(next_row, next_col)) {
                boolean nextFoundK = foundK || (map[next_row][next_col] == K);
                dfs(next_row, next_col, nextFoundK);
            }
        }
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < M) {
            return true;
        }
        return false;
    }
}
