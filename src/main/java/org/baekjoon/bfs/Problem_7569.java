package org.baekjoon.bfs;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BFS 알고리즘 - 백준: 7569번 문제 (골드 5)
 * - BFS 문제에서는 꼭 Queue를 사용하는 게 좋을 것 같다. -> 큐 사용 안 하면 시간초과 발생함
 * - 내가 실수한 점
 *   - 해당 문제에서는 익은 토마토를 중심으로 퍼져나간다.
 *     그래서 나는 1이라는 숫자를 퍼트렸고, 퍼트릴 때마다 시간을 +1 해주는 방식으로 했다.
 *     하지만 시간 초과 문제를 해결할 수 없어 정답 코드를 보니, 다음 경로에 1이라는 숫자를 퍼트리는 방식이 아닌
 *     현재 노드 숫자에 1을 더하는 방식으로 풀었다. 그러면 최댓값이 정답이 된다.
 *   - 처음에는 3차원 배열로 해결될 거라고 생각했으나, 시간 초과로 인해 큐 사용이 필수적이라는 것을 깨닫게 되었다.
 */
public class Problem_7569 {
    static int M, N, H;
    static int map[][][];
    static int[] direction_height = {0, 0, 0, 0, 1, -1};  // 앞-뒤-우-좌-상-하
    static int[] direction_row = {-1, 1, 0, 0, 0, 0};
    static int[] direction_col = {0, 0, 1, -1, 0, 0};
    static Queue<Location> queue = new LinkedList<>();
    static int result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        map = new int[H][N][M];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < N; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < M; k++) {
                    map[i][j][k] = Integer.parseInt(st.nextToken());
                    if (map[i][j][k] == 1) {
                        queue.add(new Location(i, j, k));
                    }
                }
            }
        }

        bfs();
        checkResult();

        bw.write(Integer.toString(result));
        bw.close();
    }

    public static void bfs() {
        while (!queue.isEmpty()) {
            Location tomato = queue.poll();

            for (int i = 0; i < 6; i++) {
                int next_height = tomato.height + direction_height[i];
                int next_row = tomato.row + direction_row[i];
                int next_col = tomato.col + direction_col[i];

                if(isPossiblePosition(next_height, next_row, next_col)) {
                    if(map[next_height][next_row][next_col] == 0) {
                        queue.add(new Location(next_height, next_row, next_col));
                        map[next_height][next_row][next_col] = map[tomato.height][tomato.row][tomato.col] + 1;
                    }
                }
            }
        }
    }

    public static void checkResult() {
        int max = 0;

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M; k++) {
                    if(map[i][j][k] == 0) {
                        result = -1;
                        return;
                    }
                    max = Math.max(max, map[i][j][k]);
                }
            }
        }

        if (max == 1) {
            result = 0;
        } else {
            result = max - 1;
        }
    }

    public static boolean isPossiblePosition(int height, int row, int col) {
        if(height >= 0 && height < H && row >= 0 && row < N && col >= 0 && col < M) {
            return true;
        }
        return false;
    }

    static class Location {
        int height;
        int row;
        int col;

        public Location(int height, int row, int col) {
            this.height = height;
            this.row = row;
            this.col = col;
        }
    }
}
