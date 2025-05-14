package org.baekjoon.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백트래킹 알고리즘 - 백준: 17136번 (골드 2)
 * - 백트래킹 알고리즘은 dfs를 통한 완전 탐색에 가까운 알고리즘 방식이다.
 * - 하지만 dfs와는 다르게 탐색하기 전 특정 조건을 통과해야 탐색할 수 있다.
 * - 하나의 지점에서 발생할 수 있는 경우의 수를 모두 돌린다.
 *   - depth가 깊은 것이 특징
 *   - dfs의 매개변수에 정답값의 후보들을 실어서 보내면 조건에 맞는 정답을 도출할 수 있다.
 */
public class Problem_17136 {

    static int[][] map;
    static int[] paperCnt = {0, 5, 5, 5, 5, 5};
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[10][10];
        for (int i = 0; i < 10; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 10; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0, 0);

        if (answer == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(answer);
        }
    }

    public static void dfs(int row, int col, int used) {
        if (row >= 10) {
            answer = Math.min(answer, used);
            return;
        }

        if (col >= 10) {
            dfs(row + 1, 0, used);
            return;
        }

        if (map[row][col] == 0) {
            dfs(row, col + 1, used);
            return;
        }

        for (int i = 5; i > 0; i--) {
            if (canUsePaper(row, col, i) && paperCnt[i] > 0) {
                usePaper(row, col, i, 0);
                paperCnt[i] -= 1;
                dfs(row, col + 1, used + 1);
                usePaper(row, col, i, 1);
                paperCnt[i] += 1;
            }
        }
    }

    public static void usePaper(int row, int col, int paperSize, int value) {
        for (int i = row; i < row + paperSize; i++) {
            for (int j = col; j < col + paperSize; j++) {
                if (isValid(i, j)) {
                    map[i][j] = value;
                }
            }
        }
    }

    public static boolean canUsePaper(int row, int col, int paperSize) {
        for (int i = row; i < row + paperSize; i++) {
            for (int j = col; j < col + paperSize; j++) {
                if (isValid(i, j)) {
                    if (map[i][j] == 0) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < 10 && col < 10) {
            return true;
        }
        return false;
    }
}
