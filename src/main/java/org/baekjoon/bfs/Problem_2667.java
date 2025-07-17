package org.baekjoon.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * BFS 알고리즘 - 백준: 2667번 (실버 1)
 * - map에서 군집을 찾아 개수를 세는 문제이다.
 * - 이전에 삼성 코테에서 비슷한 문제를 풀어본 것 같다.
 */
public class Problem_2667 {

    static int N;
    static int[][] map;
    static List<Integer> homes = new ArrayList<>();
    static int[] direction_row = {-1, 1, 0, 0};  // 상-하-좌-우
    static int[] direction_col = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(String.valueOf(line.charAt(j)));
            }
        }

        boolean[][] visited = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 집을 찾았다면 단지 조사 실행
                if (map[i][j] == 1 && !visited[i][j]) {
                    int homeCnt = bfs(i, j, visited);
                    homes.add(homeCnt);
                }
            }
        }

        // 결과 정렬 후, 출력
        Collections.sort(homes);
        System.out.println(homes.size());
        for (Integer result : homes) {
            System.out.println(result);
        }
    }

    public static int bfs(int row, int col, boolean[][] visited) {
        Queue<Node> queue = new LinkedList<>();
        int cnt = 0;

        visited[row][col] = true;
        queue.add(new Node(row, col));
        cnt += 1;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    if (map[next_row][next_col] == 1) {
                        queue.add(new Node(next_row, next_col));
                        visited[next_row][next_col] = true;
                        cnt += 1;
                    }
                }
            }
        }
        return cnt;
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < N) {
            return true;
        }
        return false;
    }

    private static void print() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j]);
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
