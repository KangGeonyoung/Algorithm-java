package org.baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 시뮬레이션 알고리즘 - 백준: 16235 (골드 3)
 * - 시간 초과에 있어서 굉장히 까다로운 시뮬레이션 문제이다.
 * - 내가 실수한 점
 *   - PriorityQueue를 남발한 것이다.
 *   - poll()과 add() 연산이 logN 비용이 들기 때문에 남발하는 것은 시간 초과에 최악이다.
 * - Deque를 사용할 것
 *   - Deque를 사용하면 큐의 앞이나 뒤에 넣거나 뺄 수 있어서 굉장히 유연한 큐 구조이다.
 *   - 연산이 빠르기 때문에 O(1) 정도이다.
 *   - addFirst, addLast, pollFirst, pollLast 등의 함수가 존재한다.
 */
public class Problem_16235 {

    static int N, M, K;
    static int[][] amountMap;  // 양분 맵
    static Queue<Tree> initTrees = new LinkedList<>();  // 처음에 제공된 나무 정보
    static Ground[][] grounds;

    static int[] direction_row = {0, 0, 1, -1, -1, -1, 1, 1};  // 동-서-남-북-북동-북서-남동-남서
    static int[] direction_col = {1, -1, 0, 0, 1, -1, 1, -1};


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // 양분 맵
        amountMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                amountMap[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 나무 정보
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int tree_row = Integer.parseInt(st.nextToken());
            int tree_col = Integer.parseInt(st.nextToken());
            int tree_age = Integer.parseInt(st.nextToken());
            initTrees.add(new Tree(tree_row - 1, tree_col - 1, tree_age));
        }

        // 땅 초기화
        grounds = new Ground[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grounds[i][j] = new Ground();
            }
        }

        // 나무 심기
        while (!initTrees.isEmpty()) {
            Tree tree = initTrees.poll();
            grounds[tree.row][tree.col].trees.add(tree);
        }

        // 사계절 시작
        for (int i = 0; i < K; i++) {
            // 1. 봄 & 여름
            springAndSummer();

            // 2. 가을
            autumn();

            // 3. 겨울
            winter();
        }

        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Ground ground = grounds[i][j];
                result += ground.trees.size();
            }
        }
        System.out.println(result);


        /**
         * 1. 땅 초기화
         *      - 모든 칸에 양분 5를 뿌려준다.
         *      - 나무 정보에 따라 나무를 심어준다.
         * 2. 사계절(= 1년)
         *      1. 봄
         *          - 나무가 본인 칸에서 자신의 나이만큼 양분을 먹은 후, 나이가 1 증가한다.
         *          - 하나의 칸에 여러 개의 나무가 있다면 어린 나무부터 양분을 먹는다.
         *          - 양분이 부족해 자신의 나이만큼 양분을 먹을 수 없는 나무는 양분을 먹지 못하고 즉시 죽는다.
         *              - 죽은 나무 리스트에 추가해 준다.
         *      2. 여름
         *          - 죽은 나무가 양분으로 변하게 된다.
         *          - ( 죽은 나무의 나이 / 2 ) 값이 죽은 나무의 칸에 양분으로 추가된다.
         *      3. 가을
         *          - 5의 배수 나이를 가진 나무들이 번식한다.
         *          - 인접한 8개의 칸에 나이가 1인 나무들이 생긴다. (배열 범위를 벗어난 칸은 제외)
         *      4. 겨울
         *          - 로봇이 땅을 돌아다니며 amountMap에 적힌대로 양분을 추가한다.
         */
    }

    static void springAndSummer() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 땅에 나무가 존재한다면
                if (!grounds[i][j].trees.isEmpty()) {
                    Deque<Tree> trees = grounds[i][j].trees;
                    Deque<Tree> alive = new LinkedList<>();
                    int deadAmount = 0;

                    // 나무들에게 양분 배분
                    while (!trees.isEmpty()) {
                        Tree tree = trees.pollFirst();

                        if (grounds[i][j].amount >= tree.age) {
                            grounds[i][j].amount -= tree.age;
                            tree.age += 1;
                            alive.addLast(tree);
                        } else {
                            deadAmount += (tree.age / 2);
                        }
                    }

                    // 죽은 나무들의 양분을 현재 칸에 더해주기
                    grounds[i][j].amount += deadAmount;
                    grounds[i][j].trees = alive;
                }
            }
        }
    }

    static void autumn() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 땅에 나무가 존재한다면
                if (grounds[i][j].trees.size() > 0) {
                    Deque<Tree> trees = grounds[i][j].trees;
                    List<Tree> newTrees = new ArrayList<>();

                    for (Tree tree : trees) {
                        if (tree.age % 5 == 0) {  // 나이가 5의 배수라면
                            // 8개의 칸에 나이 1인 나무가 생긴다
                            for (int k = 0; k < 8; k++) {
                                int next_row = tree.row + direction_row[k];
                                int next_col = tree.col + direction_col[k];

                                // 나이가 1인 나무 번식
                                if (isValid(next_row, next_col)) {
                                    newTrees.add(new Tree(next_row, next_col, 1));
                                }
                            }
                        }
                    }

                    for (Tree newTree : newTrees) {
                        grounds[newTree.row][newTree.col].trees.addFirst(newTree);
                    }
                }
            }
        }
    }

    static void winter() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Ground ground = grounds[i][j];
                ground.amount += amountMap[i][j];
            }
        }
    }

    static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < N) {
            return true;
        }
        return false;
    }

    static class Tree {
        int row;
        int col;
        int age;
        int eat;

        public Tree(int row, int col, int age) {
            this.row = row;
            this.col = col;
            this.age = age;
            this.eat = 0;
        }
    }

    static class Ground {
        int amount;  // 양분
        Deque<Tree> trees = new LinkedList<>();

        public Ground() {
            this.amount = 5;
        }
    }
}
