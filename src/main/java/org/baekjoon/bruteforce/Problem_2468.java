package org.baekjoon.bruteforce;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 완전탐색 알고리즘 - 백준: 2468번 (실버 1)
 * - 완전탐색도 은근 bfs를 이용하며 은근 편하다.
 * - 항상 기억할 점
 *   - bfs는 연결된 애들을 찾을 때 편리하다.
 *   - 뭔가 퍼트리거나, 덩어리 진 애들의 개수를 찾을 때
 */
public class Problem_2468 {

    static int N;
    static int[][] map;
    static int[][] waterMap;
    static Queue<Location> queue = new LinkedList<>();
    static int max = Integer.MIN_VALUE;
    static int safeZoneMax = Integer.MIN_VALUE;
    static int[] direction_row = {0, -1, 0, 1};  // 왼 -> 위 -> 오 -> 아래
    static int[] direction_col = {-1, 0, 1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // N 입력 받기
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        // 높이 정보 입력 받기
        map = new int[N][N];
        waterMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] > max) {
                    max = map[i][j];
                }
            }
        }

        // 모든 물의 높이로 안정영역 개수 확인
        for (int i = 0; i <= max; i++) {
            int safeZoneCnt = checkSafeZone(i);

            if (safeZoneCnt > safeZoneMax) {
                safeZoneMax = safeZoneCnt;
            }
        }

        bw.write(Integer.toString(safeZoneMax));
        bw.close();
    }

    public static int checkSafeZone(int waterHigh) {
        int safeZone = 0;

        // 원본 배열을 카피
        init();

        // 잠긴 지역 표시하기
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (waterMap[i][j] <= waterHigh) {
                    waterMap[i][j] = 0;
                }
            }
        }

        // 안전지역 개수 확인
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (waterMap[i][j] > 0) {
                    queue.clear();
                    bfs(i, j);
                    safeZone += 1;
                }
            }
        }

        return safeZone;
    }

    public static void bfs(int row, int col) {
        Location start = new Location(row, col);
        queue.add(start);
        waterMap[row][col] = 0;

        while (!queue.isEmpty()) {
            Location current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if(isPossiblePosition(next_row, next_col) && waterMap[next_row][next_col] != 0) {
                    Location next = new Location(next_row, next_col);
                    queue.add(next);
                    waterMap[next_row][next_col] = 0;
                }
            }
        }
    }

    public static boolean isPossiblePosition(int row, int col) {
        if (row >= 0 && row < N && col >= 0 && col < N) {
            return true;
        }
        return false;
    }

    public static void init() {
        for (int i = 0; i < map.length; i++) {
            waterMap[i] = map[i].clone();
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
