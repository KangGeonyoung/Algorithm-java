package org.baekjoon.dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * DFS 알고리즘 - 백준: 1987번(2) (골드 4)
 * - dfs를 4개의 방향으로 펼쳐 나가는 방식의 문제이다.
 * - dfs를 사용할 때는 base condition과 누적 변수를 사용할 때 과거의 경로가 옳게 삭제됐는지 잘 확인해야 한다.
 */
public class Problem_1987_2 {

    static int R, C;
    static char[][] map;

    static int[] direction_row = {-1, 1, 0, 0};  // 상하좌우
    static int[] direction_col = {0, 0, -1, 1};

    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];
        for (int i = 0; i < R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = line.charAt(j);
            }
        }

        List<Character> selected = new ArrayList<>();
        dfs(0, 0, selected);

        System.out.println(max);
    }

    public static void dfs(int row, int col, List<Character> selected) {
        if (!selected.contains(map[row][col])) {
            selected.add(map[row][col]);
            max = Math.max(max, selected.size());
        } else {
            return;
        }

        for (int i = 0; i < 4; i++) {
            int next_row = row + direction_row[i];
            int next_col = col + direction_col[i];

            if (isValid(next_row, next_col)) {
                int preSize = selected.size();
                dfs(next_row, next_col, selected);
                if (preSize != selected.size()) {
                    selected.remove(selected.size() - 1);
                }
            }
        }
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < R && col < C) {
            return true;
        }
        return false;
    }
}
