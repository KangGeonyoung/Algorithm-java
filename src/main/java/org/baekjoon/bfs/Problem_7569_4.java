package org.baekjoon.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Problem_7569_4 {

    static int R, C, H;
    static int[][][] map;
    static Queue<Tomato> tomatoes = new LinkedList<>();
    static boolean isAllComplete = true;

    static int[] direction_row = {0, 0, 0, 0, -1, 1};  // 상-하-좌-우-앞-뒤
    static int[] direction_col = {0, 0, -1, 1, 0, 0};
    static int[] direction_height = {1, -1, 0, 0, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        C = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        map = new int[H][R][C];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < R; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < C; k++) {
                    map[i][j][k] = Integer.parseInt(st.nextToken());
                    if (map[i][j][k] == 1) {
                        tomatoes.add(new Tomato(j, k, i));
                    }
                    if (map[i][j][k] == 0) {
                        isAllComplete = false;
                    }
                }
            }
        }

        bfs();

        // 초기부터 모두 익어있는 상태라면 0 출력
        if (isAllComplete) {
            System.out.println(0);
        } else {
            int result = findResult();
            System.out.println(result);
        }
    }

    public static void bfs() {
        while (!tomatoes.isEmpty()) {
            Tomato current = tomatoes.poll();

            for (int i = 0; i < 6; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];
                int next_height = current.height + direction_height[i];

                if (isValid(next_row, next_col, next_height)) {
                    if (map[next_height][next_row][next_col] == 0) {  // 익지 않았을 때만 퍼트리도록
                        map[next_height][next_row][next_col] = map[current.height][current.row][current.col] + 1;
                        tomatoes.add(new Tomato(next_row, next_col, next_height));
                    }
                }
            }
        }
    }

    public static int findResult() {
        int max = -1;

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < R; j++) {
                for (int k = 0; k < C; k++) {
                    max = Math.max(max, map[i][j][k]);
                    if (map[i][j][k] == 0) {  // 하나라도 익지 않은 토마토가 있다면 -1 리턴
                        return -1;
                    }
                }
            }
        }
        return max - 1;
    }

    public static boolean isValid(int row, int col, int height) {
        if (row >= 0 && col >= 0 && height >= 0 && row < R && col < C && height < H) {
            return true;
        }
        return false;
    }

    private static void print() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < R; j++) {
                for (int k = 0; k < C; k++) {
                    System.out.print(map[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    static class Tomato {
        int row;
        int col;
        int height;

        public Tomato(int row, int col, int height) {
            this.row = row;
            this.col = col;
            this.height = height;
        }
    }
}
