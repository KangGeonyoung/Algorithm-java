package org.baekjoon.samsung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 반례
 * 	5 5
 1 0 0 0 1
 0 1 0 1 0
 0 0 0 0 0
 0 1 0 1 0
 1 0 0 0 1
 output: 3
 answer: 1
 */

public class Problem_15683 {

    static int N, M;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 사무실 & CCTV 정보 입력 받기
        int[][] officeMap = new int[N][M];
        List<Integer> cctvList = new ArrayList<Integer>();
        int area = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                officeMap[i][j] = Integer.parseInt(st.nextToken());
                if (officeMap[i][j] >= 1 && officeMap[i][j] <= 5) {  // cctv 정보 수집
                    cctvList.add(officeMap[i][j]);
                }
                if (officeMap[i][j] == 0) {  // cctv가 촬영 가능한 공간의 수(=숫자가 0인 곳)
                    area += 1;
                }
            }
        }

        int depth = 0;
        controller(officeMap, cctvList, area, depth);
        bw.write(Integer.toString(min));
        bw.close();
    }

    // 첫 번째 cctv부터 시작
    public static void controller(int[][] officeMap, List<Integer> cctvList, int area, int depth) {
        // 종료 시점 : 모든 cctv 방향 설정 완료
        if (cctvList.size() == depth) {
            findMin(officeMap, area);
            return;
        }

        switch (cctvList.get(depth)) {
            case 1:
                // 1번 cctv 함수 호출
                cctv_1(officeMap, cctvList, area, depth);
                break;

            case 2:
                cctv_2(officeMap, cctvList, area, depth);
                break;

            case 3:
                cctv_3(officeMap, cctvList, area, depth);
                break;

            case 4:
                cctv_4(officeMap, cctvList, area, depth);
                break;

            case 5:
                cctv_5(officeMap, cctvList, area, depth);
                break;

            default:
                break;
        }
    }

    public static void findMin(int[][] officeMap, int area) {
        // map에서 0의 개수를 찾기
        int cnt_0 = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (officeMap[i][j] == 0) {
                    cnt_0 += 1;
                }
            }
        }

        if (min > cnt_0) {
            min = cnt_0;
        }
    }

    // 1번 cctv의 경우
    public static void cctv_1(int[][] officeMap, List<Integer> cctvList, int area, int myDepth) {
        int[] direction_unclock_x = {0, -1, 0, 1};  // 우-상-좌-하 순서 (반시계 방향)
        int[] direction_unclock_y = {1, 0, -1, 0};

        int cctv_x = -1;
        int cctv_y = -1;
        int match = 0;

        loop:
        for (int i = 0; i < N; i++) {  // 현재 호출한 cctv의 위치 찾기
            for (int j = 0; j < M; j++) {
                if (officeMap[i][j] >= 1 && officeMap[i][j] <= 5) {
                    if (match == myDepth) {
                        cctv_x = i;
                        cctv_y = j;
                        break loop;
                    } else {
                        match += 1;
                    }
                }
            }
        }

        // 찾은 cctv의 위치에서 가능한 방향을 모두 고려
        // 반시계 방향
        for (int i = 0; i < 4; i++) {
            // direction 정보를 사용해서 officeMap을 업데이트
            int[][] newMap = updateMap(officeMap, cctv_x, cctv_y, direction_unclock_x[i], direction_unclock_y[i]);

            // myDepth 정보를 보고 다음 depth에 해당하는 함수를 호출
            // 다음 cctv 함수를 호출 (newMap, cctvList, area, depth+1)
            controller(newMap, cctvList, area, myDepth + 1);
        }
    }

    // 2번 cctv의 경우
    public static void cctv_2(int[][] officeMap, List<Integer> cctvList, int area, int myDepth) {
        int[] direction_x = {0, 0, -1, 1};  // 우-좌-상-하
        int[] direction_y = {1, -1, 0, 0};

        int cctv_x = -1;
        int cctv_y = -1;
        int match = 0;
        loop:
        for (int i = 0; i < N; i++) {  // 현재 호출한 cctv의 위치 찾기
            for (int j = 0; j < M; j++) {
                if (officeMap[i][j] >= 1 && officeMap[i][j] <= 5) {
                    if (match == myDepth) {
                        cctv_x = i;
                        cctv_y = j;
                        break loop;
                    } else {
                        match += 1;
                    }
                }
            }
        }

        // 찾은 cctv의 위치에서 가능한 방향을 모두 고려
        for (int i = 0; i <= 2; i = i + 2) {
            // direction 정보를 사용해서 officeMap을 업데이트
            int[][] newMap = updateMap(officeMap, cctv_x, cctv_y, direction_x[i], direction_y[i]);
            int[][] newMap2 = updateMap(newMap, cctv_x, cctv_y, direction_x[i+1], direction_y[i+1]);

            // myDepth 정보를 보고 다음 depth에 해당하는 함수를 호출
            // 다음 cctv 함수를 호출 (newMap, cctvList, area, depth+1)
            controller(newMap2, cctvList, area, myDepth + 1);
        }
    }

    // 3번 cctv의 경우
    public static void cctv_3(int[][] officeMap, List<Integer> cctvList, int area, int myDepth) {
        int[] direction_unclock_x = {0, -1, 0, 1};  // 우상-상좌-좌하-하우
        int[] direction_unclock_y = {1, 0, -1, 0};

        int cctv_x = -1;
        int cctv_y = -1;
        int match = 0;
        loop:
        for (int i = 0; i < N; i++) {  // 현재 호출한 cctv의 위치 찾기
            for (int j = 0; j < M; j++) {
                if (officeMap[i][j] >= 1 && officeMap[i][j] <= 5) {
                    if (match == myDepth) {
                        cctv_x = i;
                        cctv_y = j;
                        break loop;
                    } else {
                        match += 1;
                    }
                }
            }
        }

        // 찾은 cctv의 위치에서 가능한 방향을 모두 고려
        for (int i = 0; i < 4; i++) {
            int j = i;

            // direction 정보를 사용해서 officeMap을 업데이트
            int[][] newMap = updateMap(officeMap, cctv_x, cctv_y, direction_unclock_x[j], direction_unclock_y[j]);
            if (j+1 == 4) {  // 인덱스 아웃 방지
                j -= 4;
            }
            int[][] newMap2 = updateMap(newMap, cctv_x, cctv_y, direction_unclock_x[j+1], direction_unclock_y[j+1]);

            // myDepth 정보를 보고 다음 depth에 해당하는 함수를 호출
            // 다음 cctv 함수를 호출 (newMap, cctvList, area, depth+1)
            controller(newMap2, cctvList, area, myDepth + 1);
        }
    }

    // 4번 cctv의 경우
    public static void cctv_4(int[][] officeMap, List<Integer> cctvList, int area, int myDepth) {
        int[] direction_unclock_x = {0, -1, 0, 1};  // 우상-상좌-좌하-하우
        int[] direction_unclock_y = {1, 0, -1, 0};

        int cctv_x = -1;
        int cctv_y = -1;
        int match = 0;
        loop:
        for (int i = 0; i < N; i++) {  // 현재 호출한 cctv의 위치 찾기
            for (int j = 0; j < M; j++) {
                if (officeMap[i][j] >= 1 && officeMap[i][j] <= 5) {
                    if (match == myDepth) {
                        cctv_x = i;
                        cctv_y = j;
                        break loop;
                    } else {
                        match += 1;
                    }
                }
            }
        }

        // 찾은 cctv의 위치에서 가능한 방향을 모두 고려
        for (int i = 0; i < 4; i++) {
            int j = i;

            // direction 정보를 사용해서 officeMap을 업데이트
            int[][] newMap = updateMap(officeMap, cctv_x, cctv_y, direction_unclock_x[j], direction_unclock_y[j]);
            if (j+1 == 4) {  // 인덱스 아웃 방지
                j -= 4;
            }
            int[][] newMap2 = updateMap(newMap, cctv_x, cctv_y, direction_unclock_x[j+1], direction_unclock_y[j+1]);
            if (i+2 == 4) {  // 인덱스 아웃 방지
                j -= 4;
            }
            int[][] newMap3 = updateMap(newMap2, cctv_x, cctv_y, direction_unclock_x[j+2], direction_unclock_y[j+2]);

            // myDepth 정보를 보고 다음 depth에 해당하는 함수를 호출
            // 다음 cctv 함수를 호출 (newMap, cctvList, area, depth+1)
            controller(newMap3, cctvList, area, myDepth + 1);
        }
    }

    // 5번 cctv의 경우
    public static void cctv_5(int[][] officeMap, List<Integer> cctvList, int area, int myDepth) {
        int[] direction_x = {0, -1, 0, 1};  // 우상-상좌-좌하-하우
        int[] direction_y = {1, 0, -1, 0};


        int cctv_x = -1;
        int cctv_y = -1;
        int match = 0;
        loop:
        for (int i = 0; i < N; i++) {  // 현재 호출한 cctv의 위치 찾기
            for (int j = 0; j < M; j++) {
                if (officeMap[i][j] >= 1 && officeMap[i][j] <= 5) {
                    if (match == myDepth) {
                        cctv_x = i;
                        cctv_y = j;
                        break loop;
                    } else {
                        match += 1;
                    }
                }
            }
        }

        // 찾은 cctv의 위치에서 가능한 방향을 모두 고려
        int[][] newMap = updateMap(officeMap, cctv_x, cctv_y, direction_x[0], direction_y[0]);
        int[][] newMap2 = updateMap(newMap, cctv_x, cctv_y, direction_x[1], direction_y[1]);
        int[][] newMap3 = updateMap(newMap2, cctv_x, cctv_y, direction_x[2], direction_y[2]);
        int[][] newMap4 = updateMap(newMap3, cctv_x, cctv_y, direction_x[3], direction_y[3]);

        // myDepth 정보를 보고 다음 depth에 해당하는 함수를 호출
        // 다음 cctv 함수를 호출 (newMap, cctvList, area, depth+1)
        controller(newMap4, cctvList, area, myDepth + 1);
    }


    // 해당 방향으로 맵을 업데이트 해서 반환
    public static int[][] updateMap(int[][] currentMap, int cctv_x, int cctv_y, int dir_x, int dir_y) {
        // 새로운 맵 생성
        int[][] newMap = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                newMap[i][j] = currentMap[i][j];
            }
        }

        // 해당 방향으로 map을 업데이트
        while(true) {
            cctv_x += dir_x;
            cctv_y += dir_y;
            if (!isValidPosition(newMap, cctv_x, cctv_y)) {  // 배열 범위 밖 or 벽에 해당한다면
                break;
            }
            if (newMap[cctv_x][cctv_y] == 0) {
                newMap[cctv_x][cctv_y] = -1;  // 감시 가능한 영역을 -1로 업데이트
            }
        }

        return newMap;
    }

    // 해당 위치가 cctv가 감시할 수 있는 곳인지 검사
    public static boolean isValidPosition(int[][] officeMap, int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }
        if (x >= N || y >= M) {
            return false;
        }
        if (officeMap[x][y] == 6) {  // 벽일 경우
            return false;
        }
        return true;
    }

    public static void printOfficeMap(int[][] officeMap) {
        System.out.println();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(officeMap[i][j] + " ");
            }
            System.out.println();
        }
    }
}
