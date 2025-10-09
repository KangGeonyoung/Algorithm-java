package org.baekjoon.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * BFS 알고리즘 - 백준: 2589번 (골드 5)
 * - 조건에 맞는 지점을 시작으로 하여 bfs를 여러 번 돌리는 문제가 꽤 유행인듯 하다.
 */
public class Problem_2589 {

    static int row, col;
    static char[][] map;

    static int[] direction_row = {-1, 1, 0, 0};  // 상하좌우
    static int[] direction_col = {0, 0, -1, 1};
    static PriorityQueue<Integer> result = new PriorityQueue<>(Collections.reverseOrder());  // 가장 오래 걸리는 시간이 높은 우선순위

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        row = Integer.parseInt(st.nextToken());
        col = Integer.parseInt(st.nextToken());

        // map 입력 받기
        map = new char[row][col];
        for (int i = 0; i < row; i++) {
            String line = br.readLine();
            for (int j = 0; j < line.length(); j++) {
                char digit = line.charAt(j);
                map[i][j] = digit;
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 육지일 때만 bfs 체크 - 모든 육지 출발점을 비교하기 위함
                if (map[i][j] == 'L') {
                    char[][] newMap = copyMap();
                    result.add(bfs(i, j, newMap));
                }
            }
        }

        // 결과 출력
        if (result.size() > 0) {
            Integer resultTime = result.poll();
            System.out.println(resultTime);
        } else {
            System.out.println(0);
        }
    }

    // 주어진 시작점을 기준으로 육지 내의 최장 거리(=시간) 계산
    public static int bfs(int startRow, int startCol, char[][] map) {
        int max = 0;

        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[row][col];
        visited[startRow][startCol] = true;
        queue.add(new Node(startRow, startCol, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    // 새로운 육지를 발견한 경우
                    if (map[next_row][next_col] == 'L') {
                        visited[next_row][next_col] = true;
                        queue.add(new Node(next_row, next_col, current.time + 1));
                        max = Math.max(max, current.time + 1);
                    }
                }
            }
        }

        return max;
    }

    public static boolean isValid(int r, int c) {
        if (r >= 0 && c >= 0 && r < row && c < col) {
            return true;
        }
        return false;
    }

    public static char[][] copyMap() {
        char[][] newMap = new char[row][col];
        for (int i = 0; i < row; i++) {
            newMap[i] = map[i].clone();
        }

        return newMap;
    }

    private static void print() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static class Node {
        int row;
        int col;
        int time;

        public Node(int row, int col, int time) {
            this.row = row;
            this.col = col;
            this.time = time;
        }
    }
}
