package org.baekjoon.simulation;

import java.io.*;
import java.util.StringTokenizer;

/**
 * 시뮬레이션 알고리즘 - 백준: 14719번 (골드 5)
 * - 시뮬레이션 문제 속에서는 일정한 규칙을 찾아야 한다.
 * - 해당 문제에서는 양쪽 벽만 존재하면 물이 고인다는 규칙이 존재했다.
 * - 내가 실수한 점
 *   - while문 속 구문은 조건문이 true여야 실행된다.
 */
public class Problem_14719 {
    static int H, W;
    static int[][] map;
    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // H, W 입력 받기
        st = new StringTokenizer(br.readLine());
        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        // 블록 입력 받기
        map = new int[H][W];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < W; i++) {
            int blockCnt = Integer.parseInt(st.nextToken());
            for (int j = H - 1; j >= H - blockCnt; j--) {
                map[j][i] = 1;
            }
        }

        // 칸마다 물이 고일 수 있는지 검사
        // 1 : 블록, 0 : 빈 공간
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j] == 0) {
                    if (isPossibleWater(i, j)) {
                        result += 1;
                    }
                }
            }
        }

        bw.write(Integer.toString(result));
        bw.close();
    }

    public static boolean isPossibleWater(int row, int col) {
        // 양 끝 경계에 존재하는 경우 -> 물이 고일 수가 없음
        if (col == 0 || col == W-1) {
            return false;
        }

        boolean isValidLeft = false;
        boolean isValidRight = false;

        // 왼쪽에 벽이 있는지 검사
        int copyCol = col;
        while (copyCol != 0 && copyCol != W-1) {
            copyCol -= 1;
            int isBlock = map[row][copyCol];
            if (isBlock == 1) {
                isValidLeft = true;
                break;
            }
        }

        // 오른쪽에 벽이 있는지 검사
        copyCol = col;
        while (copyCol != 0 && copyCol != W-1) {
            copyCol += 1;
            int isBlock = map[row][copyCol];
            if (isBlock == 1) {
                isValidRight = true;
                break;
            }
        }

        // 양 방향에 벽이 있을 경우에 true
        if (isValidLeft && isValidRight) {
            return true;
        }
        return false;
    }
}
