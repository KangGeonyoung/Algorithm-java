package org.baekjoon.samsung;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * 삼성 2023 하반기 오전 1번 문제 : 미지의 공간 탈출 (두 번째 풀이)
 * - https://www.codetree.ai/ko/frequent-problems/problems/royal-knight-duel/description?introductionSetId=&bookmarkId=
 */
public class Problem_2023_2_2 {
    static int L, N, Q;
    static int[][] chessMap;
    static int[][] warriorMap;
    static List<Warrior> warriors = new ArrayList<>();
    static List<Instruction> instructions = new ArrayList<>();
    static HashSet<Integer> movedWarriors = new HashSet<>();

    static int[] direction_row = {-1, 0, 1, 0};  // 상-우-하-좌
    static int[] direction_col = {0, 1, 0, -1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        L = sc.nextInt();
        N = sc.nextInt();
        Q = sc.nextInt();

        // 체스맵 정보
        chessMap = new int[L][L];
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                chessMap[i][j] = sc.nextInt();
            }
        }

        // 기사 정보
        for (int i = 0; i < N; i++) {
            warriors.add(new Warrior((i+1), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt()));
        }

        // 왕의 명령 정보
        for (int i = 0; i < Q; i++) {
            instructions.add(new Instruction(sc.nextInt(), sc.nextInt()));
        }

        // 기사맵에 기사들 배치
        warriorMap = new int[L][L];
        for(Warrior warrior : warriors) {
            for (int i = warrior.row; i < warrior.row + warrior.height; i++) {
                for (int j = warrior.col; j < warrior.col + warrior.width; j++) {
                    warriorMap[i][j] = warrior.number;
                }
            }
        }

        for (int i = 0; i < Q; i++) {
            movedWarriors.clear();
            Instruction instruction = instructions.get(i);
            int warrior_num = instruction.index;
            int warrior_dir = instruction.dir;

            int[][] temp_warrior_map = copyMap(warriorMap);

            // 해당 기사가 살아있고, 이동 가능한 경우
            if (isLiveWarrior(warrior_num) && isPossibleMoving(temp_warrior_map, warrior_num, warrior_dir)) {
                // 기사 이동
                moveWarrior(temp_warrior_map, warrior_num, warrior_dir);

                // 대미지 계산
                calculateDamage(warrior_num);
            }
        }

        // 대미지 총합 출력
        int result = 0;
        for(Warrior warrior : warriors) {
            result += warrior.damage;
        }
        System.out.println(result);
    }

    public static void calculateDamage(int warrior_num) {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                // 함정 위치라면
                if (chessMap[i][j] == 1) {
                    // 해당 위치에 기사가 있다면
                    if (warriorMap[i][j] > 0 && warriorMap[i][j] != warrior_num) {
                        // 움직인 기사들 안에 포함되어 있다면
                        if (movedWarriors.contains(warriorMap[i][j])) {
                            Warrior damagedWarrior = null;
                            for(Warrior warrior : warriors) {
                                if (warrior.number == warriorMap[i][j]) {
                                    damagedWarrior = warrior;
                                    break;
                                }
                            }

                            // 대미지 입히기
                            damagedWarrior.hp -= 1;
                            damagedWarrior.damage += 1;

                            // 기사 사망 조건
                            if (damagedWarrior.hp <= 0) {
                                warriors.remove(damagedWarrior);
                                movedWarriors.remove(damagedWarrior.number);
                                removeWarrior(damagedWarrior.number);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void removeWarrior(int warrior_num) {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (warriorMap[i][j] == warrior_num) {
                    warriorMap[i][j] = 0;
                }
            }
        }
    }

    public static void moveWarrior(int[][] map, int warrior_num, int warrior_dir) {
//        System.out.println("Start warrior_num = " + warrior_num);
        movedWarriors.add(warrior_num);
        List<Node> current = new ArrayList<>();
        List<Node> next = new ArrayList<>();

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (map[i][j] == warrior_num) {
                    current.add(new Node(i, j));

                    int next_row = i + direction_row[warrior_dir];
                    int next_col = j + direction_col[warrior_dir];

                    if (!isValid(next_row, next_col)) {
                        return;
                    }

                    // 이동할 곳에 다른 기사가 있는 경우, 이동 요청
                    if (map[next_row][next_col] != 0 && map[next_row][next_col] != warrior_num) {
                        moveWarrior(map, map[next_row][next_col], warrior_dir);
                    }

                    // 다른 기사에게 이동요청을 보냈는데도, 아직 해당 공간이 비어 있지 않다면 이동 불가 판정
                    if (warriorMap[next_row][next_col] != 0 && warriorMap[next_row][next_col] != warrior_num) {
                        return;
                    }

                    next.add(new Node(next_row, next_col));
                }
            }
        }

        // 기사의 현재 정보를 지워주기
        for(Node node : current) {
            warriorMap[node.row][node.col] = 0;
        }

        // 기사의 다음 위치 정보를 심어주기
        for (int i = current.size() - 1; i >= 0; i--) {
            Node current_position = current.get(i);
            Node next_position = next.get(i);

            warriorMap[next_position.row][next_position.col] = map[current_position.row][current_position.col];
        }

//        printMap(warriorMap);
    }

    // 해당 기사가 살아 있는지 확인
    public static boolean isLiveWarrior(int warrior_num) {
        for(Warrior warrior : warriors) {
            if (warrior.number == warrior_num) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPossibleMoving(int[][] map, int warrior_num, int warrior_dir) {
//        System.out.println("Start warrior_num = " + warrior_num);
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (map[i][j] == warrior_num) {
                    int next_row = i + direction_row[warrior_dir];
                    int next_col = j + direction_col[warrior_dir];

                    // 범위를 벗어나거나, 벽을 만난 경우 false 리턴
                    if(!isValid(next_row, next_col) || chessMap[next_row][next_col] == 2) {
                        return false;
                    }

                    // 본인이 아닌 다른 기사가 있는 경우
                    if (map[next_row][next_col] != 0 && map[next_row][next_col] != warrior_num) {
                        if (!isPossibleMoving(map, map[next_row][next_col], warrior_dir)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public static int[][] copyMap(int[][] origin) {
        int[][] copy = new int[L][L];

        for (int i = 0; i < origin.length; i++) {
            copy[i] = origin[i].clone();
        }
        return copy;
    }

    public static boolean isValid(int row, int col) {
        if(row >= 0 && col >= 0 && row < L && col < L) {
            return true;
        }
        return false;
    }

    private static void printMap(int[][] map) {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
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

    static class Instruction {
        int index;
        int dir;

        public Instruction(int index, int dir) {
            this.index = index;
            this.dir = dir;
        }
    }

    static class Warrior {
        int number;
        int row;
        int col;
        int height;
        int width;
        int hp;
        int damage;

        public Warrior(int number, int row, int col, int height, int width, int hp) {
            this.number = number;
            this.row = row - 1;
            this.col = col - 1;
            this.height = height;
            this.width = width;
            this.hp = hp;
            this.damage = 0;
        }
    }
}
