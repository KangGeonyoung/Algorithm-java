package org.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 테트리스 블록 시뮬레이션
 * - 테트리스 형식의 블록 쌓기 시뮬레이션 알고리즘이다.
 * - 블록 쌓기 단계
 *   1. 블록이 위치하는 col 범위 내에서 쌓을 수 있는 최대 높이를 구한다.
 *   2. 최대 높이를 이용해 시작 row를 계산한다.
 *   3. 블록의 높이만큼 한줄 쌓기를 반복한다.
 *   4. 하나의 블록을 다 쌓았다면 높이 배열에 쌓은만큼 더해주면서 갱신한다.
 *
 * Input)
 * 6 4
 * 1 1 3 1
 * 2 3 2 2
 * 3 2 2 1
 * 4 5 2 3
 */
public class Tetris {

    static int N, M;
    static int[][] map;
    static int[] height;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        height = new int[N];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            int width = Integer.parseInt(st.nextToken());
            int blockHeight = Integer.parseInt(st.nextToken());

            dropBlock(num, col, width, blockHeight);
            printMap();
        }

        printMap();
    }

    public static void dropBlock(int num, int startCol, int width, int blockHeight) {
        // 블록이 떨어지는 구간의 맵에 현재 높이 구하기(아무 블록이 없다면 0)
        int maxHeight = 0;
        for (int i = startCol; i < startCol + width; i++) {
            if (i >= N) {
                break;
            }
            maxHeight = Math.max(maxHeight, height[i]);
        }

        // 시작 row 계산
        int baseRow = N - maxHeight - 1;

        // row 방향으로 한줄씩 blockHeight 만큼 반복하여 블록 쌓기
        for (int i = 0; i < blockHeight; i++) {
            // 블록 쌓을 row 계산
            int row = baseRow - i;
            if (row < 0) {
                break;
            }

            // 해당 row에 col만큼 블록 쌓기
            for (int j = startCol; j < startCol + width; j++) {
                if (j < 0 || j >= N) {
                    continue;
                }
                map[row][j] = num;
            }
        }

        // 높이 정보 갱신
        for (int i = startCol; i < startCol + width; i++) {
            if (i >= N) {
                break;
            }

            height[i] += blockHeight;
            if (height[i] > N) {
                height[i] = N;
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
