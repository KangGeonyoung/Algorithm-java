package org.baekjoon.recursion;

import java.io.*;
import java.util.StringTokenizer;

/**
 * 재귀 알고리즘 - 백준: 2630번 (실버 2)
 * - 같은 패턴을 찾기
 * - 종료 조건 찾기
 */
public class Problem_2630 {
    static int N;
    static int[][] map;
    static int white = 0;
    static int blue = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // N 입력 받기
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        // 색종이 정보
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 시작점을 (0, 0)으로 하고, N 사이즈를 검사
        Recursion(0, 0, N);

        // 결과 출력
        bw.write(Integer.toString(white));
        bw.write('\n');
        bw.write(Integer.toString(blue));
        bw.close();
    }

    public static void Recursion(int row, int col, int size) {
        // white 검사
        boolean isWhite = true;
        for (int i = row; i < row + size; i++) {
            for (int j = col; j < col + size; j++) {
                if (map[i][j] == 1) {
                    isWhite = false;
                    break;
                }
            }
            if (!isWhite) {
                break;
            }
        }

        // blue 검사
        boolean isBlue = true;
        for (int i = row; i < row + size; i++) {
            for (int j = col; j < col + size; j++) {
                if (map[i][j] == 0) {
                    isBlue = false;
                    break;
                }
            }
            if (!isBlue) {
                break;
            }
        }

        // Base case
        // 모두 white일 경우 종료
        if (isWhite) {
            white += 1;
            return;
        }
        // 모두 blue일 경우 종료
        if (isBlue) {
            blue += 1;
            return;
        }

        // 재귀 호출 - 4등분 하여 재귀 호출
        Recursion(row, col, size / 2);
        Recursion(row, col + (size / 2), size / 2);
        Recursion(row + (size / 2), col, size / 2);
        Recursion(row + (size / 2), col + (size / 2), size / 2);
    }
}
