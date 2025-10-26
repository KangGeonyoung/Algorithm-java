package org.baekjoon.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BFS 알고리즘 - 백준: 1012번 (실버 2)
 * - 흔한 BFS 문제이다.
 */
public class Problem_1012 {

    static int T;
    static int M, N;
    static int cnt;
    static int[][] map;

    static int[] direction_row = {-1, 1, 0, 0};  // 상하좌우
    static int[] direction_col = {0, 0, -1, 1};
    static int result = 0;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            cnt = Integer.parseInt(st.nextToken());

            result = 0;
            map = new int[N][M];

            // 배추의 위치 등록
            for (int i = 0; i < cnt; i++) {
                st = new StringTokenizer(br.readLine());
                int col = Integer.parseInt(st.nextToken());
                int row = Integer.parseInt(st.nextToken());

                map[row][col] = 1;
            }

            boolean[][] visited = new boolean[N][M];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    // 배추가 있는 곳이라면 bfs 탐색
                    if (map[i][j] == 1 && !visited[i][j]) {
                        bfs(i, j, visited);
                    }
                }
            }

            System.out.println(result);
        }
    }

    public static void bfs(int startRow, int startCol, boolean[][] visited) {
        result += 1;

        Queue<Node> queue = new LinkedList<>();
        visited[startRow][startCol] = true;
        queue.add(new Node(startRow, startCol));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    if (map[next_row][next_col] == 1) {
                        visited[next_row][next_col] = true;
                        queue.add(new Node(next_row, next_col));
                    }
                }
            }
        }
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < M) {
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
