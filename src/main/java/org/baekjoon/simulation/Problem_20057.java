package org.baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * 시뮬레이션 알고리즘 - 백준: 20057번 (골드 3)
 * - 까다로운 시뮬레이션 문제
 * - 방향에 따라 모래를 흩뿌리는 문제인데, 회전하는 부분에서 꽤나 시간이 걸렸다.
 * - 토네이도 경로에 따라 회전하는 부분과, 토네이도가 도착한 뒤 지정된 위치에 모래를 흩뿌리는 부분에서 또 한번의 회전 로직이 들어가기 때문에 복잡한 것 같다.
 * - 내가 실수한 점
 *   - 9개의 위치에 모래를 뿌려줘야 하는데 실수로 index를 i가 아닌 dir로 고정해놔서 모래를 한곳에만 뿌리는 오류가 발생했다.
 *   - 인덱스 지정할 때 집중할 것
 */
public class Problem_20057 {

    static int N;
    static int[][] map;
    static HashMap<Integer, Integer> directionMap = new HashMap<>();  // 좌(0)->하(1)->우(2)->상(3)->좌
    static int[] direction_row = {0, 1, 0, -1};  // 좌(0)->하(1)->우(2)->상(3)
    static int[] direction_col = {-1, 0, 1, 0};

    static int[] sand_row = {-1, 1, -2, 2, 0, -1, 1, -1, 1};
    static int[] sand_col = {1, 1, 0, 0, -2, 0, 0, -1, -1};
    static int[] ratio = {1, 1, 2, 2, 5, 7, 7, 10, 10};
    static int alpha_row = 0;
    static int alpha_col = -1;

    static int outSandAmount = 0;

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

        // 방향 맵 설정
        directionMap.put(0, 1);
        directionMap.put(1, 2);
        directionMap.put(2, 3);
        directionMap.put(3, 0);

        // 시작 위치 (map의 정가운데)
        int row = N / 2;
        int col = N / 2;
        int dir = 0;

        // 토네이도 한 칸 씩 이동 시키기
        int count = 0;
        int move = 1;  // 움직여야 하는 목표치
        int currentMove = 0;  // 현재까지 이동량

        while (true) {
            // (0, 0)일 때 종료
            if (row == 0 && col == 0) {
                break;
            }

            // 방향을 바꿔야 할 경우
            if (count == 2) {
                move += 1;
                count = 0;
            }

            if (move == currentMove) {
                dir = directionMap.get(dir);  // 다음 방향으로 변경
                count += 1;
                currentMove = 0;
            }

            // 해당 방향으로 한칸 이동
            row += direction_row[dir];
            col += direction_col[dir];
            currentMove += 1;

            // 도착한 칸에서 모래 흩뿌리기
            sandStorm(row, col, dir);
        }

        System.out.println(outSandAmount);

        /**
         *  1. 토네이도가 map의 가장 가운데에서 이동을 시작한다.
         *      - 1->1->2->2->3->3-> 이런 식으로 이동을 시작한다.
         *      - 위와 같은 이동량을 채우면 방향이 바뀐다.
         *      - 방향 : 좌 -> 하 -> 우 -> 상 -> 좌
         *
         *  2. 한칸 이동할 때마다 모래를 흩날려줘야 한다.
         *      - 이동을 먼저한다.
         *      - 도착한 칸에서 현재 칸의 모래를 주어진 비율만큼 흩뿌려준다. (총 뿌려주는 모래의 양은 45%)
         *      - 뿌려주다가 map을 벗어나면 그 모래는 소멸하게 된다. (소멸 모래 양에 더해주자)
         *      - 나머지 55%의 모래는 알파 위치에 더해준다.
         */
    }

    public static void sandStorm(int row, int col, int dir) {
        int sandAmount = map[row][col];  // 현재 칸의 모래 양

        // 9개의 방향으로 모래 흩뿌리기
        int totalSpread = 0;
        for (int i = 0; i < 9; i++) {
            int percent = ratio[i];
            int sandRow = row + rotate_row(sand_row[i], sand_col[i], dir);
            int sandCol = col + rotate_col(sand_row[i], sand_col[i], dir);

            int spreadAmount = sandAmount * percent / 100;
            totalSpread += spreadAmount;
            if (isValid(sandRow, sandCol)) {
                map[sandRow][sandCol] += spreadAmount;
            } else {
                outSandAmount += spreadAmount;
            }
        }

        int alphaRow = row + rotate_row(alpha_row, alpha_col, dir);
        int alphaCol = col + rotate_col(alpha_row, alpha_col, dir);
        int alphaAmount = sandAmount - totalSpread;

        if (isValid(alphaRow, alphaCol)) {
            map[alphaRow][alphaCol] += alphaAmount;
        } else {
            outSandAmount += alphaAmount;
        }

        // 현재 칸의 모래 없애기
        map[row][col] = 0;
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < N) {
            return true;
        }
        return false;
    }

    public static int rotate_row(int row, int col, int dir) {
        if (dir == 0) {  // 좌
            return row;
        }

        // (-1, 1) -> (-1, -1)
        // (1, 1) -> (-1, 1)
        if (dir == 1) {  // 하
            return col * (-1);
        }

        // (-1, 1) -> (1, -1)
        // (1, 1) -> (-1, -1)
        if (dir == 2) {  // 우
            return row * (-1);
        }

        // (-1, 1) -> (1, 1)
        // (1, 1) -> (1, -1)
        if (dir == 3) {  // 상
            return col;
        }

        return 0;
    }

    public static int rotate_col(int row, int col, int dir) {
        if (dir == 0) {  // 좌
            return col;
        }

        // (-1, 1) -> (-1, -1)
        // (1, 1) -> (-1, 1)
        if (dir == 1) {  // 하
            return row;
        }

        // (-1, 1) -> (1, -1)
        // (1, 1) -> (-1, -1)
        if (dir == 2) {  // 우
            return col * (-1);
        }

        // (-1, 1) -> (1, 1)
        // (1, 1) -> (1, -1)
        if (dir == 3) {  // 상
            return row * (-1);
        }

        return 0;
    }


    private static void print() {
        System.out.println();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
