package org.baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 시뮬레이션 알고리즘 - 백준: 11559번 (골드 4)
 * - 블록 쌓기랑 비슷한 유형인 것 같다.
 * - 내가 실수한 점
 *   - 블록을 없애고 재조정할 때, 마지막 row만 사용하면 되는데 for문으로 돌려 버렸다.
 *   - 문제에서 연쇄 반응이 동시에 발생한다는 부분을 놓쳐 버렸다.
 */
public class Problem_11559 {

    static String[][] map;
    static int result = 0;
    static int[] direction_row = {-1, 1, 0, 0};  // 상하좌우
    static int[] direction_col = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        map = new String[12][6];
        for (int i = 0; i < 12; i++) {
            String line = br.readLine();
            for (int j = 0; j < line.length(); j++) {
                map[i][j] = String.valueOf(line.charAt(j));
            }
        }

        /**
         * 전체 맵 검사하면서, 알파벳 발견시 bfs 검사
         * - bfs 검사하면서 상하좌우에 같은 알파벳이 붙어 있으면 큐에 담아주기
         * - 검사 종료 후 개수가 4개 이상이라면 담았던 큐에서 해당 알파벳들을 .으로 대체하고, 1 리턴(연쇄 반응이 한번 일어 났다는 의미)
         *
         * 검사 이후 증가된 연쇄 반응이 있었다면 재조정 실시
         * - 새로운 맵 생성
         * - 마지막 row에서 col마다 재조정 실시
         *   - 마지막 row에서 0까지 해당 컬럼에 있는 알파벳 값들을 큐에 삽입
         *   - 큐의 크기가 0이라면 해당 줄에 . 삽입
         *   - 큐의 크기가 0이 아니라면, 큐의 요소들을 먼저 밑에서부터 삽입해주고 나머지는 .으로 삽입
         *
         * 없었다면 탐색 종료 후, 결과 출력
         */

        while (true) {
            // 전체 맵 검사
            boolean boomStatus = isBoom();
            // 재지정이 필요한 경우
            if (boomStatus) {
                map = repositionMap();
            } else {
                break;
            }
        }

        System.out.println(result);
    }

    public static String[][] repositionMap() {
        String[][] newMap = new String[12][6];

        for (int j = 0; j < 6; j++) {
            // 세로줄에서 알파벳만 추출해서 큐에 넣기
            Queue<String> digitQueue = removeBlank(11, j);

            // 새로운 맵에 세로로 한줄씩 재지정 해주기
            insertDigit(newMap, digitQueue, 11, j);
        }

        return newMap;
    }

    public static Queue<String> removeBlank(int row, int col) {
        Queue<String> queue = new LinkedList<>();
        for (int i = row; i >= 0; i--) {
            if (!map[i][col].equals(".")) {
                queue.add(map[i][col]);
            }
        }

        return queue;
    }

    public static void insertDigit(String[][] map, Queue<String> digitQueue, int row, int col) {
        int index = row;

        // 큐에 있는 걸 먼저 삽입
        while (!digitQueue.isEmpty()) {
            String digit = digitQueue.poll();
            map[index][col] = digit;
            index -= 1;
        }

        // 나머지는 .으로 채워주기
        while (index >= 0) {
            map[index][col] = ".";
            index -= 1;
        }
    }

    private static boolean isBoom() {
        int repositionCnt = 0;

        boolean[][] visited = new boolean[12][6];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 6; j++) {
                // 알파벳을 만났다면, bfs 검사 실시
                if (!map[i][j].equals(".") && !visited[i][j]) {
                    repositionCnt += bfs(i, j, map[i][j], visited);
                }
            }
        }

        // 연쇄 반응이 일어났을 경우
        if (repositionCnt >= 1) {
            result += 1;
            return true;
        }
        return false;
    }

    public static int bfs(int startRow, int startCol, String digit, boolean[][] visited) {
        Queue<Node> queue = new LinkedList<>();
        Queue<Node> tempQueue = new LinkedList<>();

        visited[startRow][startCol] = true;
        queue.add(new Node(startRow, startCol));
        tempQueue.add(new Node(startRow, startCol));

        // 상하좌우 탐색
        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    if (map[next_row][next_col].equals(digit)) {
                        queue.add(new Node(next_row, next_col));
                        tempQueue.add(new Node(next_row, next_col));
                        visited[next_row][next_col] = true;
                    }
                }
            }
        }

        // 연쇄반응 조건 만족한 경우
        if (tempQueue.size() >= 4) {
            while (!tempQueue.isEmpty()) {
                Node remove = tempQueue.poll();
                map[remove.row][remove.col] = ".";
            }
            return 1;
        }

        return 0;
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < 12 && col < 6) {
            return true;
        }
        return false;
    }

    private static void printMap() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
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
