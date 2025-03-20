package org.baekjoon.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BFS 알고리즘 - 백준: 7569번 문제(3) (골드 5)
 * - bfs에서 다음 자리로 넘어가는 조건문을 조심해야 한다.
 * - 이 문제에서는 익지 않은 토마토(=0)일 때만 이동할 수 있게 해야 풀린다.
 */
public class Problem_7569_3 {

    static int row, col, height;
    static int[][][] map;
    static Queue<Node> queue = new LinkedList<>();
    static int[] direction_height = {1, -1, 0, 0, 0, 0};
    static int[] direction_row = {0, 0, 0, 0, 1, -1};
    static int[] direction_col = {0, 0, -1, 1, 0, 0};
    static int result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        col = Integer.parseInt(st.nextToken());
        row = Integer.parseInt(st.nextToken());
        height = Integer.parseInt(st.nextToken());

        map = new int[height][row][col];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < row; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < col; k++) {
                    map[i][j][k] = Integer.parseInt(st.nextToken());
                    if (map[i][j][k] == 1) {
                        queue.add(new Node(i, j, k));
                    }
                }
            }
        }

        bfs();
        checkResult();
        System.out.println(result);
    }

    public static void checkResult() {
        int max = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < row; j++) {
                for (int k = 0; k < col; k++) {
                    if (map[i][j][k] == 0) {
                        result = -1;
                        return;
                    }
                    max = Math.max(max, map[i][j][k]);
                }
            }
        }

        if (max == 1) {
            result = 0;
        } else {
            result = max - 1;
        }
    }

    public static void bfs() {
        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < 6; i++) {
                int next_height = current.height + direction_height[i];
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_height, next_row, next_col)) {
                    if (map[next_height][next_row][next_col] == 0) {
                        map[next_height][next_row][next_col] = map[current.height][current.row][current.col] + 1;
                        queue.add(new Node(next_height, next_row, next_col));
                    }
                }
            }
        }
    }

    public static boolean isValid(int h, int r, int c) {
        if (h >= 0 && r >= 0 && c >= 0 && h < height && r < row && c < col) {
            return true;
        }
        return false;
    }

    static class Node {
        int height;
        int row;
        int col;

        public Node(int height, int row, int col) {
            this.height = height;
            this.row = row;
            this.col = col;
        }
    }
}
