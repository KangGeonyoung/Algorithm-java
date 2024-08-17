package org.baekjoon.dfs;

import java.io.*;
import java.util.StringTokenizer;

/**
 * DFS 알고리즘 - 백준: 1987번
 * - depth를 매개변수로 전달하면서 경로의 길이를 측정할 것.
 * - List를 메서드 많이 생성하는 것은 속도 저하를 일으킴.
 * - 알파벳일 경우, 방문한 알파벳을 List에 추가하는 방식이 아닌, 배열 26칸을 만들어서 boolean으로 방문 여부를 확인하자.
 */
public class Problem_1987 {
    static int R, C;
    static char[][] map;
    static boolean[] visitedAlphabets;
    static int[] direction_row = {0, -1, 0, 1};  // 왼쪽 -> 위 -> 오른쪽 -> 아래 순서로 탐색
    static int[] direction_col = {-1, 0, 1, 0};  // 왼쪽 -> 위 -> 오른쪽 -> 아래 순서로 탐색
    static int max = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // R, C 입력 받기
        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        // 알파벳 정보 입력 받기
        map = new char[R][C];
        visitedAlphabets = new boolean[26];

        for (int i = 0; i < R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = line.charAt(j);
            }
        }

        // 시작점 설정 후 dfs 실행
        dfs(0, 0, 1);

        // 결과 출력
        bw.write(Integer.toString(max));
        bw.close();
    }

    public static void dfs(int row, int col, int depth) {
        char currentChar = map[row][col];
        visitedAlphabets[currentChar - 'A'] = true;  // 방문 처리
        max = Math.max(max, depth);  // depth로 max 값 반영

        // 4개의 방향으로 경로 탐색
        for (int i = 0; i < 4; i++) {
            int next_row = row + direction_row[i];
            int next_col = col + direction_col[i];

            if (isPossiblePosition(next_row, next_col)) {
                char nextChar = map[next_row][next_col];  // 다음 글자 가져오기

                // 그 글자가 가본 적 없는 글자라면
                if (!visitedAlphabets[nextChar - 'A']) {
                    dfs(next_row, next_col, depth + 1);
                }
            }
        }

        // 방문 처리 원복
        visitedAlphabets[currentChar - 'A'] = false;
    }

    public static boolean isPossiblePosition(int row, int col) {
        if (row >= 0 && row < R && col >= 0 && col < C) {
            return true;
        }
        return false;
    }
}
