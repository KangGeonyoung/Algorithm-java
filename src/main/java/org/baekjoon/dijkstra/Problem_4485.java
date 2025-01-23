package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 다익스트라 알고리즘 - 백준: 4485번 (골드 4)
 */
public class Problem_4485 {

    static int N;
    static int[][] map;
    static int[][] result;
    static List<Integer> output = new ArrayList<>();
    static int[] direction_row = {-1, 1, 0, 0};  // 상 - 하 - 좌 - 우
    static int[] direction_col = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());

            if (N == 0) {
                break;
            }

            map = new int[N + 1][N + 1];
            result = new int[N + 1][N + 1];

            for (int i = 1; i <= N; i++) {
                Arrays.fill(result[i], 1250);
            }

            for (int i = 1; i <= N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            dijkstra();
            output.add(result[N][N]);
        }

        for (int i = 0; i < output.size(); i++) {
            System.out.println("Problem " + (i+1) + ": " + output.get(i));
        }
    }

    public static void dijkstra() {
        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        int start_row = 1;
        int start_col = 1;

        result[start_row][start_col] = map[start_row][start_col];
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
        if (row >= 1 && row <= N && col >= 1 && col <= N) {
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
