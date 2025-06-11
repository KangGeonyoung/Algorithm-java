package org.baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 시뮬레이션 알고리즘 - 백준: 2638 (골드 3)
 * - bfs에서 탐색 종료 조건들을 잘 변형해 보자.
 */
public class Problem_2638 {

    static int N, M;
    static int[][] map;
    static int[] direction_row = {-1, 1, 0, 0};  // 상-하-좌-우
    static int[] direction_col = {0, 0, -1, 1};
    static Queue<Node> meltingCheese = new LinkedList<>();
    static List<Integer> result = new ArrayList<>();

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

        while (true) {
            // 1번
            bfs(0, 0);

            // 2번
            meltingCheese.clear();
            checkMeltingCheese();

            // 종료 조건
            if (meltingCheese.size() == 0) {
                break;
            }

            // 3번
            int meltingCount = 0;
            while (!meltingCheese.isEmpty()) {
                Node current = meltingCheese.poll();
                map[current.row][current.col] = 0;
                meltingCount += 1;
            }
            result.add(meltingCount);

            // 4번
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (map[i][j] >= 1) {
                        map[i][j] = 1;
                    }
                }
            }
        }

        System.out.println(result.size());

        // 1. bfs로 공기들을 따라가면서 치즈를 만나게 되면 해당 치즈의 숫자를 1 더해주고 종료
        // 2. map에서 치즈인 곳(숫자가 1이상인 곳)을 확인하면서 녹을 치즈(숫자가 3이상인 곳) 검사
        //  - 녹을 치즈가 없다면 탐색 종료
        // 3. 녹을 치즈 녹이기
        //  - 녹인 치즈를 카운팅해서 result에 추가
        // 4. 나머지 치즈 숫자 1로 정상화
        // 5. 1-4번 반복
        // 종료: result의 크기 출력
    }

    public static void checkMeltingCheese() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] >= 3) {
                    meltingCheese.add(new Node(i, j));
                }
            }
        }
    }

    public static void bfs(int row, int col) {
        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];
        queue.add(new Node(row, col));
        visited[row][col] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    if (map[next_row][next_col] >= 1) {
                        map[next_row][next_col] += 1;
                    } else {
                        queue.add(new Node(next_row, next_col));
                        visited[next_row][next_col] = true;
                    }
                }
            }
        }
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < M) {
            return true;
        }
        return false;
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
