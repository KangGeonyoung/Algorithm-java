package org.baekjoon.samsung;

import java.util.*;

public class Problem_2024_1 {
    static int K, M;
    static int[][] map;  // 원본 맵
    static Queue<Integer> bonus = new LinkedList<>();  // 추가 유물 조각
    static PriorityQueue<Candidate> queue = new PriorityQueue<>(new Comparator<Candidate>() {  // 우선순위 큐
        @Override
        public int compare(Candidate o1, Candidate o2) {
            if (o1.first_value == o2.first_value) {
                if (o1.angle == o2.angle) {
                    if (o1.col == o2.col) {
                        return o1.row - o2.row;
                    }
                    return o1.col - o2.col;
                }
                return o1.angle - o2.angle;
            }
            return o2.first_value - o1.first_value;
        }
    });

    static int[] direction_row = {-1, 1, 0, 0};  // 상하좌우
    static int[] direction_col = {0, 0, -1, 1};

    static List<Integer> result = new ArrayList<>();  // 결과값

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        K = sc.nextInt();
        M = sc.nextInt();

        map = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        for (int i = 0; i < M; i++) {
            bonus.add(sc.nextInt());
        }

        // ----------- 알고리즘 시작-----------------

        // 1. 9개 칸을 중심 좌표로 하여 3번씩 회전을 실행
        // 2. 회전마다 얻을 수 있는 1차 획득 가치, 해당 좌표, 회전 각도를 queue에 삽입 (우선순위 큐를 사용?)
        //    - 만약 1차 획득 가치를 얻을 수 없다면 queue에 삽입하지 않는다.
        //    - 만약 queue가 비어 있다면, 탐사 종료
        // 3. 2번을 통해 정해진 결과로 실행 = 유물 1차 획득 + 연쇄 획득
        //    - 유물 1차 획득 : bfs를 통해 같은 숫자 칸이 3개 이상일 시 그 칸의 개수를 유물 가치 점수에 더해주고, 칸을 0으로 채워줌
        //    - 유물 연쇄 획득 : 칸에 0이 있다면 추가 유물 조각으로 채워주고, 유물 획득 가능한지 다시 재검사
        // 4. 3번에서 더이상 연쇄 획득이 없다면 다시 1번 반복

        for (int k = 0; k < K; k++) {
            // 턴 시작전, 큐 비워주기
            queue.clear();
            int score = 0;

            // 9개의 중심좌표를 기준으로 3번씩 회전 실행
            for (int i = 1; i <= 3; i++) {
                for (int j = 1; j <= 3; j++) {
                    rotateAllCase(i, j);
                }
            }

            // 우선순위 큐에서 제일 앞에 있는 데이터를 꺼내서 오리지널 맵에 실행
            if (queue.size() > 0) {

                // 오리지널 맵에서 유물 1차 획득
                Candidate candidate = queue.poll();
                int angle = candidate.angle;

                if (angle == 90) {
                    rotate90(map, candidate.row, candidate.col);
                    int first_value = getFirstValue(map);  // 1차 획득 가치
                    score += first_value;
                }

                if (angle == 180) {
                    rotate90(map, candidate.row, candidate.col);
                    rotate90(map, candidate.row, candidate.col);
                    int first_value = getFirstValue(map);
                    score += first_value;
                }

                if (angle == 270) {
                    rotate90(map, candidate.row, candidate.col);
                    rotate90(map, candidate.row, candidate.col);
                    rotate90(map, candidate.row, candidate.col);
                    int first_value = getFirstValue(map);
                    score += first_value;
                }


                // 칸에 0이 있다면
                while (isExistZero(map)) {
                    // 유물 조각 채워주기
                    fillNumber(map);

                    // 연쇄 반응 검사
                    score += getFirstValue(map);

                }

                // 해당 턴에서 얻은 유물 가치 점수 저장
                result.add(score);

            } else {  // 더이상 획득할 유물이 없다면, 탐사 자체를 종료
                break;
            }
        }

        // 각 턴마다 획득한 유물 가치 점수 총합 출력
        for (Integer num : result) {
            System.out.print(num + " ");
        }
    }

    // 추가 유물 조각 채워주는 메서드
    public static void fillNumber(int[][] matrix) {
        for (int col = 0; col < 5; col++) {
            for (int row = 4; row >= 0; row--) {
                if (matrix[row][col] == 0) {
                    matrix[row][col] = bonus.poll();
                }
            }
        }

    }

    public static boolean isExistZero(int[][] matrix) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void rotateAllCase(int centerRow, int centerCol) {
        // 90도
        int[][] temp90 = copyMap();
        rotate90(temp90, centerRow, centerCol);
        int first_value_90 = getFirstValue(temp90);  // bfs를 통해 해당 맵에서 1차 획득 가치를 얼마나 얻을 수 있는지 우선순위 큐에 업데이트
        if (first_value_90 != 0) {  // 1차 획득 가치가 0이면 유물을 획득하지 못했다는 것
            queue.add(new Candidate(first_value_90, centerRow, centerCol, 90));
        }

        // 180도
        int[][] temp180 = copyMap();
        rotate90(temp180, centerRow, centerCol);
        rotate90(temp180, centerRow, centerCol);
        int first_value_180 = getFirstValue(temp180);
        if (first_value_180 != 0) {
            queue.add(new Candidate(first_value_180, centerRow, centerCol, 180));
        }

        // 270도
        int[][] temp270 = copyMap();
        rotate90(temp270, centerRow, centerCol);
        rotate90(temp270, centerRow, centerCol);
        rotate90(temp270, centerRow, centerCol);
        int first_value_270 = getFirstValue(temp270);
        if (first_value_270 != 0) {
            queue.add(new Candidate(first_value_270, centerRow, centerCol, 270));
        }
    }

    // 원본 맵 카피해주는 메서드
    public static int[][] copyMap() {
        int[][] copy = new int[5][5];
        for (int i = 0; i < 5; i++) {
            copy[i] = map[i].clone();
        }

        return copy;
    }

    // 90도 시계방향으로 회전
    public static void rotate90(int[][] matrix, int centerRow, int centerCol) {
        int size = 3;
        int[][] temp = new int[size][size];

        int startRow = centerRow - 1;
        int startCol = centerCol - 1;

        // 서브 배열에 원본값 복사
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                temp[i][j] = matrix[startRow + i][startCol + j];
            }
        }

        // 90도 시계방향으로 회전
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[startRow + j][startCol + (size - 1 - i)] = temp[i][j];
            }
        }
    }

    public static int getFirstValue(int[][] matrix) {
        int score = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] != 0) {
                    score += bfs(matrix, i, j);
                }
            }
        }

        return score;
    }

    public static int bfs(int[][] matrix, int startRow, int startCol) {
        Queue<Node> qe = new LinkedList<>();
        Queue<Node> tempQueue = new LinkedList<>();

        boolean[][] visited = new boolean[5][5];
        int currentValue = matrix[startRow][startCol];
        int score = 0;

        tempQueue.add(new Node(startRow, startCol));
        qe.add(new Node(startRow, startCol));
        visited[startRow][startCol] = true;
        score += 1;

        while (!qe.isEmpty()) {
            Node current = qe.poll();
            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    if (matrix[next_row][next_col] == currentValue) {
                        tempQueue.add(new Node(next_row, next_col));
                        qe.add(new Node(next_row, next_col));
                        visited[next_row][next_col] = true;
                        score += 1;
                    }
                }
            }
        }

        if (score >= 3) {
            while (!tempQueue.isEmpty()) {
                Node remove = tempQueue.poll();
                matrix[remove.row][remove.col] = 0;
            }
            return score;
        }
        return 0;
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < 5 && col < 5) {
            return true;
        }
        return false;
    }

    static class Candidate {
        int first_value;
        int row;
        int col;
        int angle;

        public Candidate(int first_value, int row, int col, int angle) {
            this.first_value = first_value;
            this.row = row;
            this.col = col;
            this.angle = angle;
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
