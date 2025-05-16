package org.baekjoon.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 백트래킹 알고리즘 - 백준: 18428번 (골드 5)
 * - 백트래킹 알고리즘은 우선 조합을 만들어 놓고, 해당 조합들이 조건에 맞는지 확인하는 흐름인 경우가 많은 것 같다.
 * - 내가 실수한 것
 *   - 학생의 초기 위치를 Queue에 넣은 것 -> poll()을 통해 사용하다가 아예 제거되어 버림. -> 재사용 불가능
 *   - 되도록 초기값들은 List에 저장할 것
 */
public class Problem_18428 {

    static int N;
    static char[][] map;
    static List<Integer> selected = new ArrayList<>();
    static List<Node> students = new ArrayList<>();
    static int[] direction_row = {-1, 1, 0, 0};  // 상-하-좌-우
    static int[] direction_col = {0, 0, -1, 1};
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        map = new char[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = st.nextToken().charAt(0);
                if (map[i][j] == 'S') {  // 학생 위치 저장
                    students.add(new Node(i, j));
                }
            }
        }

        combination(0);
        if (answer == 0) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
        }
    }

    public static void combination(int index) {
        if (selected.size() == 3) {
            // 모든 학생들이 감시를 피할 수 있는 검사
            if (isValidPosition()) {
                answer += 1;
            }
            return;
        }

        for (int i = index; i < N * N; i++) {
            selected.add(i);
            combination(i + 1);
            selected.remove(selected.size() - 1);
        }
    }

    // 장애물 3개가 X에 놓여 있는지, 모든 학생들이 감시를 피하는지 검사
    public static boolean isValidPosition() {
        // 장애물 3개 모두 X에 놓여 있는지 검사
        if (!isRightPosition()) {
            return false;
        }

        // selected -> 'O'로 변환하기
        Queue<Character> temp = new LinkedList<>();
        for (Integer number : selected) {
            temp.add(map[number / N][number % N]);
            map[number / N][number % N] = 'O';
        }

        // 모든 학생들이 감시를 피할 수 없을 경우
        if (!canAvoid()) {
            // 원복
            for (Integer number : selected) {
                map[number / N][number % N] = temp.poll();
            }
            return false;
        }

        // 원복
        for (Integer number : selected) {
            map[number / N][number % N] = temp.poll();
        }

        return true;
    }

    // 장애물 3개 모두 X에 놓여 있는지 검사
    public static boolean isRightPosition() {
        for (Integer index : selected) {
            int row = index / N;
            int col = index % N;
            if (map[row][col] != 'X') {
                return false;
            }
        }
        return true;
    }

    public static boolean canAvoid() {
        for (Node student : students) {
            if (isInTeacherRange(student.row, student.col)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInTeacherRange(int row, int col) {
        // 상
        if (isExistTeacher(row, col, 0)) {
            return true;
        }

        // 하
        if (isExistTeacher(row, col, 1)) {
            return true;
        }

        // 좌
        if (isExistTeacher(row, col, 2)) {
            return true;
        }

        // 우
        if (isExistTeacher(row, col, 3)) {
            return true;
        }

        return false;
    }

    public static boolean isExistTeacher(int row, int col, int dir) {
        while (true) {
            int next_row = row + direction_row[dir];
            int next_col = col + direction_col[dir];

            if (isValid(next_row, next_col)) {
                if (map[next_row][next_col] == 'T') {
                    return true;
                }
                if (map[next_row][next_col] == 'O') {
                    return false;
                }
                row = next_row;
                col = next_col;
            } else {
                return false;
            }
        }
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < N) {
            return true;
        }
        return false;
    }

    public static void print() {
        System.out.println();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
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
