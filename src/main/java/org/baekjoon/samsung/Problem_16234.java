package org.baekjoon.samsung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Problem_16234 {
    // 16234 - 인구이동

    static int N, L, R;

    static int[][] map;
    static boolean[][] visited;

    static int[] direction_x = {0, -1, 0, 1};  // 우상좌하
    static int[] direction_y = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        // 나라별 인구수 입력 받기
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int result = 0;  // 인구 이동 횟수
        visited = new boolean[N][N];

        while (true) {
            if (movePeople() == 0) {
                break;
            } else {
                result += 1;
            }
        }

        bw.write(Integer.toString(result));
        bw.close();
    }

    // unionCount가 2번째 for문 안으로 들어가야 하는거 아닌가..?
    // 그리고 최상단에는 unionCount가 아닌 인구이동 count 변수를 선언해서 인구가 이동했을 때마다 +1해주고
    // 마지막에 return 해주는 방식으로 해보자
    public static int movePeople() {
        int moveCount = 0;  // 인구 이동 횟수

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    visited[i][j] = true;
                    int unionCount = 0;  // 연합국 개수
                    Location location = new Location(i, j);

                    Queue<Location> queue = new LinkedList<>();
                    queue.add(location);

                    List<Location> unionList = new ArrayList<>();  // 연합국 리스트
                    unionList.add(location);

                    int unionSum = map[location.x][location.y];

                    while (!queue.isEmpty()) {  // 연합 검사
                        Location current = queue.poll();

                        for (int m = 0; m < 4; m++) {  // 상하좌우로 국경 검사
                            int nextX = current.x + direction_x[m];
                            int nextY = current.y + direction_y[m];

                            if (nextX >= 0 && nextY >= 0 && nextX < N && nextY < N) {  // 배열 범위 내에 있는지 검사
                                if (!visited[nextX][nextY] && isOpen(current.x, current.y, nextX, nextY)) {  // 국경이 열리는 조건인지 검사
                                    Location union = new Location(nextX, nextY);
                                    queue.add(union);
                                    unionList.add(union);
                                    visited[nextX][nextY] = true;
                                    unionSum += map[nextX][nextY];
                                    unionCount += 1;
                                }
                            }
                        }
                    }

                    if (unionCount > 0) {
                        int changedPopulation = unionSum / unionList.size();  // 연합의 조정된 인구 수

                        for (int k = 0; k < unionList.size(); k++) {  // 연합 나라에 조정된 인구 수로 업데이트
                            Location updateLocation = unionList.get(k);
                            map[updateLocation.x][updateLocation.y] = changedPopulation;
                        }
                        moveCount += 1;
                    }

                }
            }
        }

        // 다시 인구 조정하기 위해 visited 배열을 false로 초기화
        for (int i = 0; i < N; i++) {
            Arrays.fill(visited[i], false);
        }

        return moveCount;
    }

    public static boolean isOpen(int current_x, int current_y, int next_x, int next_y) {
        int currentPopulation = map[current_x][current_y];
        int nextPopulation = map[next_x][next_y];
        int interval = Math.abs(currentPopulation - nextPopulation);

        if (interval >= L && interval <= R) {
            return true;
        }
        return false;
    }

    // 나라별 인구수 출력
    public static void printCountry() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static class Location {
        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
