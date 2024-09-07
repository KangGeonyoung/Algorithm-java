package org.baekjoon.simulation;

import java.io.*;
import java.util.StringTokenizer;

/**
 * 시뮬레이션 알고리즘 - 백준: 14503 (골드 5)
 * - 구현이나 시뮬레이션 문제는 동서남북 방향을 이용하는 문제들이 많은 것 같다.
 * - 특정 방향일 때의 변화량을 배열로 하드코딩 해두고 사용하면 편하다.
 * - 예상치도 못하게 한번에 풀려서 놀란 문제
 */
public class Problem_14503 {
    static int N, M;
    static int robot_row, robot_col;
    static int direction;
    static int[][] cleanState;  // 0 : 청소되지 않은 빈칸, 1 : 벽, -1 : 청소 완료된 칸
    static int cleanCount = 0;
    static int[] direction_row = {0, -1, 0, 1};  // 북 -> 동 -> 남 -> 서
    static int[] direction_col = {-1, 0, 1, 0};
    static int[] back_row = {1, 0, -1, 0};  // 북 -> 동 -> 남 -> 서
    static int[] back_col = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // N, M 입력 받기
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        cleanState = new int[N][M];

        // 로봇 청소기 초기 정보
        st = new StringTokenizer(br.readLine());
        robot_row = Integer.parseInt(st.nextToken());
        robot_col = Integer.parseInt(st.nextToken());
        direction = Integer.parseInt(st.nextToken());

        // 청소 상태 정보
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                cleanState[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 로봇 청소기 지정된 위치에서 청소 시작
        cleanRobot(robot_row, robot_col);

        // 결과 출력
        bw.write(Integer.toString(cleanCount));
        bw.close();
    }

    public static void cleanRobot(int current_row, int current_col) {
        // 현재 칸이 청소되지 않은 칸(=0)이라면, 청소 실행
        if (cleanState[current_row][current_col] == 0) {
            cleanCount += 1;
            cleanState[current_row][current_col] = -1;
        }

        // 반시계로 90도 회전하면서 해당 칸이 청소되지 않았으면 한 칸 전진
        for (int i = 0; i < 4; i++) {
            int next_row = current_row + direction_row[direction];
            int next_col = current_col + direction_col[direction];
            changeDirection();

            // 청소되지 않은 빈칸일 경우 전진
            if (cleanState[next_row][next_col] == 0) {
                robot_row = next_row;
                robot_col = next_col;
                cleanRobot(robot_row, robot_col);
                return;
            }
        }

        // 반시계로 90도를 4번 회전하여 제자리로 돌아왔는데도 청소할 곳이 없다면,
        // 현재 방향 유지한 채로 한 칸 후진
        robot_row = robot_row + back_row[direction];
        robot_col = robot_col + back_col[direction];

        // 만약 뒤쪽의 칸이 벽이라면 작동 중단
        if (cleanState[robot_row][robot_col] == 1) {
            return;
        }

        // 후진 실행된 자리에서 다시 탐색
        cleanRobot(robot_row, robot_col);
    }

    private static void changeDirection() {
        direction -= 1;
        if (direction < 0) {
            direction = 3;
        }
    }
}
