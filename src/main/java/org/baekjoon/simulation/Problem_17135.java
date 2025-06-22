package org.baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 시뮬레이션 알고리즘 - 백준: 17135 (골드 3)
 * - 전형적인 복잡한 시뮬레이션 알고리즘이다.
 * - 실수한 점
 *   - 다 쓴 Queue 크기로 종료 조건을 탐색하다가 오류가 발생했다.
 *   - 항상 Queue를 사용할 때, size가 0이 아닐 때만 poll()을 사용하자. -> 오류 잘 터짐
 *   - Queue나 List는 코드 순서에 따라 정말 의미가 많이 달라지기 때문에 정신 차리고 사용하자.
 */
public class Problem_17135 {

    static int N, M, D;
    static int[][] map;
    static List<Integer> result = new ArrayList<>();  // 결과값 리스트

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        map = new int[N+1][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0);
        System.out.println(Collections.max(result));

        /**
         * 1. N+1 행에서 M열까지 3개의 조합을 만들어 궁수를 배치한다.
         *      - 궁수 배치가 3개가 되었을 때, 게임 진행 -> 제거할 수 있는 적의 수를 리스트에 추가해준다.
         *      - 게임을 진행하기 전, 적Map을 복제해서 진행해야 한다.
         * 2. 게임 진행
         *      1. 한턴에 3명의 궁수가 동시에 적을 골라서 큐에 각각 담는다.
         *          - 궁수가 적을 고르는 법
         *              1. 1명의 궁수 좌표와 모든 적들의 좌표 거리를 계산한 값과, 적들의 좌표를 저장해서 우선순위 큐에 넣는다.
         *              2. 우선순위 조건 : Dist(서로의 거리)가 작아야 하고, 중복된다면 col이 가장 작은 적이 우선순위
         *              3. 우선순위 큐 주의점 : 우선순위에서 선정된 첫 번째 적의 Dist가 D이하인지 확인해야 한다.
         *      2. 큐에서 3명의 궁수가 고른 적들을 하나씩 공격한다.
         *          - 같은 적이 여러 번 공격당할 수 있다.
         *          - 공격 받은 적은 적Map에서 사라진다. (=0이 된다) -> 적을 0으로 만들 때만 제거 수를 count +1 해준다.
         *      3. 모든 적이 아래로 한칸 이동한다.
         *          - 적Map을 업데이트한다.
         *          - 성이 있는 칸(=적Map의 범위 밖)으로 이동할 시 게임에서 제외된다.
         *      4. 모든 적이 적Map에서 제외됐는지 검사한다.
         *          - 제외됐다면, 게임 종료 후 제거 수를 전역 변수 result 리스트에 추가해준다.
         *          - 그렇지 않다면, 2.1로 돌아간다.
         * 3. result 리스트에서 제일 큰 값을 찾아 출력한다.
         */
    }

    public static void startGame(int[][] map) {
        int removeCnt = 0;
        Queue<Enemy> enemies = new LinkedList<>();
        Queue<Enemy> remains = new LinkedList<>();

        while (true) {
            enemies.clear();
            remains.clear();

            // 1. 3명의 궁수가 적을 고른다.
            for (int i = 0; i < N + 1; i++) {
                for (int j = 0; j < M; j++) {
                    // 궁수 발견 시, 궁수가 적을 골라오게 함
                    if (map[i][j] == 2) {
                        Enemy enemy = findEnemy(i, j, map);
                        if (enemy != null) {
                            enemies.add(enemy);
                        }
                    }
                }
            }

            // 2. 고른 적들을 공격한다.
            while (!enemies.isEmpty()) {
                Enemy enemy = enemies.poll();

                // 적이 map에 살아 있는 경우
                if (map[enemy.row][enemy.col] == 1) {
                    map[enemy.row][enemy.col] = 0;
                    removeCnt += 1;
                }
            }

            // 3. 남은 적들이 아래로 한칸 이동한다.

            // 현재 남아있는 적들을 찾아서 큐에 추가해주기
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (map[i][j] == 1) {
                        remains.add(new Enemy(i, j));
                    }
                }
            }

            // 종료 조건 : 모든 적이 map에서 제거됐다면 break
            if (remains.size() == 0) {
                break;
            }

            // 성 부분을 제외한 map 초기화
            for (int i = 0; i < N; i++) {
                Arrays.fill(map[i], 0);
            }

            // 적들 아래로 한칸 전진
            while (!remains.isEmpty()) {
                Enemy enemy = remains.poll();
                if (enemy.row + 1 != N) {
                    map[enemy.row + 1][enemy.col] = 1;
                }
            }
        }

        result.add(removeCnt);
    }

    public static Enemy findEnemy(int row, int col, int[][] map) {
        // 우선순위 큐 커스텀
        PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>(){
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.dist == o2.dist) {
                    return o1.col - o2.col;
                }
                return o1.dist - o2.dist;
            }
        });

        // 적과의 거리 정보 저장
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                // 적을 찾았다면, 적과의 거리와 좌표를 객체로 만들어 우선순위 큐에 저장
                if (map[i][j] == 1) {
                    int dist = Math.abs(row - i) + Math.abs(col - j);
                    queue.add(new Node(dist, i, j));
                }
            }
        }

        // 우선순위 큐에서 가장 앞에 있는 요소 조회
        if (queue.size() == 0) {
            return null;
        }

        Node selected = queue.poll();
        if (selected.dist <= D) {
            Enemy enemy = new Enemy(selected.row, selected.col);
            return enemy;
        }
        return null;
    }

    public static void dfs(int index, int depth) {
        // 궁수 3명 배치했을 경우
        if (depth == 3) {
            // map 복제 후, 게임 진행
            int[][] copy = copyMap();
            startGame(copy);
            return;
        }

        for (int i = index; i < M; i++) {
            map[N][i] = 2;  // 궁수 = 2
            dfs(i + 1, depth + 1);
            map[N][i] = 0;
        }
    }

    public static int[][] copyMap() {
        int[][] copy = new int[N+1][M];
        for (int i = 0; i < N + 1; i++) {
            copy[i] = map[i].clone();
        }

        return copy;
    }

    private static void print(int[][] map) {
        for (int i = 0; i < N+1; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static class Enemy {
        int row;
        int col;

        public Enemy(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    static class Node {
        int dist;
        int row;
        int col;

        public Node(int dist, int row, int col) {
            this.dist = dist;
            this.row = row;
            this.col = col;
        }
    }
}
