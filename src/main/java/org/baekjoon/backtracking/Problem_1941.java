package org.baekjoon.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 백트래킹 알고리즘 - 백준: 1941번 (골드 3)
 * - nCm과 같은 조합을 만드는 코드를 익혀두자.
 * - 모든 경우의 수를 만들어서, 특정 조건이 되었을 때 검사를 통해 카운팅 하는 방식을 고려해 보자.
 * - 배열 크기가 주어졌을 때, 각 칸별로 번호를 부여해서 우리만의 인덱싱 규칙을 만드는 방식도 좋은 듯 하다.
 *   - 해당 문제에서는 5*5 크기의 배열이었기 때문에, { row = num / 5 }, { col = num % 5 }로 인덱싱을 했다.
 */
public class Problem_1941 {

    static char[][] girl;
    static List<Integer> selected = new ArrayList<>();
    static int result = 0;
    static int[] direction_row = {-1, 1, 0, 0};  // 상-하-좌-우
    static int[] direction_col = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        girl = new char[5][5];
        for (int i = 0; i < 5; i++) {
            String line = br.readLine();
            for (int j = 0; j < 5; j++) {
                girl[i][j] = line.charAt(j);
            }
        }

        combination(0, 0);
        System.out.println(result);
    }

    // 25명 중 7명의 조합을 만드는 메서드
    public static void combination(int index, int depth) {
        if (depth == 7) {
            if (isValidGroup()) {
                result += 1;
            }
            return;
        }

        for (int i = index; i < 25; i++) {
            selected.add(i);
            combination(i + 1, depth + 1);
            selected.remove(selected.size() - 1);
        }
    }

    // 7명의 여학생이 서로 연결되어 있는지, 다솜파(S)가 4명 이상인지 검사
    public static boolean isValidGroup() {
        boolean[] visited = new boolean[7];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        visited[0] = true;

        int connected = 1;
        int cnt_S = 0;

        if (girl[selected.get(0) / 5][selected.get(0) % 5] == 'S') {
            cnt_S += 1;
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            int current_row = selected.get(current) / 5;
            int current_col = selected.get(current) % 5;

            for (int i = 0; i < 4; i++) {
                int next_row = current_row + direction_row[i];
                int next_col = current_col + direction_col[i];
                int next_index = next_row * 5 + next_col;

                for (int j = 0; j < 7; j++) {
                    if (!visited[j] && isValid(next_row, next_col) && selected.get(j) == next_index) {
                        visited[j] = true;
                        queue.add(j);
                        connected += 1;
                        if (girl[next_row][next_col] == 'S') {
                            cnt_S += 1;
                        }
                    }
                }
            }
        }

        return connected == 7 && cnt_S >= 4;
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < 5 && col < 5) {
            return true;
        }
        return false;
    }
}
