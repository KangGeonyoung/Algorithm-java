package org.baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 시뮬레이션 알고리즘 - 백준: 3190 (골드 4)
 * - 뱀을 움직이는 문제이다.
 * - 일반적인 상하좌우 규칙적으로 움직이는 것이 아닌, 뱀이 바라보는 방향의 오른쪽 또는 왼쪽 90도로 변환해주는 문제이다.
 *   - 위와 같은 경우는 상-우-하-좌 와 같이 값을 설정해서 인덱스를 +1, -1 해줌으로써 구현했다.
 * - 오류가 발생했던 부분은 뱀의 시작 몸통을 처음에 담아두지 않았던 것과, 뱀 꼬리를 잘라주는 순서와 뱀 머리 부분을 추가해주는 순서가 뒤바뀌었던 부분이다.
 * - 시뮬레이션 문제는 단계별로 문장을 잘 작성하는 것이 중요한 것 같다.
 * - 조금이라도 설계가 꼬이면 과감하게 다시 작성하자.
 */
public class Problem_3190 {

    static int N, K, L;
    static int[][] map;
    static Queue<Snake> snakes = new LinkedList<>();
    static List<Node> snake = new ArrayList<>();  // 현재 뱀 정보
    static int[] direction_row = {-1, 0, 1, 0};  // 상-우-하-좌
    static int[] direction_col = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());

        map = new int[N][N];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int appleRow = Integer.parseInt(st.nextToken());
            int appleCol = Integer.parseInt(st.nextToken());

            map[appleRow - 1][appleCol - 1] = 1;
        }

        L = Integer.parseInt(br.readLine());
        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine());
            int second = Integer.parseInt(st.nextToken());
            String dir = st.nextToken();

            snakes.add(new Snake(second, dir));
        }

        int current_dir = 1;
        int current_row = 0;
        int current_col = 0;
        snake.add(new Node(current_row, current_col));  // 시작점 뱀 몸통 추가해주기

        Snake order = snakes.poll();

        int second = 0;
        while (true) {
            int next_row = current_row + direction_row[current_dir];
            int next_col = current_col + direction_col[current_dir];

            // 좌표가 범위 밖이나, 자기 자신의 몸과 겹친다면 종료
            if (!isValid(next_row, next_col) || isMyBody(next_row, next_col)) {
                second += 1;
                break;
            }

            // 이동한 칸에 사과가 있다면
            if (map[next_row][next_col] == 1) {
                snake.add(0, new Node(next_row, next_col));
                map[next_row][next_col] = 0;

                current_row = next_row;
                current_col = next_col;
            } else {  // 이동한 칸에 사과가 없다면
                snake.add(0, new Node(next_row, next_col));
                snake.remove(snake.size() - 1);

                current_row = next_row;
                current_col = next_col;
            }

            second += 1;
            if (order != null && second == order.second) {
                if (order.dir.equals("L")) {
                    if (current_dir - 1 < 0) {
                        current_dir = 3;
                    } else {
                        current_dir -= 1;
                    }
                } else {
                    if (current_dir + 1 > 3) {
                        current_dir = 0;
                    } else {
                        current_dir += 1;
                    }
                }

                // 방향 전환 후, 다음 방향 전환 정보가 있다면 업데이트, 없다면 null
                if (snakes.size() > 0) {
                    order = snakes.poll();
                } else {
                    order = null;
                }
            }

        }

        System.out.println(second);

        /**
         * 1. 뱀이 현재 방향으로 한칸 움직였을 때 좌표 계산
         * 2. 그 좌표가 범위 밖이나, 자기 자신과 겹친다면 종료
         *      - 자기 자신 겹치는 검사 : 뱀 list 좌표들과 내가 이동할 좌표가 겹치는지 검사
         * 3. 이동한 칸에 사과가 있다면,
         *      - 뱀 list의 0번째 인덱스에 이동한 칸의 좌표 정보를 넣어준다
         *      - 해당 사과 값을 0으로 만든다.
         * 4. 이동한 칸에 사과가 없다면,
         *      - 뱀 list의 0번째 인덱스에 이동한 칸의 좌표 정보를 넣어준다
         *      - 뱀 list의 마지막 인덱스 데이터를 삭제해준다.
         * 5. 모두 이동했다면 second + 1
         * 6. 현재 second가 orderSecond와 같다면 방향 변경
         *      - L 이라면 현재 dir에서 -1
         *      - D 라면 현재 dir에서 + 1
         *      - 하지만 범위를 벗어날 수 있기에 추가 예외 처리 필수
         */
    }

    public static boolean isMyBody(int row, int col) {
        for(Node body : snake) {
            if (body.row == row && body.col == col) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < N) {
            return true;
        }
        return false;
    }
    private static void print(int second) {

        System.out.println("second = " + second);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static class Snake {
        int second;
        String dir;

        public Snake(int second, String dir) {
            this.second = second;
            this.dir = dir;
        }
    }

    static class Node {
        int row;
        int col;

        public Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
