package org.baekjoon.backtracking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 백트래킹 알고리즘 - 백준: 14889번 (실버 1)
 * - 백트래킹 알고리즘은 dfs와 비슷한 원리로 실행된다.
 * - isPromising 조건을 통해 갈 수 있는 곳인지 확인한 후, 못 간다면 그곳에서 끊어내고 되돌아가는 방식이다.
 * - 최악의 경우, 모든 트리를 돌 수도 있다고 한다.
 */
public class Problem_14889 {

    static int N;
    static int[][] map;
    static int[][] calculate;
    static boolean[] visited;
    static int min = Integer.MAX_VALUE;
    static int memberCount = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // N 입력 받기
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        // 크기 초기화
        map = new int[N][N];
        calculate = new int[N][N];
        visited = new boolean[N + 1];

        // 능력치 값 입력 받기
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 능력치 계산
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                calculate[i][j] = map[i][j] + map[j][i];
            }
        }

        backTracking(1);

        bw.write(Integer.toString(min));
        bw.close();
    }

    public static void backTracking(int depth) {
        visited[depth] = true;
        memberCount += 1;

        // 팀원의 수가 균등해졌을 경우
        if (memberCount == N / 2) {
            // 두 팀의 능력치 차이 계산
            int difference = calculatePower();
            if (difference < min) {  // 최소 값일 경우 min 업데이트
                min = difference;
            }
            visited[depth] = false;
            memberCount -= 1;
            return;
        }

        for (int i = depth; i < N; i++) {
            backTracking(i + 1);
        }

        visited[depth] = false;
        memberCount -= 1;
    }

    public static int calculatePower() {
        List<Integer> start_team = new ArrayList<>();  // visited가 true인 경우
        List<Integer> link_team = new ArrayList<>();  // visited가 false인 경우

        for (int i = 1; i < N + 1; i++) {
            if (visited[i]) {
                start_team.add(i);
            } else {
                link_team.add(i);
            }
        }

        // 능력치 구하기
        int start_power = getPower(start_team);
        int link_power = getPower(link_team);

        return Math.abs(start_power - link_power);
    }

    public static int getPower(List<Integer> team) {
        int power = 0;

        // calculate 배열에서 능력치 찾아서 계산
        for (int i = 0; i < team.size(); i++) {
            int row = team.get(i) - 1;
            for (int j = i + 1; j < team.size(); j++) {
                int col = team.get(j) - 1;
                power += calculate[row][col];
            }
        }

        return power;
    }
}
