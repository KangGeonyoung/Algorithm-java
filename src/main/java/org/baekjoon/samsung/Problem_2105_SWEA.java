package org.baekjoon.samsung;

import java.io.*;
import java.util.*;

/**
 * DFS 알고리즘 - SWEA 2105번
 * - 이전 방향을 기억하고 싶다면 dfs 매개변수에 담아가면 된다.
 * - 무언가 문제에서 한정적으로 종류의 개수를 정해줬다면 그 개수만큼 배열을 만들어서 사용하자.
 * - 조건문과 continue를 활용해서 다음으로 갈 경로를 찾자.
 */
public class Problem_2105_SWEA {

    static int T;
    static int N;
    static int[][] map;
    static boolean[] isAte;
    static int[] direction_row = {1, 1, -1, -1};  // 우하 -> 좌하 -> 좌상 -> 우상
    static int[] direction_col = {1, -1, -1, 1};
    static int start_row;
    static int start_col;
    static int max = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        T = Integer.parseInt(st.nextToken());

        // 테스트 케이스만큼 반복
        for (int test_case = 1; test_case <= T; test_case++) {
            // N 입력 받기
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());

            // 초기화
            max = -1;

            // 카페 정보 입력 받기
            map = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            // 시작 지점별로 탐색 시작
            for (int i = 0; i < N - 2; i++) {
                for (int j = 1; j < N - 1; j++) {
                    isAte = new boolean[101];
                    isAte[map[i][j]] = true;

                    start_row = i;
                    start_col = j;
                    dfs(i, j, -1, -1, 0, 0);
                }
            }

            // 결과 출력
            bw.write("#" + test_case + " " + max);
            bw.newLine();
        }

        bw.close();
    }

    public static void dfs(int row, int col, int pre_row, int pre_col, int eatCount, int dir) {

        for (int i = dir; i < 4; i++) {
            int next_row = row + direction_row[i];
            int next_col = col + direction_col[i];

            if (!isPossiblePosition(next_row, next_col)) {
                continue;
            }

            if (next_row == pre_row && next_col == pre_col) {
                continue;
            }

            if (isStartPosition(next_row, next_col)) {
                max = Math.max(max, eatCount+1);
                return;
            }

            if (isAte[map[next_row][next_col]]) {
                continue;
            }

            isAte[map[next_row][next_col]] = true;
            dfs(next_row, next_col, row, col, eatCount + 1, i);
            isAte[map[next_row][next_col]] = false;
        }
    }

    public static boolean isStartPosition(int row, int col) {
        if (row == start_row && col == start_col) {
            return true;
        }
        return false;
    }

    public static boolean isPossiblePosition(int row, int col) {
        if (row >= 0 && row < N && col >= 0 && col < N) {
            return true;
        }
        return false;
    }
}