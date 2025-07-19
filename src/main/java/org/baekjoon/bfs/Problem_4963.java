package org.baekjoon.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BFS 알고리즘 - 백준: 4963번 (실버 2)
 * - 탐색 방향이 8개인 BFS 문제이다.
 * - 방향 배열에 추가된 방향을 넣어주면 잘 풀린다.
 */
public class Problem_4963 {

    static int col, row;
    static int[][] map;
    static int landCnt = 0;
    static int[] direction_row = {0, 0, 1, -1, -1, -1, 1, 1};  // 동-서-남-북-북동-북서-남동-남서
    static int[] direction_col = {1, -1, 0, 0, 1, -1, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());
            col = Integer.parseInt(st.nextToken());
            row = Integer.parseInt(st.nextToken());

            // 종료 조건
            if (row == 0 && col == 0) {
                break;
            }

            // 입력 받기
            map = new int[row][col];
            for (int i = 0; i < row; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < col; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            // 섬 개수 찾기
            landCnt = 0;
            boolean[][] visited = new boolean[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (map[i][j] == 1 && !visited[i][j]) {
                        bfs(i, j, visited);
                    }
                }
            }

            System.out.println(landCnt);
        }
    }

    public static void bfs(int row, int col, boolean[][] visited) {
        Queue<Node> queue = new LinkedList<>();

        queue.add(new Node(row, col));
        visited[row][col] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < 8; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    if (map[next_row][next_col] == 1) {
                        queue.add(new Node(next_row, next_col));
                        visited[next_row][next_col] = true;
                    }
                }
            }
        }
        landCnt += 1;
    }

    public static boolean isValid(int r, int c) {
        if (r >= 0 && c >= 0 && r < row && c < col) {
            return true;
        }
        return false;
    }

    private static void print() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
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
