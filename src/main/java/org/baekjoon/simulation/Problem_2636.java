package org.baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 시뮬레이션 알고리즘 - 백준: 2636 (골드 4)
 * - 시뮬레이션은 규칙을 찾아야 한다.
 * - 예외 케이스를 두지 않고 공통된 규칙을 찾아보자.
 * - 이럴 때는 저렇게 하고, 저럴 때는 이렇게 하는 방식으로 구현하면 보통 틀린다.
 *   -> 하나의 공통된 흐름을 찾아야 한다.
 */
public class Problem_2636 {

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

        // 1. 0 또는 2인 곳을 모두 검사
        //  - 상하좌우를 돌면서 해당 방향으로 끝까지 가는데, 만약 1이 나온다면 다음 방향 탐색, 하나라도 map 범위 밖을 나간다면 공기로 판정(=0)
        //  - 상하좌우 방향 끝에 모두 1이 있다면 치즈 내 구멍으로 판정(=2)
        // 2. 치즈(=1) 검사
        //  - 1인 곳을 하나씩 검사하는데, 치즈의 상하좌우를 보면서 공기가 하나라도 있으면 녹을 치즈 대상 리스트에 좌표 추가
        // 3. 녹을 치즈 녹이기
        //  - 녹는 치즈 큐에서 하나씩 꺼내서 치즈를 녹여 0으로 만들고, 카운팅 해주기
        //  - 모두 녹였다면 카운팅된 숫자를 result 리스트에 추가
        // 4. 1-3번 과정을 반복
        //  - 2번 과정에서 녹을치즈 대상 큐의 크기가 0이라면 종료
        // 5. 종료 시
        //  - 모두 녹는데 걸린 시간 = result 리스트의 개수
        //  - 모두 녹기 전 남아있던 치즈조각 개수 = result의 마지막 요소

        while (true) {
            // 녹을 치즈 찾기
            meltingCheese.clear();
            findMetingCheese(0, 0);

            // 치즈가 다 녹았을 경우, 탐색 종료
            if (meltingCheese.size() == 0) {
                break;
            }

            // 녹을 치즈 녹이기
            int meltCount = 0;
            while (!meltingCheese.isEmpty()) {
                Node current = meltingCheese.poll();
                map[current.row][current.col] = 0;
                meltCount += 1;
            }
            result.add(meltCount);
        }

        System.out.println(result.size());
        System.out.println(result.get(result.size() - 1));
    }


    public static void findMetingCheese(int row, int col) {
        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];
        visited[row][col] = true;
        queue.add(new Node(row, col));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    // 치즈를 찾았다면 멈추기
                    if (map[next_row][next_col] == 1) {
                        meltingCheese.add(new Node(next_row, next_col));
                        visited[next_row][next_col] = true;
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

    private static void print() {
        System.out.println("---------------------------");
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
