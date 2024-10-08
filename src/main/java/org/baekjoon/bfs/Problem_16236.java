package org.baekjoon.bfs;

import java.io.*;
import java.util.*;

/**
 * BFS 알고리즘 - 백준: 16236번 (골드 3)
 * - Queue를 사용할 때 방문 처리를 해주는데, 꼭 queue.add를 실행한 이후에 바로 방문 처리를 해줘야 한다.
 *   -> 그렇지 않으면 메모리 초과 발생하는 경우가 많음.
 * - 이 문제에서는 BFS를 상어가 물고기를 사냥하러 가는데 걸리는 시간 계산에 사용했다.
 *   - 하지만 상어가 갈 수 없는 칸이 있다는 것을 내가 간과하고 모든 칸을 갈 수 있다고 가정하고 최단경로 값을 업데이트 했다.
 *   - 못 가는 칸은 -1로 지정하고 BFS를 실행해야 한다.
 */
public class Problem_16236 {

    static int N;  // 공간의 크기
    static int[][] input;  // 공간의 상태
    static int[][] map;  // 계산에 이용할 map
    static boolean[][] visited;  // 방문 처리에 이용
    static Queue<Location> queue = new LinkedList<>();  // bfs에 이용할 큐
    static int start_row, start_col;
    static int shark_size = 2;
    static int hunt_count = 0;
    static int[] direction_row = {0, -1, 0, 1};  // 왼쪽 -> 위 -> 오른쪽 -> 아래 순서
    static int[] direction_col = {-1, 0, 1, 0};  // 왼쪽 -> 위 -> 오른쪽 -> 아래 순서

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // N 입력 받기
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        input = new int[N][N];
        map = new int[N][N];
        visited = new boolean[N][N];

        // 공간 상태 입력 받기
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                input[i][j] = Integer.parseInt(st.nextToken());
                // 초기 상어의 위치 찾기
                if (input[i][j] == 9) {
                    start_row = i;
                    start_col = j;
                }
            }
        }

        int result = 0;
        while (true) {
            // 물고기 한마리 사냥했을 때 발생한 시간
            int second = huntFish(start_row, start_col);

            // 상어의 레벨업 검사
            if (hunt_count == shark_size) {
                shark_size += 1;
                hunt_count = 0;
            }

            if (second == 0) {  // 사냥에 실패하여 탐색 종료
                break;
            }

            // 사냥에 걸린 시간을 누적으로 더해줌
            result += second;
        }

        // 결과 출력
        bw.write(Integer.toString(result));
        bw.close();
    }

    public static int huntFish(int shark_row, int shark_col) {
        // bfs 실행하기 전에 변수들 초기화
        init();

        // 상어 위치를 지정하여 최단 이동 시간을 map에 반영
        input[shark_row][shark_col] = 0;
        bfs(shark_row, shark_col);

        int minDistance = Integer.MAX_VALUE;
        int hunt_row  = -1;
        int hunt_col = -1;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] > 0 && input[i][j] > 0 && input[i][j] < shark_size) {
                    if (minDistance > map[i][j]) {
                        hunt_row = i;
                        hunt_col = j;
                        minDistance = map[i][j];
                    }
                }
            }
        }

        if (hunt_row != -1 && hunt_col != -1) {
            hunt_count += 1;  // 사냥한 물고기 수 카운트
            start_row = hunt_row;  // 사냥한 물고기 자리로 이동
            start_col = hunt_col;
            input[hunt_row][hunt_col] = 0;  // 사냥당한 물고기의 크기를 0으로 변경
            return minDistance;  // 사냥에 소요된 시간 리턴
        }

        // 엄마 상어를 요청해야 할 때
        return 0;
    }

    public static void init() {
        for (int i = 0; i < N; i++) {
            Arrays.fill(visited[i], false);
            for (int j = 0; j < N; j++) {
                // 상어보다 큰 물고기 있으면 -1로 지나갈수 없도록 구현
                if (input[i][j] > shark_size) {
                    map[i][j] = -1;
                } else {
                    map[i][j] = 0;
                }
            }
        }
        map[start_row][start_col] = 0;
    }

    // 상어 위치를 지정하여 이동 시간이 반영된 map 반환
    public static void bfs(int start_row, int start_col) {
        queue.clear();
        queue.add(new Location(start_row, start_col));
        visited[start_row][start_col] = true;

        while (!queue.isEmpty()) {
            // 현재 노드
            Location current = queue.poll();
            int current_row = current.row;
            int current_col = current.col;

            for (int i = 0; i < 4; i++) {
                int next_row = current_row + direction_row[i];
                int next_col = current_col + direction_col[i];

                // 항상 배열 범위 검사하는 함수를 먼저 발동시킬 것
                if (isPossiblePosition(next_row, next_col) && !visited[next_row][next_col] && map[next_row][next_col] != -1) {
                    // 다음 노드의 정보로 Location을 만들어 Queue에 추가
                    visited[next_row][next_col] = true;
                    queue.add(new Location(next_row, next_col));
                    map[next_row][next_col] = map[current_row][current_col] + 1;
                }
            }
        }
    }

    public static boolean isPossiblePosition(int row, int col) {
        if (row >= 0 && row < N && col >= 0 && col < N) {
            return true;
        }
        return false;
    }

    static class Location {
        int row;
        int col;

        public Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
