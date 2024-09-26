package org.baekjoon.implementation;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 구현 알고리즘 - 백준: 14502번 (골드 4)
 * - DFS, BFS 모두 사용되는 구현 문제이다.
 * - 무언가 퍼트리거나 번져 나간다는 개념에는 BFS를 적용하자.
 * - 무언가 설치하거나 모든 경우의 수를 대조하고 싶을 때는 DFS를 사용하자.
 * - 내가 실수한 점
 *   - DFS에서 일정 Depth에 도달하면 return을 통해 종료 시켰어야 했는데 누락해버렸다. -> 무한 루프 발생
 *   - BFS를 여러 번 사용해야 하는 경우에는 초기화를 잘해줘야 한다. ex) visited 초기화
 */
public class Problem_14502 {

    static int N, M;
    static int[][] map;
    static int[][] virusMap;
    static boolean[][] visited;
    static Queue<Location> queues = new LinkedList<>();
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
        virusMap = new int[N][M];
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
        // 벽 3개 다 세웠을 경우
        if (depth == 3) {
            // 바이러스 퍼트리기 시작
            virus();
            int safeZone = getSafeZone();
            if (safeZone > result) {
                result = safeZone;
            }
            return;
        }

        // 벽 1개 세우기
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 0) {
                    map[i][j] = 1;
                    dfs(depth + 1);
                    map[i][j] = 0;
                }
            }
        }
    }

    // 바이러스 활동 시작
    public static void virus() {
        // map을 virusMap에 깊은 복사
        copyArray();

        // 바이러스 위치를 기준으로 퍼트리기
        init();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 2) {
                    bfs(i, j);
                }
            }
        }
    }

    // 바이러스 맵에 현재 맵을 복사해서 바이러스 퍼트리기
    public static void bfs(int row, int col) {
        // 시작점을 큐에 추가
        Location start = new Location(row, col);
        queues.add(start);
        visited[start.row][start.col] = true;

        while (!queues.isEmpty()) {
            Location current = queues.poll();
            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isPossiblePosition(next_row, next_col) && !visited[next_row][next_col]) {
                    if (virusMap[next_row][next_col] != 1) {
                        Location next = new Location(next_row, next_col);
                        queues.add(next);
                        visited[next_row][next_col] = true;
                        virusMap[next_row][next_col] = 2;
                    }
                }
            }
        }
    }

    public static void init() {
        queues.clear();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                visited[i][j] = false;
            }
        }
    }

    public static void copyArray() {
        for (int i = 0; i < N; i++) {
            virusMap[i] = map[i].clone();
        }
    }

    public static boolean isPossiblePosition(int row, int col) {
        if (row >= 0 && row < N && col >= 0 && col < M) {
            return true;
        }
        return false;
    }

    public static int getSafeZone() {
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (virusMap[i][j] == 0) {
                    result += 1;
                }
            }
        }
        return result;
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
