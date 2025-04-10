package org.baekjoon.samsung;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * 삼성 2023 하반기 오전 1번 문제 : 미지의 공간 탈출 (첫 번째 풀이)
 * - https://www.codetree.ai/ko/frequent-problems/problems/royal-knight-duel/description?introductionSetId=&bookmarkId=
 */
public class Problem_2023_2_1 {

    static int L, N, Q;
    static int[][] chessMap;  // 체스판 맵
    static int[][] warriorMap;  // 기사 맵
    static List<Warrior> warriors = new ArrayList<>();  // 기사 정보
    static List<Instruction> instructions = new ArrayList<>();  // 왕의 명령
    static HashSet<Integer> movedWarriors = new HashSet<>();  // 하나의 명령을 수행하는 동안 이동한 기사들

    static int[] direction_row = {-1, 0, 1, 0};  // 상-우-하-좌
    static int[] direction_col = {0, 1, 0, -1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        L = sc.nextInt();
        N = sc.nextInt();
        Q = sc.nextInt();

        chessMap = new int[L][L];
        warriorMap = new int[L][L];

        // 체스맵
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                chessMap[i][j] = sc.nextInt();
            }
        }

        // 기사 정보
        for (int i = 0; i < N; i++) {
            warriors.add(new Warrior(i + 1, sc.nextInt() - 1, sc.nextInt() - 1, sc.nextInt(), sc.nextInt(), sc.nextInt()));
        }

        // 왕의 명령
        for (int i = 0; i < Q; i++) {
            instructions.add(new Instruction(sc.nextInt(), sc.nextInt()));
        }

        // ---------------------------------------

        // 1. Q개의 명령을 실행 - i번째 기사를 d방향으로 한칸 움직이기
        // 2. 기사를 기사맵에 배치
        //    height - 1, weight - 1 -> (1,2), (3,2) -> 기사의 첫번째 칸과 마지막 칸 좌표 구하기
        //    row가 서로 같다면 row 좌표로 for문, col이 같다면 col 좌표로 for문
        // 3. 왕의 명령 실행
        //    - 현재 기사맵을 하나 복제
        //    - 해당 기사맵으로 가상 시뮬레이션 돌려보기
        //    - 1번 기사를 아래 방향으로 이동하는 명령이라면, 1번 기사 블록을 모두 찾아서 해당 방향으로 이동 가능한지 확인
        //    - 만약 벽이라면, false 판정
        //    - 빈공간이면 true, 다른 기사 자리라면 그 기사한테 해당 방향으로 갈 수 있는지 확인 요청
        // 4. 이동 후, 대미지를 계산
        //    - 체스맵이랑 기사맵을 비교해서 기사별 대미지 결과 측정
        //    - 체력이 0이 된 기사들은 삭제 처리
        // 5. 명령이 모두 끝났다면 남아 있는 기사들의 누적 대미지를 합하여 출력

        // 기사들을 기사맵에 배치
        for(Warrior warrior : warriors) {
            int start_row = warrior.row;
            int start_col = warrior.col;

            int end_row = start_row + (warrior.height - 1);
            int end_col = start_col + (warrior.width - 1);

            for (int i = start_row; i <= end_row; i++) {
                for (int j = start_col; j <= end_col; j++) {
                    warriorMap[i][j] = warrior.number;
                }
            }
        }

        // 왕의 명령 실행
        for (int i = 0; i < Q; i++) {
            movedWarriors.clear();
            Instruction instruction = instructions.get(i);
            int warrior_num = instruction.index;
            int warrior_dir = instruction.dir;

            // 현재 기사맵 복제
            int[][] temp_warrior_map = copyMap(warriorMap);

            // 복제한 기사맵으로 명령 시뮬레이션을 통과했을 경우
            if (isPossibleInstruction(temp_warrior_map, warrior_num, warrior_dir)) {
                // 기사 이동
                runInstruction(temp_warrior_map, warrior_num, warrior_dir);

                // 대결 대미지 계산
                calculateDamage(warrior_num);
//                printWarriorState();
            }
        }

        int result = 0;
        // 남아 있는 기사들의 누적 대미지 합 출력
        for(Warrior warrior : warriors) {
            result += warrior.damage;
        }

        System.out.println(result);
    }

    // 기사 상태 출력
    private static void printWarriorState() {
        for (int j = 0; j < warriors.size(); j++) {
            Warrior warrior = warriors.get(j);
//            System.out.println("#" + warrior.number + " hp=" + warrior.hp + " damage=" + warrior.damage);
        }
        System.out.println();
    }

    public static void calculateDamage(int current_warrior_num) {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                // 함정이 있는 곳일 때
                if (chessMap[i][j] == 1) {
                    // 기사가 없거나, 현재 기사 위치일 경우는 패스
                    if (warriorMap[i][j] == 0 || warriorMap[i][j] == current_warrior_num) {
                        continue;
                    } else {
                        // 움직인 기사 명단에 있을 경우, 대미지 입히기
                        if (movedWarriors.contains(warriorMap[i][j])) {
                            int damagedWarriorId = warriorMap[i][j];
                            Warrior damagedWarrior = null;
                            for(Warrior warrior : warriors) {
                                if (warrior.number == damagedWarriorId) {
                                    damagedWarrior = warrior;
                                    break;
                                }
                            }
                            damagedWarrior.hp -= 1;
                            damagedWarrior.damage += 1;

                            // 만약 대미지를 받고 hp가 0이 된다면 기사 제거
                            if (damagedWarrior.hp == 0) {
                                warriors.remove(damagedWarrior);
                                movedWarriors.remove(warriorMap[i][j]);
                                removeWarrior(warriorMap[i][j]);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void removeWarrior(int removedWarrior) {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (warriorMap[i][j] == removedWarrior) {
                    warriorMap[i][j] = 0;
                }
            }
        }
    }

    // 원본 기사맵에 기사 이동 명령 실행
    public static void runInstruction(int[][] copyMap, int warrior_num, int warrior_dir) {
        System.out.println("Start warrior_num = " + warrior_num);
        movedWarriors.add(warrior_num);
        List<Node> positions = new ArrayList<>();

        // 1. warrior_num의 현재 위치들 찾기
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (copyMap[i][j] == warrior_num) {
                    positions.add(new Node(i, j));
                }
            }
        }

        // 2. 밀려야 할 다음 위치 확인
        List<Node> nextPositions = new ArrayList<>();
        for (Node pos : positions) {
            int nextRow = pos.row + direction_row[warrior_dir];
            int nextCol = pos.col + direction_col[warrior_dir];

            if (!isValid(nextRow, nextCol)) return;  // 범위 벗어나면 실패

            int nextVal = warriorMap[nextRow][nextCol];
            if (nextVal != 0 && nextVal != warrior_num) {
                // 다른 전사가 있으면 재귀로 먼저 밀기 시도
                runInstruction(copyMap, nextVal, warrior_dir);
            }

            // 재귀 후에도 다른 전사가 있으면 이동 불가
            if (warriorMap[nextRow][nextCol] != 0 && warriorMap[nextRow][nextCol] != warrior_num) {
                return;
            }

            nextPositions.add(new Node(nextRow, nextCol));
        }

        // 3. 이동 수행 (뒤에서부터 → 덮어쓰기 방지)
        for (int i = positions.size() - 1; i >= 0; i--) {
            Node cur = positions.get(i);
            warriorMap[cur.row][cur.col] = 0;
        }

        for (int i = positions.size() - 1; i >= 0; i--) {
            Node cur = positions.get(i);
            Node next = nextPositions.get(i);
            warriorMap[next.row][next.col] = copyMap[cur.row][cur.col];
        }

        printWarriorMap();
    }


    // 복제된 기사맵에서 해당 명령을 수행할 수 있는지 판단
    //    - 1번 기사를 아래 방향으로 이동하는 명령이라면, 1번 기사 블록을 모두 찾아서 해당 방향으로 이동 가능한지 확인
    //    - 만약 벽이라면, false 판정
    //    - 빈공간이면 true, 다른 기사 자리라면 그 기사한테 해당 방향으로 갈 수 있는지 확인 요청
    public static boolean isPossibleInstruction(int[][] map, int warrior_num, int warrior_dir) {
//        System.out.println("(Possible) Start warrior_num = " + warrior_num);
        boolean isExistWarrior = false;

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (map[i][j] == warrior_num) {
                    isExistWarrior = true;
                    int next_row = i + direction_row[warrior_dir];
                    int next_col = j + direction_col[warrior_dir];

                    // 체스판 범위 밖이거나, 벽을 마주쳤다면 false
                    if (!isValid(next_row, next_col) || chessMap[next_row][next_col] == 2) {
                        return false;
                    }

                    // 이동한 곳에 내 기사번호가 아닌 다른 기사가 있는 경우, 그 기사한테 이동할 수 있는지 요청
                    if (map[next_row][next_col] != 0) {
                        if (map[next_row][next_col] != warrior_num) {
                            if (!isPossibleInstruction(map, map[next_row][next_col], warrior_dir)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        // 이동 명령을 받은 기사가 없는 경우
        if (!isExistWarrior) {
            return false;
        }

        return true;
    }

    public static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < L && col < L) {
            return true;
        }
        return false;
    }

    public static int[][] copyMap(int[][] origin) {
        int[][] copy = new int[L][L];
        for (int i = 0; i < origin.length; i++) {
            copy[i] = origin[i].clone();
        }
        return copy;
    }

    private static void printWarriorMap() {
        // 기사맵 출력
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                System.out.print(warriorMap[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void printCopyMap(int[][] map) {
        System.out.println("copyMap");
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static class Warrior {
        int number;
        int row;
        int col;
        int height;
        int width;
        int hp;
        int damage;  // 기사가 받은 대미지 (초기값은 0)

        public Warrior(int number, int row, int col, int height, int width, int hp) {
            this.number = number;
            this.row = row;
            this.col = col;
            this.height = height;
            this.width = width;
            this.hp = hp;
            this.damage = 0;
        }
    }

    static class Instruction {
        int index;
        int dir;

        public Instruction(int index, int dir) {
            this.index = index;
            this.dir = dir;
        }
    }

    static class Node {
        int row, col;
        public Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
