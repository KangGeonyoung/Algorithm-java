package org.baekjoon.samsung;

import java.util.*;

/**
 * 삼성 2024 하반기 오전 1번 문제 : 미지의 공간 탈출
 * - https://www.codetree.ai/ko/frequent-problems/problems/escape-unknown-space/description?introductionSetId=&bookmarkId=
 */
public class Problem_2024_2 {
    static int N, M, F;

    static int[][] space;  // 미지의 공간 평면도
    static int[][][] time_map;  // 시간의 벽 단면도, 동(0)-서(1)-남(2)-북(3)-윗면(4)
    static int[][] fire_map;  // 이상현상 정보 맵
    static List<Fire> fires = new ArrayList<>();  // 이상현상 정보


    static int start_row_3d, start_col_3d;  // 3d의 시작 지점
    static int end_row_3d, end_col_3d;  // 3d의 도착 지점
    static int up_dest_row, up_dest_col;  // 윗면 시간의 벽에서의 탈출 지점

    static int dir_3d_to_2d;  // 3d에서 2d로 탈출한 방향

    static int start_row_2d, start_col_2d;  // 2d의 시작 지점
    static int end_row_2d, end_col_2d;  // 2d의 도착 지점

    static int base_row, base_col;  // 시간의 벽 base 좌표
    static boolean baseFlag = false;


    static int[] direction_row = {0, 0, 1, -1};  // 동-서-남-북
    static int[] direction_col = {1, -1, 0, 0};

    static int time = 0;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();
        F = sc.nextInt();

        // 미지의 공간 평면도 정보
        space = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                space[i][j] = sc.nextInt();
                if (space[i][j] == 4) {  // 탈출 위치
                    end_row_2d = i;
                    end_col_2d = j;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (space[i][j] == 3) {  // 시간의 벽이 있는 곳
                    // 시간의 벽 base 좌표 찾기 (첫 번째 벽이 base 좌표)
                    if (!baseFlag) {
                        base_row = i;
                        base_col = j;
                        baseFlag = true;
                    }

                    // 4가지 방향에 탈출 지점이 있는지 확인
                    for (int k = 0; k < 4; k++) {
                        int next_row = i + direction_row[k];
                        int next_col = j + direction_col[k];

                        if (space[next_row][next_col] == 0) {  // 탈출 지점 찾은 경우

                            up_dest_row = i;
                            up_dest_col = j;

                            start_row_2d = next_row;
                            start_col_2d = next_col;
                            dir_3d_to_2d = k;

                            // 3d의 탈출 지점 찾기
                            if (dir_3d_to_2d == 0) {  // 동
                                end_row_3d = M - 1;
                                end_col_3d = (M - 1) - (i - base_row);
                            }

                            if (dir_3d_to_2d == 1) {  // 서
                                end_row_3d = M - 1;
                                end_col_3d = i - base_row;
                            }

                            if (dir_3d_to_2d == 2) {  // 남
                                end_row_3d = M - 1;
                                end_col_3d = j - base_col;
                            }

                            if (dir_3d_to_2d == 3) {  // 북
                                end_row_3d = M - 1;
                                end_col_3d = (M - 1) - (j - base_col);
                            }
                        }
                    }
                }
            }
        }

        // 시간의 벽 단면도 정보
        time_map = new int[5][M][M];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < M; k++) {
                    time_map[i][j][k] = sc.nextInt();
                    if (time_map[i][j][k] == 2) {  // 초기 시작 위치
                        start_row_3d = j;
                        start_col_3d = k;
                        time_map[i][j][k] = 1;
                    }
                }
            }
        }

        // 이상현상 정보
        fire_map = new int[N][N];
        for (int i = 0; i < F; i++) {
            int fire_row = sc.nextInt();
            int fire_col = sc.nextInt();
            int fire_dir = sc.nextInt();
            int fire_time = sc.nextInt();
            fires.add(new Fire(fire_row, fire_col, fire_dir, fire_time));

            space[fire_row][fire_col] = 1;
        }

        // 3d에서 탈출 시작
        bfs_3d();
        time = time_map[dir_3d_to_2d][end_row_3d][end_col_3d];

        // 이상현상 확산시킨 맵
        makeFireMap();

        // 2d 초기 세팅
        space[start_row_2d][start_col_2d] = time;

        // 2d 탈출 시작
        bfs_2d();

        // 결과 출력
        if (space[end_row_2d][end_col_2d] == 4) {
            System.out.println(-1);
        } else {
            System.out.println(space[end_row_2d][end_col_2d]);
        }
    }

    static public void bfs_2d() {
        Queue<Node_2d> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];
        queue.add(new Node_2d(start_row_2d, start_col_2d));
        visited[start_row_2d][start_col_2d] = true;

        while (!queue.isEmpty()) {
            Node_2d current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if(isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    if (space[next_row][next_col] == 0 || space[next_row][next_col] == 4) {
                        if (fire_map[next_row][next_col] > 0) {
                            if (fire_map[next_row][next_col] - 1 > space[current.row][current.col]) {
                                queue.add(new Node_2d(next_row, next_col));
                                visited[next_row][next_col] = true;
                                space[next_row][next_col] = space[current.row][current.col] + 1;
                            }
                        } else {
                            queue.add(new Node_2d(next_row, next_col));
                            visited[next_row][next_col] = true;
                            space[next_row][next_col] = space[current.row][current.col] + 1;
                        }
                    }
                }

            }
        }
    }

    public static void makeFireMap() {
        for (Fire fire : fires) {
            while (true) {
                int next_row = fire.row + direction_row[fire.dir];
                int next_col = fire.col + direction_col[fire.dir];

                if(isValid(next_row, next_col)) {
                    if (space[next_row][next_col] == 0) {
                        fire_map[next_row][next_col] = fire_map[fire.row][fire.col] + fire.v;
                        fire.row = fire.row + direction_row[fire.dir];
                        fire.col = fire.col + direction_col[fire.dir];
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < N) {
            return true;
        }
        return false;
    }

    // 3d bfs
    static public void bfs_3d() {
        Map<Integer, Integer> left = new HashMap<>();
        left.put(2, 1);
        left.put(1, 3);
        left.put(3, 0);
        left.put(0, 2);

        Map<Integer, Integer> right = new HashMap<>();
        right.put(2, 0);
        right.put(0, 3);
        right.put(3, 1);
        right.put(1, 2);

        boolean[][][] visited = new boolean[5][M][M];
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(4, start_row_3d, start_col_3d));
        visited[4][start_row_3d][start_col_3d] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.dir == dir_3d_to_2d && current.row == end_row_3d && current.col == end_col_3d) {
                return;
            }

            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];
                int next_dir = current.dir;

                if (next_row < 0) {  // 위쪽 이탈
                    if (current.dir == 0) {  // 동
                        next_dir = 4;
                        next_row = (M - 1) - current.col;
                        next_col = M - 1;
                    }

                    if (current.dir == 1) {  // 서
                        next_dir = 4;
                        next_row = current.col;
                        next_col = 0;
                    }

                    if (current.dir == 2) {  // 남
                        next_dir = 4;
                        next_row = M - 1;
                        next_col = current.col;
                    }

                    if (current.dir == 3) {  // 북
                        next_dir = 4;
                        next_row = 0;
                        next_col = (M - 1) - current.col;
                    }

                    if (current.dir == 4) {  // 윗면
                        next_dir = 3;
                        next_row = 0;
                        next_col = (M - 1) - current.col;
                    }
                }

                if (next_row >= M) {  // 아래쪽 이탈
                    if (current.dir == 4) {  // 윗면
                        next_dir = 2;
                        next_row = 0;
                        next_col = current.col;
                    } else {
                        continue;
                    }
                }

                if (next_col < 0) {  // 왼쪽 이탈
                    if (current.dir == 4) {  // 윗면
                        next_dir = 1;
                        next_row = 0;
                        next_col = current.row;
                    } else {  // 나머지 전부
                        next_dir = left.get(current.dir);
                        next_row = current.row;
                        next_col = M - 1;
                    }
                }

                if (next_col >= M) {  // 오른쪽 이탈
                    if (current.dir == 4) {  // 윗면
                        next_dir = 0;
                        next_row = 0;
                        next_col = (M - 1) - current.row;
                    } else {  // 나머지 전부
                        next_dir = right.get(current.dir);
                        next_row = current.row;
                        next_col = 0;
                    }
                }

                if (!visited[next_dir][next_row][next_col] && time_map[next_dir][next_row][next_col] == 0) {
                    queue.add(new Node(next_dir, next_row, next_col));
                    visited[next_dir][next_row][next_col]= true;

                    time_map[next_dir][next_row][next_col] = time_map[current.dir][current.row][current.col] + 1;
                }
            }
        }
    }

    // 이상현상 정보
    static class Fire {
        int row;
        int col;
        int dir;
        int v;

        public Fire(int row, int col, int dir, int v) {
            this.row = row;
            this.col = col;
            this.dir = dir;
            this.v = v;
        }
    }

    static class Node {
        int dir;
        int row;
        int col;

        public Node(int dir, int row, int col) {
            this.dir = dir;
            this.row = row;
            this.col = col;
        }
    }

    static class Node_2d {
        int row;
        int col;

        public Node_2d(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
