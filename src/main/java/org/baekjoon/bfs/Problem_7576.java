package org.baekjoon.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BFS 알고리즘 - 백준: 7576번 문제 (골드 5)
 * - 토마토 2차원 BFS 문제이다.
 * - 내가 실수한 점
 *   - bfs는 4개의 방향으로 한번 퍼트리고 나서야 다음 큐 요소로 넘어가게 된다.
 *   - 나는 bfs 큐 요소 하나를 map에서 갈 수 있는 곳까지 한번에 다 퍼트리는 줄 알았다.
 */
public class Problem_7576 {

    static int M, N;
    static int[][] map;
    static Queue<Tomato> tomatoes = new LinkedList<>();

    static int[] direction_row = {-1, 1, 0, 0};  // 상-하-좌-우
    static int[] direction_col = {0, 0, -1, 1};

    static boolean isAllTomato = true;  // 모두 익은 토마토일 경우
    static int max = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                // 익은 토마토의 위치 정보 추가
                if (map[i][j] == 1) {
                    tomatoes.add(new Tomato(i, j));
                }
                if (map[i][j] == 0) {
                    isAllTomato = false;
                }
            }
        }

        // 익은 토마토 퍼트리기
        bfs();

        // 이미 처음부터 모두 익어 있는 경우
        if (isAllTomato) {
            System.out.println(0);
            return;
        }

        // 최소 날짜 구하기
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 0) {
                    System.out.println(-1);
                    return;
                }
                max = Math.max(max, map[i][j]);
            }
        }
        System.out.println(max - 1);
    }

    private static void bfs() {
        while (!tomatoes.isEmpty()) {
            Tomato tomato = tomatoes.poll();

            for (int i = 0; i < 4; i++) {
                int next_row = tomato.row + direction_row[i];
                int next_col = tomato.col + direction_col[i];

                if (isValid(next_row, next_col)) {
                    if (map[next_row][next_col] == 0) {
                        tomatoes.add(new Tomato(next_row, next_col));
                        map[next_row][next_col] = map[tomato.row][tomato.col] + 1;
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

    private static void print() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static class Tomato {
        int row;
        int col;

        public Tomato(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
