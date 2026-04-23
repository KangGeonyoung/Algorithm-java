package org.baekjoon.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BFS 알고리즘 - 백준: 2468번 (실버 1)
 * - 엣지 케이스를 항상 주의하자.
 */
public class Problem_2468_2 {

    static int N;
    static int[][] map;

    static int max = Integer.MIN_VALUE;
    static int min = Integer.MAX_VALUE;
    static int result = 0;

    static int[] direction_row = {-1, 1, 0, 0};  // 상하좌우
    static int[] direction_col = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                max = Math.max(max, map[i][j]);
                min = Math.min(min, map[i][j]);
            }
        }

        for (int rain = min; rain <= max; rain++) {
            int safeCnt = findSafeZone(rain);
            result = Math.max(result, safeCnt);
        }

        if (min == max) {
            result = 1;
        }

        System.out.println(result);
    }

    public static int findSafeZone(int rain) {
        int safeCnt = 0;
        boolean[][] visited = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 안전지대일 경우
                if (!visited[i][j] && map[i][j] > rain) {
                    bfs(i, j, visited, rain);
                    safeCnt += 1;
                }
            }
        }

        return safeCnt;
    }

    public static void bfs(int row, int col, boolean[][] visited, int rain) {
        Queue<Node> queue = new LinkedList<>();

        queue.add(new Node(row, col));
        visited[row][col] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    if (map[next_row][next_col] > rain) {
                        queue.add(new Node(next_row, next_col));
                        visited[next_row][next_col] = true;
                    }
                }
            }
        }
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < N) {
            return true;
        }
        return false;
    }

    static class Node {
        int row;
        int col;

        public Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
