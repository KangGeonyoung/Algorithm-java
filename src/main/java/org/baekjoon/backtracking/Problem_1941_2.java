package org.baekjoon.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Problem_1941_2 {
    static char[][] girl;
    static int answer = 0;
    static List<Integer> selected = new ArrayList<>();
    static int[] direction_row = {-1, 1, 0, 0};  // 상-하-좌-우
    static int[] direction_col = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        girl = new char[5][5];
        for (int i = 0; i < 5; i++) {
            String line = br.readLine();
            girl[i] = line.toCharArray();
        }

        combination(0);
        System.out.println(answer);
    }

    public static void combination(int index) {
        if (selected.size() == 7) {
            if (isValidGroup()) {
                answer += 1;
            }
            return;
        }

        for (int i = index; i < 25; i++) {
            selected.add(i);
            combination(i + 1);
            selected.remove(selected.size() - 1);
        }
    }

    // 7명이 연결되어 있는지, S가 4개 이상 존재하는지 검사
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
                int next_value = next_row * 5 + next_col;

                for (int j = 0; j < 7; j++) {
                    if (!visited[j] && isValid(next_row, next_col) && selected.get(j) == next_value) {
                        connected += 1;
                        queue.add(j);
                        visited[j] = true;

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
