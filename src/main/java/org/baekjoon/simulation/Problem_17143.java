package org.baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시뮬레이션 알고리즘 - 백준: 17143 (골드 1)
 * - 약간 삼성 코테와 비슷한 유형의 문제이다.
 * - 시간초과가 난 부분
 *     - 상어의 speed만큼 for문으로 상어를 이동시킨 부분에서 시간초과가 났다.
 *     - speed를 정직하게 모두 이동시킨 것이 문제였다.
 *     - 상어의 이동범위는 한행 또는 한열 이다.
 *     - 따라서 규칙에 따라 speed를 줄일 수가 있었다.
 * - 유용한 규칙
 *     - 주기를 이용하는 것이다.
 *     - 만약 한행이 4라고 가정하자.
 *     - 그러면 주기는 1-2-3-4-3-2-1 이렇게 되고, 1->1까지 가는데 6이 걸린다.
 *     - 이 주기를 식으로 변환하면 2 * (R - 1) 이 된다.
 *     - speed = speed % (2 * (R - 1)) 로 speed의 크기를 줄일 수 있다.
 *     - 이렇게 하면 시간초과가 해결된다.
 * - 이렇게 행이나 열을 왔다 갔다 하는 시뮬레이션 문제를 최근 코테에서 많이 접한 것 같다.
 */
public class Problem_17143 {

    static int R, C, M;
    static Shark[][] sharkMap;
    static Queue<Shark> sharks = new LinkedList<>();  // 회수용 상어 큐
    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        sharkMap = new Shark[R  + 1][C + 1];
        for (int i = 1; i <= R; i++) {
            for (int j = 1; j <= C; j++) {
                sharkMap[i][j] = new Shark();
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            int speed = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            int size = Integer.parseInt(st.nextToken());

            sharkMap[row][col] = new Shark(row, col, speed, dir, size);
        }

        int manIdx = 0;
        while (true) {

            // 1번
            manIdx += 1;

            // 종료 조건
            if (manIdx == C + 1) {
                break;
            }

            // 2번
            for (int i = 1; i <= R; i++) {
                // 상어가 있다면, 잡아먹고 삭제해주기
                if (sharkMap[i][manIdx].size > 0) {
                    result += sharkMap[i][manIdx].size;
                    sharkMap[i][manIdx] = new Shark();
                    break;
                }
            }

            // 3번
            sharks.clear();

            // 3-1. 상어 회수
            for (int i = 1; i <= R; i++) {
                for (int j = 1; j <= C; j++) {
                    if (sharkMap[i][j].size > 0) {
                        sharks.add(sharkMap[i][j]);
                    }
                }
            }

            // 3-2. sharkMap 초기화
            for (int i = 1; i <= R; i++) {
                for (int j = 1; j <= C; j++) {
                    sharkMap[i][j] = new Shark();
                }
            }

            // 3-3. 상어를 하나씩 꺼내, 이동 좌표를 계산하여 sharkMap에 배치
            while (!sharks.isEmpty()) {
                Shark currentShark = sharks.poll();

                int row = currentShark.row;
                int col = currentShark.col;
                int speed = currentShark.speed;
                int dir = currentShark.dir;
                int size = currentShark.size;

                if (dir == 1 || dir == 2) {
                    speed = speed % (2 * (R - 1));
                } else {
                    speed = speed % (2 * (C - 1));
                }

                // 주어진 방향으로 한 칸씩 speed 만큼 이동해서 새로운 좌표 획득
                for (int i = 0; i < speed; i++) {
                    if (dir == 1) {  // 상
                        row = row - 1;
                        if (row == 0) {  // 방향을 상->하로 변경하고, 한칸 이동
                            dir = 2;
                            row = row + 2;
                        }
                        col = col;
                        continue;
                    }
                    if (dir == 2) {  // 하
                        row = row + 1;
                        if (row == R + 1) {  // 방향을 하->상 으로 변경하고, 한칸 이동
                            dir = 1;
                            row = row - 2;
                        }
                        col = col;
                        continue;
                    }
                    if (dir == 3) {  // 우
                        col = col + 1;
                        if (col == C + 1) {  // 방향을 우->좌 로 변경하고, 한칸 이동
                            dir = 4;
                            col = col - 2;
                        }
                        row = row;
                        continue;
                    }
                    if (dir == 4) {  // 좌
                        col = col - 1;
                        if (col == 0) {  // 방향을 좌->우 로 변경하고, 한칸 이동
                            dir = 3;
                            col = col + 2;
                        }
                        row = row;
                        continue;
                    }
                }

                // 계산된 좌표에 있는 size보다 현재 상어가 더 클 때만 상어 배치
                if (sharkMap[row][col].size < size) {
                    sharkMap[row][col] = new Shark(row, col, speed, dir, size);
                }
            }
        }

        System.out.println(result);

        // 1. 낚시꾼이 1칸 이동 (1 ~ C열)
        // 2. 낚시꾼이 있는 열에서 제일 마지막 행(= R)부터 역순으로 물고기 검사
        //  - 물고기가 있다면 잡아먹기 = sharkMap에서 해당 물고기 삭제 이후, result에 해당 상어의 size만큼 더해주기
        // 3. 상어의 이동
        //  - 1. 모든 상어를 큐에 회수
        //  - 2. sharkMap을 새롭게 초기화
        //  - 3. 상어를 하나씩 꺼내서 이동 좌표 계산하고 sharkMap에 배치, 상(1)-하(2)-우(3)-좌(4)
        //      - dir에 따라 모두 각자 계산해줘야 함
        //      - 만약 이미 해당 좌표에 상어가 존재할 시, size가 큰 상어가 자리를 차지하게 된다.
    }

    static class Shark {
        int row;
        int col;
        int speed;
        int dir;
        int size;

        public Shark() {
            this.row = 0;
            this.col = 0;
            this.speed = 0;
            this.dir = 0;
            this.size = 0;
        }

        public Shark(int row, int col, int speed, int dir, int size) {
            this.row = row;
            this.col = col;
            this.speed = speed;
            this.dir = dir;
            this.size = size;
        }
    }
}
