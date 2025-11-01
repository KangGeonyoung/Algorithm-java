package org.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 블록 돌리기 시뮬레이션
 * - 블록을 시계방향으로 90도 회전 시키는 시뮬레이션이다.
 * - 중심 좌표를 기준으로 90도 회전 시키는 로직에 특정 계산법이 있어 외워두는 것이 편하다.
 *
 * Input)
 * 5
 * 1 2 3 4 5
 * 1 2 3 4 5
 * 1 2 3 4 5
 * 1 2 3 4 5
 * 1 2 3 4 5
 */
public class Rotate {

    static int N;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        printMap();
        rotate90(map, 2, 2);
        printMap();
    }

    public static void rotate90(int[][] map, int centerRow, int centerCol) {
        // 사이즈 지정
        int size = 3;
        int[][] temp = new int[size][size];

        int start_row = centerRow - 1;
        int start_col = centerCol - 1;

        // 원본 배열 복제
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                temp[i][j] = map[start_row + i][start_col + j];
            }
        }

        // 시계방향 90도 회전 실행
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[start_row + j][start_col + (size - 1 - i)] = temp[i][j];
            }
        }
    }


    public static void printMap() {
        System.out.println();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
