package org.baekjoon.samsung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Problem_21610 {

    static int[][] basket;
    static int[][] cloud;
    static int[][] rainPosition;

    static int N;
    static int M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 초기 바구니 물 초기화
        basket = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                basket[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 초기 구름 생성
        cloud = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cloud[i][j] = 0;
            }
        }

        cloud[N-1][0] = 1;
        cloud[N-1][1] = 1;
        cloud[N-2][0] = 1;
        cloud[N-2][1] = 1;

        // 구름 M번 이동
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());

            // 구름 1번 이동
            moveCloud(d, s);
            rain();
            searchCopyWater();
            createCloud();
        }

        // 바구니에 들어있는 물의 양의 합
        bw.write(Integer.toString(getBasketWater()));
        bw.close();
    }

    // 구름 이동 명령
    public static void moveCloud(int d, int s) {

        int x = 0;
        int y = 0;

        if (d == 1) {
            x = 0;
            y = s * (-1);
        }

        if (d == 2) {
            x = s * (-1);
            y = s * (-1);
        }

        if (d == 3) {
            x = s * (-1);
            y = 0;
        }

        if (d == 4) {
            x = s * (-1);
            y = s;
        }

        if (d == 5) {
            x = 0;
            y = s;
        }

        if (d == 6) {
            x = s;
            y = s;
        }

        if (d == 7) {
            x = s;
            y = 0;
        }

        if (d == 8) {
            x = s;
            y = s * (-1);
        }

        changeCloud(x, y);
    }


    public static void changeCloud(int x, int y) {
        int[][] newCloud = new int[N][N];

        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (cloud[i][j] == 1) {
                    cloud[i][j] = 0;  // 초기 구름 없애주기
                    int new_i = i + x;
                    int new_j = j + y;

                    while (new_i < 0) {  // 배열 범위 밖일 경우
                        new_i += N;
                    }
                    while (new_j < 0) {
                        new_j += N;
                    }

                    while (new_i >= N) {
                        new_i -= N;
                    }
                    while (new_j >= N) {
                        new_j -= N;
                    }

                    newCloud[new_i][new_j] = 1;  // 새로운 구름 위치
                }
            }
        }

        cloud = newCloud;
    }

    public static void cloneCloudToRainPosition() {
        rainPosition = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                rainPosition[i][j] = cloud[i][j];
            }
        }
    }

    // 구름에서 비가 내려 바구니에 물 1 증가
    public static void rain() {
        cloneCloudToRainPosition();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (cloud[i][j] == 1) {  // 구름이 있는 곳
                    basket[i][j] += 1;  // 비 내려서 바구니에 물 1 증가
                    cloud[i][j] = 0;  // 구름 없애주기
                }
            }
        }
    }

    // 물 복사 버그 마법을 부릴 위치 찾아서 실행
    public static void searchCopyWater() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (rainPosition[i][j] == 1) {
                    copyWaterMagic(i, j);
                }
            }
        }
    }

    // 물 복사 버그 마법 실행
    public static void copyWaterMagic(int i, int j) {
        int possibleCount = 0;

        if (possibleNumber(i-1, j-1)) {
            possibleCount += isExistWater(i-1, j-1);
        }
        if (possibleNumber(i+1, j-1)) {
            possibleCount += isExistWater(i+1, j-1);
        }
        if (possibleNumber(i-1, j+1)) {
            possibleCount += isExistWater(i-1, j+1);
        }
        if (possibleNumber(i+1, j+1)) {
            possibleCount += isExistWater(i+1, j+1);
        }

        basket[i][j] += possibleCount;
    }

    // 배열의 인덱스 범위를 벗어나지 않는지 검사
    public static boolean possibleNumber(int i, int j) {
        if (i < 0 || i >= N) {
            return false;
        }
        if (j < 0 || j >= N) {
            return false;
        }
        return true;
    }

    // 해당 위치의 바구니에 물이 있는지 검사
    public static int isExistWater(int i, int j) {
        if (basket[i][j] > 0) {  // 해당 위치의 바구니에 물이 있다면 1 return
            return 1;
        }
        return 0;
    }

    // 바구니에서 구름 생성
    public static void createCloud() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (basket[i][j] >= 2 && rainPosition[i][j] != 1) {
                    cloud[i][j] = 1;
                    basket[i][j] -= 2;
                }
            }
        }
    }

    // 바구니에 들어있는 모든 물의 양
    public static int getBasketWater() {
        int totalWater = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                totalWater += basket[i][j];
            }
        }
        return totalWater;
    }

    public static void printCloud() {
        System.out.println("구름");
        for (int m = 0; m < N; m++) {
            for (int n = 0; n < N; n++) {
                System.out.print(cloud[m][n]);
            }
            System.out.println();
        }
    }

    public static void printBasket() {
        System.out.println("바구니");
        for (int m = 0; m < N; m++) {
            for (int n = 0; n < N; n++) {
                System.out.print(basket[m][n]);
            }
            System.out.println();
        }
    }
}
