package org.baekjoon.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/**
 * BFS 알고리즘 - 백준: 10026번 문제 (골드 5)
 * - BFS 알고리즘에서 퍼트리는 조건이 변형된 문제이다.
 */
public class Problem_10026 {

    static int N;
    static char[][] map;
    static int normal = 0;
    static int abnormal = 0;

    static int[] direction_row = {-1, 1, 0, 0};  // 상하좌우
    static int[] direction_col = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        map = new char[N][N];
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < line.length(); j++) {
                map[i][j] = line.charAt(j);
            }
        }

        // 정상
        boolean[][] visited = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    bfs_normal(i, j, visited);
                }
            }
        }

        // 색약
        boolean[][] visited_ab = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited_ab[i][j]) {
                    bfs_abnormal(i, j, visited_ab);
                }
            }
        }

        System.out.println(normal + " " + abnormal);
    }

    public static void bfs_normal(int startRow, int startCol, boolean[][] visited) {
        char digit = map[startRow][startCol];

        Queue<Node> queue = new LinkedList<>();
        visited[startRow][startCol] = true;
        queue.add(new Node(startRow, startCol));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    if (map[next_row][next_col] == digit) {
                        visited[next_row][next_col] = true;
                        queue.add(new Node(next_row, next_col));
                    }
                }
            }
        }

        normal += 1;
    }

    public static void bfs_abnormal(int startRow, int startCol, boolean[][] visited) {
        char digit = map[startRow][startCol];

        Queue<Node> queue = new LinkedList<>();
        visited[startRow][startCol] = true;
        queue.add(new Node(startRow, startCol));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    if (digit == 'B') {
                        if (map[next_row][next_col] == digit) {
                            visited[next_row][next_col] = true;
                            queue.add(new Node(next_row, next_col));
                        }
                    } else {
                        if (map[next_row][next_col] == 'R' || map[next_row][next_col] == 'G') {
                            visited[next_row][next_col] = true;
                            queue.add(new Node(next_row, next_col));
                        }
                    }
                }
            }
        }

        abnormal += 1;
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
