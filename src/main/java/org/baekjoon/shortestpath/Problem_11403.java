package org.baekjoon.shortestpath;

import java.io.*;
import java.util.StringTokenizer;

/**
 * 최단경로 알고리즘 - 백준: 11403번 (실버 1)
 * - 그래프 간선을 따라 가면서 배열에 방문 체크를 해주면 된다.
 * - while문보다는 for문 활용이 더 적합한 것 같다.
 */
public class Problem_11403 {

    static int N;
    static int[][] map;
    static int[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // N 입력 받기
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        // 간선 정보 입력 받기
        map = new int[N][N];
        visited = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 1) {
                    visited[i][j] = 1;
                    setting(i, j);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(visited[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void setting(int orderRow, int row) {
        for (int i = 0; i < N; i++) {
            if (map[row][i] == 1 && visited[orderRow][i] == 0) {
                visited[orderRow][i] = 1;
                setting(orderRow, i);
            }
        }
    }
}
