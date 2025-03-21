package org.baekjoon.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 구현 알고리즘 - 백준: 14502번 (3) (골드 4)
 * - BFS에서 굳이 visited를 안써도 되는 경우가 있다. -> 시간이 절약됨
 * - DFS에서 항상 base case를 고려해줘야 한다. -> return문이 있어야 함
 * - map을 카피해야 하는 경우, 매개변수로 어떤 map을 전달하고 있는지 잘 확인하자.
 */
public class Problem_14502_3 {
    static int row, col;
    static int[][] map;
    static int[] direction_row = {-1, 1, 0, 0};  // 상하좌우
    static int[] direction_col = {0, 0, -1, 1};
    static int max = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        row = Integer.parseInt(st.nextToken());
        col = Integer.parseInt(st.nextToken());
        map = new int[row][col];

        for (int i = 0; i < row; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < col; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0);
        System.out.println(max);
    }

    public static void dfs(int depth) {
        if (depth == 3) {
            // 바이러스 퍼트릴 새로운 맵 카피
            int[][] newMap = copyMap();

            // 바이러스 퍼트리기
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (map[i][j] == 2) {
                        bfs(newMap, i, j);
                    }
                }
            }

            // 안전 영역 구하기
            max = Math.max(max, getSafeZone(newMap));
            return;
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (map[i][j] == 0) {
                    map[i][j] = 1;
                    dfs(depth + 1);
                    map[i][j] = 0;
                }
            }
        }
    }

    public static void bfs(int[][] map, int start_row, int start_col) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start_row, start_col));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];
                if (isValid(next_row, next_col)) {
                    if (map[next_row][next_col] == 0) {
                        map[next_row][next_col] = 2;
                        queue.add(new Node(next_row, next_col));
                    }
                }
            }
        }
    }

    public static int getSafeZone(int[][] map) {
        int sum = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (map[i][j] == 0) {
                    sum += 1;
                }
            }
        }
        return sum;
    }

    public static boolean isValid(int r, int c) {
        if (r >= 0 && c >= 0 && r < row && c < col) {
            return true;
        }
        return false;
    }

    public static int[][] copyMap() {
        int[][] newMap = new int[row][col];
        for (int i = 0; i < row; i++) {
            newMap[i] = map[i].clone();
        }
        return newMap;
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
