package org.baekjoon.samsung;

import java.util.*;

public class Problem_2024_1_2 {
    static int K, M;
    static int[][] map;
    static Queue<Integer> bonus = new LinkedList<>();
    static PriorityQueue<Candidate> queue = new PriorityQueue<>(new Comparator<Candidate>() {
        @Override
        public int compare(Candidate o1, Candidate o2) {
            if (o1.value == o2.value) {
                if (o1.angle == o2.angle) {
                    if (o1.col == o2.col) {
                        return o1.row - o2.row;
                    }
                    return o1.col - o2.col;
                }
                return o1.angle - o2.angle;
            }
            return o2.value - o1.value;
        }
    });

    static int[] direction_row = {-1, 1, 0, 0};  // 상-하-좌-우
    static int[] direction_col = {0, 0, -1, 1};

    static List<Integer> result = new ArrayList<>();  // 결과 저장


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        K = sc.nextInt();
        M = sc.nextInt();

        // 유물 정보
        map = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        // 추가 유물 조각
        for (int i = 0; i < M; i++) {
            bonus.add(sc.nextInt());
        }

        // K턴 동안 탐사 진행 - 해당 턴 안에 유물을 획득하지 못했다면 바로 종료
        for (int i = 0; i < K; i++) {
            int totalValue = 0;
            queue.clear();

            // 1. 9개의 중심좌표를 기준으로 3번씩 회전해서, 1차 획득 가치를 최대로 얻을 수 있는 중심좌표, 회전각도 정보를 찾기
            for (int m = 1; m <= 3; m++) {
                for (int n = 1; n <= 3; n++) {
                    rotateAll(m, n);
                }
            }


            // 2. 찾은 중심좌표로 회전을 진행하고, 1차 획득 가치 얻기
            if (queue.size() > 0) {
                // 우선순위 큐에서 획득한 정보로 탐사 시작
                Candidate candidate = queue.poll();

                // 탐사를 통해 1차 획득 가치 얻기
                if (candidate.angle == 90) {
                    rotate90(map, candidate.row, candidate.col);
                    int value = getValue(map);
                    totalValue += value;
                }

                if (candidate.angle == 180) {
                    rotate90(map, candidate.row, candidate.col);
                    rotate90(map, candidate.row, candidate.col);
                    int value = getValue(map);
                    totalValue += value;
                }

                if (candidate.angle == 270) {
                    rotate90(map, candidate.row, candidate.col);
                    rotate90(map, candidate.row, candidate.col);
                    rotate90(map, candidate.row, candidate.col);
                    int value = getValue(map);
                    totalValue += value;
                }


                // 빈 공간에 채워넣은 맵에서 유물 연쇄 획득이 발생하는지 검사
                while(isExistZero(map)) {
                    // 빈 공간에 보너스 숫자 채워넣기
                    fillBonus(map);
                    int bonusValue = getValue(map);
                    if (bonusValue > 0) {
                        totalValue += bonusValue;
                    }
                }
            } else {  // 만약 유물을 획득하지 못했다면 탐사를 즉시 종료
                break;
            }

            // 획득한 유물의 총합을 결과에 저장
            result.add(totalValue);
        }

        // 결과 출력
        for (Integer number : result) {
            System.out.print(number + " ");
        }
    }

    public static boolean isExistZero(int[][] map) {
        for (int i = 0; i < 5; i++) {
            for (int j = 4; j >= 0; j--) {
                if (map[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    // 빈 공간에 보너스 유물 조각 채워넣기
    public static void fillBonus(int[][] map) {
        for (int col = 0; col < 5; col++) {
            for (int row = 4; row >= 0; row--) {
                if (map[row][col] == 0) {
                    map[row][col] = bonus.poll();
                }
            }
        }
    }

    // 중심좌표를 기준으로 90, 180, 270도 회전 시켜서 발생하는 값을 우선순위 큐에 추가하기
    public static void rotateAll(int center_row, int center_col) {
        // 90도
        int[][] map_90 = copyMap(map);
        rotate90(map_90, center_row, center_col);
        int value_90 = getValue(map_90);
        if (value_90 > 0) {
            queue.add(new Candidate(center_row, center_col, 90, value_90));
        }

        // 180도
        int[][] map_180 = copyMap(map);
        rotate90(map_180, center_row, center_col);
        rotate90(map_180, center_row, center_col);
        int value_180 = getValue(map_180);
        if (value_180 > 0) {
            queue.add(new Candidate(center_row, center_col, 180, value_180));
        }

        // 270도
        int[][] map_270 = copyMap(map);
        rotate90(map_270, center_row, center_col);
        rotate90(map_270, center_row, center_col);
        rotate90(map_270, center_row, center_col);
        int value_270 = getValue(map_270);
        if (value_270 > 0) {
            queue.add(new Candidate(center_row, center_col, 270, value_270));
        }
    }

    // 중심좌표를 기준으로 시계방향으로 90도 회전
    public static void rotate90(int[][] map, int center_row, int center_col) {
        int size = 3;
        int[][] temp = new int[size][size];

        int start_row = center_row - 1;
        int start_col = center_col - 1;

        // 원복 복제
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                temp[i][j] = map[start_row + i][start_col + j];
            }
        }

        // 90도 시계방향 회전
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[start_row + j][start_col + (size - 1 - i)] = temp[i][j];
            }
        }

    }

    // 해당 맵에서 얻을 수 있는 최대 가치 얻기
    public static int getValue(int[][] map) {
        int sum = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (map[i][j] != 0) {
                    sum += bfs(map, i, j);
                }
            }
        }

        return sum;
    }

    // bfs를 통해 1차 유물 획득 가치 구하기
    public static int bfs(int[][] map, int startRow, int startCol) {
        Queue<Node> q = new LinkedList<>();
        List<Node> removeCandidate = new ArrayList<>();
        boolean[][] visited = new boolean[5][5];

        int count = 0;  // 연결된 조각 개수
        int current_value = map[startRow][startCol];

        q.add(new Node(startRow, startCol));
        visited[startRow][startCol] = true;
        count += 1;
        removeCandidate.add(new Node(startRow, startCol));

        while(!q.isEmpty()) {
            Node current = q.poll();

            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if(isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    if (map[next_row][next_col] == current_value) {
                        count += 1;
                        q.add(new Node(next_row, next_col));
                        visited[next_row][next_col] = true;
                        removeCandidate.add(new Node(next_row, next_col));
                    }
                }
            }
        }

        // 3개 이상 연결되었을 때, 연결된 조각들 제거하고 값 리턴
        if (count >= 3) {
            for (Node node : removeCandidate) {
                map[node.row][node.col] = 0;
            }
            return count;
        }
        return 0;
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < 5 && col < 5) {
            return true;
        }
        return false;
    }

    public static int[][] copyMap(int[][] origin) {
        int[][] copy = new int[5][5];
        for (int i = 0; i < 5; i++) {
            copy[i] = origin[i].clone();
        }

        return copy;
    }

    private static void printMap(int[][] map) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static class Candidate {
        int row;  // 중심좌표
        int col;
        int angle;
        int value;  // 1차 획득 가치

        public Candidate(int row, int col, int angle, int value) {
            this.row = row;
            this.col = col;
            this.angle = angle;
            this.value = value;
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
