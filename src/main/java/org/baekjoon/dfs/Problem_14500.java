package org.baekjoon.dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * DFS 알고리즘 - 백준: 14500번 (골드 4)
 * - 꽤나 까다로웠던 DFS
 * - 상하좌우 방향을 이용하여 조합을 만들어내는 DFS 문제이다.
 * - 하지만 만들어내지 못하는 무늬가 있어서 해당 무늬는 수동으로 하드코딩을 해줘야 했다.
 * - 내가 실수한 점
 *   - dfs를 너무 복잡하게 구현해서 시간초과가 발생했다.
 *   - dfs에서는 visited는 전역으로 하나만 선언해서 사용해도 충분하다.
 *   - dfs 내에서 dfs를 재귀호출 할 때는 항상 앞뒤로 visited 처리를 해줘야 한다.
 *   - 예외가 발생했을 때는 하드코딩도 하나의 방법
 */
public class Problem_14500 {

    static int N, M;
    static int[][] map;
    static int[] direction_row = {-1, 1, 0, 0};  // 상하좌우
    static int[] direction_col = {0, 0, -1, 1};
    static int max = 0;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 모든 시작점에서 4개 블록 무늬 만들어서 max 업데이트 하기
        visited = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                visited[i][j] = true;
                dfs(i, j, 1, map[i][j]);
                visited[i][j] = false;
                checkT(i, j);
            }
        }

        System.out.println(max);
    }

    public static void checkT(int centerRow, int centerCol) {
        int center = map[centerRow][centerCol];

        for (int i = 0; i < 4; i++) {
            int sum = center;

            for (int j = 0; j < 4; j++) {
                // ㅏ, ㅓ, ㅗ, ㅜ 이기 때문에 방향을 한번씩 제외시켜야 함
                if (i == j) {
                    continue;
                }

                int next_row = centerRow + direction_row[j];
                int next_col = centerCol + direction_col[j];

                if (isValid(next_row, next_col)) {
                    sum += map[next_row][next_col];
                } else {
                    break;
                }
            }

            if (sum != center) {
                max = Math.max(max, sum);
            }
        }
    }

    public static void dfs(int startRow, int startCol, int depth, int sum) {
        if (depth == 4) {
            max = Math.max(max, sum);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int next_row = startRow + direction_row[i];
            int next_col = startCol + direction_col[i];

            if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                visited[next_row][next_col] = true;
                dfs(next_row, next_col, depth + 1, sum + map[next_row][next_col]);
                visited[next_row][next_col] = false;
            }
        }
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < M) {
            return true;
        }
        return false;
    }

    private static void printMap() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
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
