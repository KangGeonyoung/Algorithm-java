package org.baekjoon.breadth_first_search;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BFS 알고리즘 - 백준: 2178번
 * 매우 도움이 되었던 블로그
 * https://innovation123.tistory.com/72
 */
public class Problem_2178 {

    static int N, M;
    static int[][] maze;  // 실제 input
    static int[][] map;  // 최소 거리를 계산할 map
    static boolean[][] visited;  // 방문 확인
    static Queue<Location> queue = new LinkedList<>();
    static int[] direction_row = {0, -1, 0, 1};  // 왼쪽 -> 위 -> 오른쪽 -> 아래 순서로 탐색
    static int[] direction_col = {-1, 0, 1, 0};  // 왼쪽 -> 위 -> 오른쪽 -> 아래 순서로 탐색
    static int result = 0;  // 결과 값


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        // N, M input 읽기
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // N * M 배열 선언
        maze = new int[N][M];
        visited = new boolean[N][M];
        map = new int[N][M];

        // 미로의 정보 input 읽기
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                // 숫자 아스키코드 값에 0의 아스키코드 값(=48)을 빼주면 본래의 숫자가 나온다.
                maze[i][j] = line.charAt(j) - '0';
            }
        }

        // maze로 map 커스텀
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (maze[i][j] == 1) {
                    map[i][j] = 0;
                } else {
                    map[i][j] = -1;
                }
            }
        }

        // 시작 지점
        int start_x = 0;
        int start_y = 0;

        // bfs 실행
        bfs(start_x, start_y);
        result = map[N-1][M-1];

        // 결과 출력
        bw.write(Integer.toString(result));
        bw.close();
    }

    public static void bfs(int start_row, int start_col) {

        // 시작점
        Location start = new Location(start_row, start_col);
        queue.offer(start);
        map[start_row][start_col] += 1;

        while (!queue.isEmpty()) {
            // 현재 노드
            Location current = queue.poll();
            int current_row = current.row;
            int current_col = current.col;

            // 현재 노드 방문 처리
            visited[current_row][current_col] = true;

            // 현재 노드의 인접 노드 탐색
            for (int i = 0; i < 4; i++) {
                int next_row = current_row + direction_row[i];
                int next_col = current_col + direction_col[i];

                // 배열 속에 해당하는 위치인지 & 방문하지 않은 곳인지 검사
                if (isPossiblePosition(next_row, next_col) && !visited[next_row][next_col]) {
                    if (map[next_row][next_col] == 0) {
                        Location next = new Location(next_row, next_col);
                        queue.offer(next);
                        map[next_row][next_col] = map[current_row][current_col] + 1;
                    }
                }
            }
        }
    }

    public static boolean isPossiblePosition(int row, int col) {
        if (row >= 0 && row < N && col >= 0 && col < M) {
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
