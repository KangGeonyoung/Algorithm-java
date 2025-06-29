package org.baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 시뮬레이션 알고리즘 - 백준: 17144 (골드 4)
 * - 흥미로운 시뮬레이션 문제이다.
 * - 반시계 방향과 시계 방향을 구현해야 하는 문제이다.
 * - 내가 실수한 점
 *   - 미세먼지를 동시에 퍼트려야 하는 조건이 있다. 그래서 그 시점의 먼지 양을 사용해야 한다.
 *   - 확산시킨 먼지 양을 업데이트 시킬 때의 시점은 현재 시점의 map을 사용해야 한다.
 *     -> 즉, 퍼트릴 때는 과거의 map 데이터를, 확산된 먼지 양을 업데이트 시킬 때는 현재의 map 데이터를 사용해야 한다.
 */
public class Problem_17144 {

    static int R, C, T;
    static int[][] map;
    static Queue<Dust> dusts = new LinkedList<>();  // 먼지들 큐
    static Queue<Cleaner> cleaners = new LinkedList<>();  // 청소기 큐

    static int[] direction_row = {0, -1, 0, 1};  // 우-상-좌-하
    static int[] direction_col = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        map = new int[R][C];
        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < C; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());

                // 미세먼지를 발견했을 경우, 먼지들 큐에 넣어주기
                if (map[i][j] > 0) {
                    dusts.add(new Dust(i, j, map[i][j]));
                }

                // 청소기를 발견했을 경우
                if (map[i][j] == -1) {
                    cleaners.add(new Cleaner(i, j));
                }
            }
        }

        for (int i = 0; i < T; i++) {
            // 미세먼지 확산
            spreadDust();

            // 공기청정기 가동
            startCleaner();

            // 미세먼지 큐 초기화
            init();
        }

        // 남은 미세먼지 출력
        int result = calculate();
        System.out.println(result);

        /**
         * 1. 모든 칸에 있는 미세먼지 확산
         *      미세먼지 확산
         *          1. 상하좌우 방향으로 확산하는데, 해당 방향에 공기청정기가 있거나, 범위 밖이라면 확산하지 않는다.
         *          2. 확산되는 양 = (현재 칸의 미세먼지 양 / 5)
         *              - 주의점 : 확산양을 먼저 계산한 후에, 4개의 방향으로 퍼트려야 한다. 퍼트릴 때마다 계산해주면 안된다.
         *          3. 확산 후 현재 칸에 미세먼지 양을 업데이트 해준다.
         *              - 남은 미세먼지 양 = 원래 양 - 확산되는 양 * 퍼트린 방향의 개수
         *
         * 2. 공기청정기를 작동하여 바람의 경로에 있는 데이터를 한칸씩 밀어낸다.
         *      공기청정기 작동
         *          1. 공기청정기에서 바람이 두 갈래로 나온다.
         *              - 윗칸 : 반시계 방향
         *              - 아랫칸 : 시계 방향
         *          2. 바람의 경로 리스트를 하나 생성한다.
         *          3. 바람의 경로 리스트에 바람의 갈래를 따라 값들을 추가해준다.
         *              - 경로에 숫자가 없다면 0을 추가해준다.
         *          4. 공기청정기가 바람의 경로를 한칸 밀기 때문에 바람의 경로 리스트의 0번 인덱스에 숫자 0을 추가해준다.
         *          5. 바람의 경로를 따라가며 리스트의 0번 인덱스 값부터 칸을 하나씩 채워준다.
         *              - 주의점 : 리스트의 길이는 공기청정기의 바람으로 인해 한칸 추가되었기 때문에 무조건 마지막 리스트의 값이 삭제된다.
         * 3. 1~2번을 T번 반복한다.
         */
    }

    public static void init() {
        // 미세먼지 큐 재설정
        dusts.clear();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                // 미세먼지를 발견했을 경우, 먼지들 큐에 넣어주기
                if (map[i][j] > 0) {
                    dusts.add(new Dust(i, j, map[i][j]));
                }
            }
        }
    }

    public static int calculate() {
        int sum = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] > 0) {
                    sum += map[i][j];
                }
            }
        }
        return sum;
    }

    public static void startCleaner() {
        Cleaner upCleaner = cleaners.poll();
        Cleaner downCleaner = cleaners.poll();

        // 공기청정기 바람 한칸 밀어넣기
        startWind(upCleaner, 0);
        startWind(downCleaner, 1);

        // 청소기 큐 재설정
        cleaners.clear();
        cleaners.add(upCleaner);
        cleaners.add(downCleaner);
    }

    public static void startWind(Cleaner cleaner, int dir) {
        int current_dir = 0;  // 오른쪽 방향부터 시작
        List<Integer> winds = new ArrayList<>();

        int current_row = cleaner.row;
        int current_col = cleaner.col;

        // 바람의 갈래에 따라 값들을 리스트에 추가
        while (true) {
            int next_row = current_row + direction_row[current_dir];
            int next_col = current_col + direction_col[current_dir];

            if (isValid(next_row, next_col)) {
                if (map[next_row][next_col] == -1) {
                    break;
                }
                // 바람의 갈래를 따라 값을 리스트에 추가
                winds.add(map[next_row][next_col]);
                current_row = next_row;
                current_col = next_col;
            } else {
                // 반시계
                if (dir == 0) {
                    current_dir += 1;
                } else {  // 시계
                    current_dir -= 1;
                    if (current_dir == -1) {
                        current_dir = 3;
                    }
                }
            }
        }

        // 바람의 경로 리스트 0번 인덱스에 0 추가
        winds.add(0, 0);

        // 바람의 경로를 따라가며 리스트의 0번 인덱스 값부터 칸을 하나씩 채워준다.
        current_row = cleaner.row;
        current_col = cleaner.col;
        current_dir = 0;
        int windIdx = 0;

        while (true) {
            int next_row = current_row + direction_row[current_dir];
            int next_col = current_col + direction_col[current_dir];

            if (isValid(next_row, next_col)) {
                if (map[next_row][next_col] == -1) {
                    break;
                }
                // 바람의 갈래를 따라 값을 리스트에 추가
                map[next_row][next_col] = winds.get(windIdx);
                windIdx += 1;

                current_row = next_row;
                current_col = next_col;
            } else {
                // 반시계
                if (dir == 0) {
                    current_dir += 1;
                } else {  // 시계
                    current_dir -= 1;
                    if (current_dir == -1) {
                        current_dir = 3;
                    }
                }
            }
        }
    }

    public static void spreadDust() {
        while (!dusts.isEmpty()) {
            Dust dust = dusts.poll();
            int spreadAmount = dust.amount / 5;
            int spreadCnt = 0;

            // 4개 방향 중에 가능한 방향으로 퍼트리기
            for (int i = 0; i < 4; i++) {
                int next_row = dust.row + direction_row[i];
                int next_col = dust.col + direction_col[i];

                // 범위 안이어야 하고, 공기청정기가 없어야 한다.
                if (isValid(next_row, next_col) && map[next_row][next_col] != -1) {
                    map[next_row][next_col] += spreadAmount;
                    spreadCnt += 1;
                }
            }

            // 확산시키고 남은 미세먼지 양 업데이트
            map[dust.row][dust.col] = map[dust.row][dust.col] - spreadAmount * spreadCnt;
        }
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < R && col < C) {
            return true;
        }
        return false;
    }

    static class Dust {
        int row;
        int col;
        int amount;

        public Dust(int row, int col, int amount) {
            this.row = row;
            this.col = col;
            this.amount = amount;
        }
    }

    static class Cleaner {
        int row;
        int col;

        public Cleaner(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static void print() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
