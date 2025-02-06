package org.baekjoon.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BFS 알고리즘 - 백준: 7569번 문제 (골드 5)
 * - 무언가를 동시에 퍼트릴려면 queue에 한번에 넣어서 퍼트려야 한다.
 * - 해당 문제에서는 익은 토마토를 하나씩 찾아서 BFS를 돌리다가 다른 결과가 나와서 틀렸다.
 * - input을 입력 받을 때, 미리 큐에 넣어서 그 이후에 BFS를 돌리면 된다.
 */
public class Problem_7569_2 {

    static int M, N, H;
    static int[][][] map;
    static int[] direction_row = {0, 0, 0, 0, -1, 1};  // 상-하-좌-우-앞-뒤
    static int[] direction_col = {0, 0, -1, 1, 0, 0};
    static int[] direction_height = {1, -1, 0, 0, 0, 0};
    static int result;
    static Queue<Node> queue = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());  // col
        N = Integer.parseInt(st.nextToken());  // row
        H = Integer.parseInt(st.nextToken());  // height

        map = new int[H][N][M];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < N; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < M; k++) {
                    map[i][j][k] = Integer.parseInt(st.nextToken());
                    if (map[i][j][k] == 1) {
                        queue.add(new Node(i, j, k));
                    }
                }
            }
        }

        bfs();
        calculateResult(map);
        System.out.println(result);
    }

    public static void calculateResult(int[][][] map) {
        int max = 0;

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M; k++) {
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

    public static boolean isValid(int height, int row, int col) {
        if (height >= 0 && height < H && row >= 0 && row < N && col >= 0 && col < M) {
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
