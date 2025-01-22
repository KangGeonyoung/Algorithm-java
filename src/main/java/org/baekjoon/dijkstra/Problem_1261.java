package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 다익스트라 알고리즘 - 백준: 1261번 (골드 4)
 * - 최단 경로에 1이라는 숫자가 있으면 더해가는 문제이다.
 * - BFS 개념이 섞여있다.
 */
public class Problem_1261 {

    static int M, N;
    static int[][] map;
    static int[][] result;
    static int[] direction_row = {0, -1, 0, 1};  // 우 -> 상 -> 좌 -> 하
    static int[] direction_col = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        map = new int[N + 1][M + 1];
        result = new int[N + 1][M + 1];

        for (int i = 1; i <= N; i++) {
            Arrays.fill(result[i], Integer.MAX_VALUE);
        }

        for (int i = 1; i <= N; i++) {
            String[] split = br.readLine().split("");
            for (int j = 1; j <= split.length; j++) {
                map[i][j] = Integer.parseInt(split[j-1]);
            }
        }

        dijkstra();

        System.out.println(result[N][M]);
    }

    public static void dijkstra() {
        int start_row = 1;
        int start_col = 1;

        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        result[start_row][start_col] = 0;
        queue.add(new Node(start_row, start_col, result[start_row][start_col]));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && current.cost + map[next_row][next_col] < result[next_row][next_col]) {
                    result[next_row][next_col] = current.cost + map[next_row][next_col];
                    queue.add(new Node(next_row, next_col, result[next_row][next_col]));
                }
            }
        }

    }

    public static boolean isValid(int row, int col) {
        if (row >= 1 && row <= N && col >= 1 && col <= M) {
            return true;
        }
        return false;
    }

    static class Node {
        int row;
        int col;
        int cost;

        public Node(int row, int col, int cost) {
            this.row = row;
            this.col = col;
            this.cost = cost;
        }
    }
}
