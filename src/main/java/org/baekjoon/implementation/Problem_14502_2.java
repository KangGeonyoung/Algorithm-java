package org.baekjoon.implementation;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Problem_14502_2 {

    static int N, M;
    static int[][] map;
    static int[][] copyMap;
    static boolean[][] visited;

    static int[] direction_row = {0, -1, 0, 1};  // 왼쪽 -> 위 -> 오른쪽 -> 아래
    static int[] direction_col = {-1, 0, 1, 0};  // 왼쪽 -> 위 -> 오른쪽 -> 아래
    static int result = Integer.MIN_VALUE;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // N, M 입력 받기
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        copyMap = new int[N][M];
        visited = new boolean[N][M];

        // 지도 정보 입력 받기
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0);

        bw.write(Integer.toString(result));
        bw.close();
    }

    public static void dfs(int depth) {
        if (depth == 3) {
            result = Math.max(result, getSafeZone());
            return;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 0 && !visited[i][j]) {
                    map[i][j] = 1;
                    visited[i][j] = true;

                    dfs(depth + 1);

                    map[i][j] = 0;
                    visited[i][j] = false;
                }
            }
        }
    }

    public static int getSafeZone() {
        copyMap();
        startVirus();
        return calculateSafeZone();
    }

    public static int calculateSafeZone() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (copyMap[i][j] == 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public static void startVirus() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 2) {
                    bfs(i, j);
                }
            }
        }
    }

    public static void bfs(int row, int col) {
        Queue<Location> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];

        Location start = new Location(row, col);
        queue.add(start);
        visited[row][col] = true;

        while(!queue.isEmpty()) {
            Location current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if(isPossiblePosition(next_row, next_col) && !visited[next_row][next_col]) {
                    if (copyMap[next_row][next_col] != 1) {
                        copyMap[next_row][next_col] = 2;
                        Location next = new Location(next_row, next_col);
                        queue.add(next);
                        visited[next_row][next_col] = true;
                    }
                }
            }
        }
    }

    public static boolean isPossiblePosition(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < M) {
            return true;
        }
        return false;
    }

    public static void copyMap() {
        for (int i = 0; i < N; i++) {
            copyMap[i] = map[i].clone();
        }
    }

    static class Location {
        int row;
        int col;

        public Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
