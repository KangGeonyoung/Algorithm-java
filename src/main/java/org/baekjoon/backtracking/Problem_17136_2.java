package org.baekjoon.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Problem_17136_2 {

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
            if (canAttach(row, col, i) && paperCnt[i] > 0) {
                attach(row, col, i, 0);
                paperCnt[i] -= 1;
                dfs(row, col + 1, used + 1);
                attach(row, col, i, 1);
                paperCnt[i] += 1;
            }
        }
    }

    public static void attach(int row, int col, int paperSize, int value) {
        for (int i = row; i < row + paperSize; i++) {
            for (int j = col; j < col + paperSize; j++) {
                if (isValid(i, j)) {
                    map[i][j] = value;
                }
            }
        }
    }

    public static boolean canAttach(int row, int col, int paperSize) {
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
