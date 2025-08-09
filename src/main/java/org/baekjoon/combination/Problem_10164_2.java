package org.baekjoon.combination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Problem_10164_2 {

    static int N, M, K;
    static int[][] map;
    static int cnt = 0;
    static int[] direction_row = {0, 1};  // 우-하
    static int[] direction_col = {1, 0};

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

        // 조합 실행
        boolean isFoundK = (map[0][0] == K) || (K == 0);
        combination(0, 0, isFoundK);

        // 결과 출력
        System.out.println(cnt);
    }

    public static void combination(int row, int col, boolean isFoundK) {
        // 종료 조건 및 옳은 결과인지 검증
        if (row == N - 1 && col == M - 1) {
            if (isFoundK) {
                cnt += 1;
            }
            return;
        }

        // 오른쪽, 아래 방향으로 보내기
        for (int i = 0; i < 2; i++) {
            int next_row = row + direction_row[i];
            int next_col = col + direction_col[i];

            if (isValid(next_row, next_col)) {
                boolean newFoundK = isFoundK || (map[next_row][next_col] == K);
                combination(next_row, next_col, newFoundK);
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
