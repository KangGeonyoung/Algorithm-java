package org.baekjoon.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 구현 알고리즘 - 백준: 15686번 (골드 5)
 * - dfs를 사용할 때 특정 위치를 먼저 찾아서 조합을 만드는 것이 시간 절약에 좋다.
 * - dfs를 실행할 때 중복 조합 발생에 주의하자.
 */
public class Problem_15686 {

    static int N, M;
    static int[][] map;
    static List<Node> chickens = new ArrayList<>();
    static List<Node> selected = new ArrayList<>();
    static PriorityQueue<Integer> minQueue = new PriorityQueue<>();  // 치킨거리의 최솟값을 저장하기 위한 큐

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());  // 남겨둬야 하는 치킨집의 개수

        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2) {  // 치킨집일 때
                    chickens.add(new Node(i, j));
                }
            }
        }

        dfs(0, 0);

        if (minQueue.size() > 0) {
            System.out.println(minQueue.poll());
        }
    }

    public static void dfs(int depth, int start) {
        // M만큼 선정을 마쳤을 경우, 도시의 치킨 거리 구하기
        if (depth == M) {
            calculate();
            return;
        }

        for (int i = start; i < chickens.size(); i++) {
            selected.add(chickens.get(i));
            dfs(depth + 1, i + 1);
            selected.remove(selected.size() - 1);
        }
    }

    public static void calculate() {
        int distance = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 1) {  // 집을 발견한 경우
                    distance += getMinDistance(i, j);
                }
            }
        }

        if (distance != 0) {
            minQueue.add(distance);
        }
    }

    public static int getMinDistance(int startRow, int startCol) {
        int min = Integer.MAX_VALUE;
        for(Node chicken : selected) {
            int distance = Math.abs(startRow - chicken.row) + Math.abs(startCol - chicken.col);
            min = Math.min(min, distance);
        }

        // 가져온 최솟값 리턴
        return min;
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
