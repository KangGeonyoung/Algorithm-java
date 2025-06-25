package org.baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 시뮬레이션 알고리즘 - 백준: 20056 (골드 4)
 * - 삼성 코테와 비슷한 문제이다.
 * - 배열에서 상하좌우를 벗어났을 때, 제외시키는 것이 아닌 차원이동 하여 다시 연결시켜주는 문제이다.
 * - 내가 실수한 점
 *   - 이동시킬 때 한칸씩 이동시켰어야 했는데 내가 한번에 이동시키는 바람에 차원이동하는 순간에 값이 꼬여버렸다.
 *   - 시간초과 오류가 나지 않는 한, 하나씩 이동시키는 것이 안전해보인다.
 *   - 시간초과가 날 것 같으면 주기를 찾아 이동시키는 횟수를 줄여야 한다.
 */
public class Problem_20056 {

    static int N, M, K;
    static Queue<FireBall> fireBalls = new LinkedList<>();
    static List<FireBall>[][] map;
    static int[] direction_row = {-1, -1, 0, 1, 1, 1, 0, -1};  // 0-1-2-3-4-5-6-7
    static int[] direction_col = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // 파이어볼 정보 저장
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int speed = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            fireBalls.add(new FireBall(row - 1, col - 1, m, speed, dir));
        }

        // K번 반복
        for (int i = 0; i < K; i++) {
            // map 초기화
            map = new List[N][N];
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    map[j][k] = new ArrayList<>();
                }
            }

            // 파이어볼 이동 명령 실행
            moveFireBalls(map);
        }

        // 남아있는 파이어볼 질량의 합 출력
        int total_m = 0;
        while (!fireBalls.isEmpty()) {
            FireBall fireBall = fireBalls.poll();
            total_m += fireBall.m;
        }
        System.out.println(total_m);


        /**
         * 1. 파이어볼 이동 명령 실행 (k번 실행)
         * 2. 이동 명령
         *      1. 파이어볼 큐에 담긴 파이어볼들을 하나씩 꺼내서 이동 좌표를 계산 후, map에 배치한다.
         *          - 꺼낸 파이어볼에 dir과 speed를 적용시켜서 map에 배치한다.
         *      2. 모든 map을 확인하면서 파이어볼이 2개 이상인 칸에서 파이어볼 분리 현상을 실행시킨다.
         *          - 파이어볼 분리 현상
         *              1. 파이어볼이 4개로 나뉜다.
         *                  - 질량 = 합친 파이어볼의 질량 합 / 5
         *                  - 속력 = 합친 파이어볼의 속력 합 / 합친 파이어볼 개수
         *                  - 합쳐진 파이어볼의 방향이 모두 홀수이거나, 모두 짝수라면 -> 방향 = 0 2 4 6
         *                  - 아니라면, 방향 = 1 3 5 7
         *      3. 다시한번 모든 map을 확인하면서 질량이 0이라면 해당 파이어볼은 소멸시키고, 아니라면 파이어볼 큐에 다시 담는다.
         *          - 3번이 끝난 후, map을 초기화 시킨다.
         */
    }

    public static void moveFireBalls(List<FireBall>[][] map) {
        // 파이어볼 모두 꺼내서 이동 좌표 계산 후, map에 배치
        while (!fireBalls.isEmpty()) {
            FireBall fireBall = fireBalls.poll();
            int current_row = fireBall.row;
            int current_col = fireBall.col;
            int current_dir = fireBall.dir;

            int next_row = 0;
            int next_col = 0;
            // 파이어볼의 speed만큼 이동
            for (int i = 0; i < fireBall.speed; i++) {
                next_row = current_row + direction_row[fireBall.dir];
                next_col = current_col + direction_col[fireBall.dir];

                // 0번 행에서 위로 벗어난 경우
                if (next_row < 0) {
                    if (current_dir == 7 || current_dir == 0 || current_dir == 1) {
                        next_row = N-1;
                    }
                }

                // N-1번 행에서 아래로 벗어난 경우
                if (next_row >= N) {
                    if (current_dir == 5 || current_dir == 4 || current_dir == 3) {
                        next_row = 0;
                    }
                }

                // 0번째 열에서 왼쪽으로 벗어난 경우
                if (next_col < 0) {
                    if (current_dir == 7 || current_dir == 6 || current_dir == 5) {
                        next_col = N-1;
                    }
                }

                // N-1번째 열에서 오른쪽으로 벗어난 경우
                if (next_col >= N) {
                    if (current_dir == 1 || current_dir == 2 || current_dir == 3) {
                        next_col = 0;
                    }
                }

                current_row = next_row;
                current_col = next_col;
            }

            fireBall.row = next_row;
            fireBall.col = next_col;

            // map에 이동한 파이어볼 배치
            map[next_row][next_col].add(fireBall);
        }

        // 모든 map을 확인하면서 한칸에 파이어볼이 2칸이상인 곳 분리 실행
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 한칸에 파이어볼이 2개 이상 들어있는 경우, 파이어볼 분리 실행
                if (map[i][j].size() >= 2) {
                    separateFireBall(map, i, j);
                }
            }
        }

        // 모든 map을 확인하면서 질량이 0이라면 해당 파이어볼은 소멸시키고, 아니라면 파이어볼 큐에 다시 담는다.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j].size() >= 1) {
                    List<FireBall> balls = map[i][j];
                    for (FireBall fireBall : balls) {
                        if (fireBall.m > 0) {
                            fireBalls.add(fireBall);
                        }
                    }
                }
                map[i][j].clear();
            }
        }
    }

    private static void separateFireBall(List<FireBall>[][] map, int i, int j) {
        int sum_m = 0;  // 합친 파이어볼의 질량 합
        int sum_speed = 0;  // 합친 파이어볼의 속력 합
        int cnt = map[i][j].size();  // 합친 파이어볼 개수

        boolean isAllEven = true;  // 짝수
        boolean isAllOdd = true;  // 홀수

        for (FireBall fireBall : map[i][j]) {
            sum_m += fireBall.m;
            sum_speed += fireBall.speed;
            if (fireBall.dir % 2 == 0) {
                isAllOdd = false;
            } else {
                isAllEven = false;
            }
        }

        int new_m = sum_m / 5;
        int new_speed = sum_speed / cnt;
        int[] new_dir;

        if (isAllEven || isAllOdd) {
            new_dir = new int[]{0, 2, 4, 6};
        } else {
            new_dir = new int[]{1, 3, 5, 7};
        }

        // 해당 공간의 map을 비워주고, 4개의 새로운 파이어볼 생성
        map[i][j].clear();
        for (int k = 0; k < 4; k++) {
            map[i][j].add(new FireBall(i, j, new_m, new_speed, new_dir[k]));
        }
    }

    static class FireBall {
        int row;
        int col;
        int m;
        int speed;
        int dir;

        public FireBall(int row, int col, int m, int speed, int dir) {
            this.row = row;
            this.col = col;
            this.m = m;
            this.speed = speed;
            this.dir = dir;
        }
    }
}
