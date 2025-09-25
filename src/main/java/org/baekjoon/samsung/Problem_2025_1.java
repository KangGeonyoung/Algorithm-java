package org.baekjoon.samsung;

import java.util.*;

/**
 * 삼성 2025 상반기 오전 1번 문제 : 민트 초코 우유 (미완성)
 * - https://www.codetree.ai/ko/frequent-problems/samsung-sw/problems/mint-choco-milk/description
 */
public class Problem_2025_1 {

    static int N, T;
    static Person[][] map;  // 현재 맵 상태
    static int[] direction_row = {-1, 1, 0, 0};  // 상-하-좌-우
    static int[] direction_col = {0, 0, -1, 1};


    /**
     * {신봉하는 음식 문자열, 현재 신앙심} 클래스로 맵 만들어서 초기값 넣기
     *
     * 하루
     *      1. 아침
     *          - 모든 맵을 돌며 신앙심을 1 더해주기 (보류, 어차피 대표자에게 1을 줘야 하기 때문)
     *      2. 점심
     *          - 동일 그룹 내 대표자를 선정하여, 신앙심 모으기
     *              - 방문 처리를 유지하는 bfs를 돌려서 각 그룹마다의 대표자를 선정해 대표자 큐에 넣기
     *              - 대표자 1명 선정할 때
     *                  1. 상화좌우를 비교하며 신앙심 크기 비교 실행, 비교할 때 cnt 변수를 통해 그룹 멤버수 파악해주기
     *                  2. 신앙심이 같다면 row가 작은 걸로, row가 같다면 col이 작은 걸로 선정
     *                  3. 마지막까지 남은 사람의 신앙심에 그룹 멤버수를 더해주고, 대표자 큐에 넣어주기
     *                    - 대표자 큐는 신앙심이 높은 순서 -> 행 번호 작은 순서 -> 열 번호 작은 순서
     *      3. 저녁
     *          - 대표자 큐에서 한 사람씩 꺼내서, 전파 시작
     *              - 대표자가 신봉했던 음식이 현재 본인의 맵 위치에 등록되어 있는 신봉 음식과 같다면 전파 시작
     *              1. 전파자의 전파 방향 = B % 4, 간절함 = 신앙심 - 1, 신앙심 = 1로 지정
     *                - 0 1 2 3 = 상, 하, 좌, 우
     *              2. 전파 방향으로 한 칸씩 이동
     *                  - 격자 밖이거나, 현재 간절함이 0일 경우 종료
     *                  - 이동한 칸의 신봉 음식과 전파자의 신봉 음식 비교
     *                    - 같은 경우, 전파하지 않고 다음 칸으로 패스
     *                    - 다른 경우, 전파 시작
     *                      1. 현재 나의 간절함(x) > 전파 대상의 신앙심(y) -> 강한 전파
     *                        - x = x - (y + 1) 으로 감소됨
     *                        - y = y + 1
     *                      2. x <= y -> 약한 전파
     *                        - 내 신봉 음식과 전파 대상의 신봉 음식이 합쳐진 것을 신봉하게 됨
     *                        - y = y + x
     *                        - x = 0, 전파 종료
     *      4. 하루 일과 끝
     *          - 민트초코우유, 민트초코, 민트우유, 초코우유, 우유, 초코, 민트 순서대로 신봉자들의 신앙심 총합 출력
     */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        T = sc.nextInt();

        String[][] foods = new String[N][N];
        int[][] numbers = new int[N][N];
        map = new Person[N][N];

        // 음식 이름 저장
        for (int i = 0; i < N; i++) {
            String line = sc.next();
            for (int j = 0; j < line.length(); j++) {
                String digit = String.valueOf(line.charAt(j));
                foods[i][j] = digit;
            }
        }

        // 신앙심 저장
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                numbers[i][j] = sc.nextInt();
            }
        }

        // 맵 생성하기
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = new Person(i, j, foods[i][j], numbers[i][j]);
            }
        }

        // T일 동안 반복
        for (int t = 0; t < T; t++) {
            // 1. 초기화 과정

            // 2. 점심
            boolean[][] visited = new boolean[N][N];
            PriorityQueue<Person> attackQueue = new PriorityQueue<>(new Comparator<Person>(){  // 대표자 선정 규칙
                @Override
                public int compare(Person o1, Person o2) {
                    if (o1.cnt == o2.cnt) {
                        if (o2.sum == o1.sum) {
                            if (o1.row == o2.row) {
                                return o1.col - o2.col;
                            }
                            return o1.row - o2.row;
                        }
                        return o2.sum - o1.sum;
                    }
                    return o1.cnt - o2.cnt;
                }
            });

            // 각 그룹의 대표자 찾고, 신앙심 몰아주기
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    // 방문하지 않은 경우 해당 위치 그룹에서 대표자 찾기
                    if (!visited[i][j]) {
                        Person attackPerson = findAttack(visited, map[i][j]);
                        attackQueue.add(new Person(attackPerson.row, attackPerson.col, attackPerson.food, attackPerson.sum));
                    }
                }
            }

            // 3. 저녁
            while (!attackQueue.isEmpty()) {
                Person attackPerson = attackQueue.poll();
                // 대표자가 신봉했던 음식이 현재 본인의 맵 위치에 등록되어 있는 신봉 음식과 다르다면 전파 실패
                if (!attackPerson.food.equals(map[attackPerson.row][attackPerson.col].food)) {
                    continue;
                }

                int attackDir = map[attackPerson.row][attackPerson.col].sum % 4;
                int attackAmount = map[attackPerson.row][attackPerson.col].sum - 1;  // 간절함
                map[attackPerson.row][attackPerson.col].sum = 1;

                while (true) {
                    // 간절함이 0일 경우 종료
                    if (attackAmount == 0) {
                        break;
                    }
                    // 공격할 다음칸 위치 계산
                    int next_row = attackPerson.row + direction_row[attackDir];
                    int next_col = attackPerson.col + direction_col[attackDir];

                    if (isValid(next_row, next_col)) {
                        Person attackedPerson = map[next_row][next_col];

                        // 이동한 칸에 있는 사람 음식이 공격자와 같다면
                        if (attackPerson.food.equals(attackedPerson.food)) {
                            attackPerson.row = next_row;
                            attackPerson.col = next_col;
                            continue;
                        } else {
                            if (attackAmount > attackedPerson.sum) {  // 강한 전파일 경우
                                attackAmount = attackAmount - (attackedPerson.sum + 1);
                                attackedPerson.sum += 1;
                                attackedPerson.food = attackPerson.food;
                                attackedPerson.cnt = attackPerson.cnt;
                            } else {  // 약한 전파일 경우
                                attackedPerson.sum += attackAmount;
                                attackAmount = 0;
                                attackedPerson.food = getNewFood(attackPerson.food, attackedPerson.food);
                                attackedPerson.cnt = attackedPerson.food.length();
                            }

                            // 전파 공격 후 이동시키기
                            attackPerson.row = next_row;
                            attackPerson.col = next_col;
                        }
                    } else {  // 격자 밖일 경우 종료
                        break;
                    }
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    System.out.print(map[i][j].food + map[i][j].sum + " ");
                }
                System.out.println();
            }

            // 4. 하루 일과 끝
            //   - 민트초코우유, 민트초코, 민트우유, 초코우유, 우유, 초코, 민트 순서대로 신봉자들의 신앙심 총합 출력
            resultCalculate();
            System.out.println();
        }
    }

    public static void resultCalculate() {
        int TCM = 0;
        int TC = 0;
        int TM = 0;
        int CM = 0;
        int M = 0;
        int C = 0;
        int T = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j].food.equals("TCM")) {
                    TCM += map[i][j].sum;
                } else if (map[i][j].food.equals("TC")) {
                    TC += map[i][j].sum;
                } else if (map[i][j].food.equals("TM")) {
                    TM += map[i][j].sum;
                } else if (map[i][j].food.equals("CM")) {
                    CM += map[i][j].sum;
                } else if (map[i][j].food.equals("M")) {
                    M += map[i][j].sum;
                } else if (map[i][j].food.equals("C")) {
                    C += map[i][j].sum;
                } else if (map[i][j].food.equals("T")) {
                    T += map[i][j].sum;
                }
            }
        }

        System.out.println(TCM + " " + TC + " " + TM + " " + CM + " " + M + " " + C + " " + T);
    }

    public static String getNewFood(String food1, String food2) {
        // T C M
        String[][] foodInfo = {
                {"T", "F", "F", "T"}, {"F", "T", "F", "C"}, {"F", "F", "T", "M"},
                {"F", "T", "T", "CM"}, {"T", "F", "T", "TM"}, {"T", "T", "F", "TC"},
                {"T", "T", "T", "TCM"}
        };

        // 들어온 음식 이름을 분해해서 set에 넣어주는 과정 -> 중복이 제거됨
        Set<String> digits = new HashSet<>();
        for (int i = 0; i < food1.length(); i++) {
            String digit = String.valueOf(food1.charAt(i));
            digits.add(digit);
        }
        for (int i = 0; i < food2.length(); i++) {
            String digit = String.valueOf(food2.charAt(i));
            digits.add(digit);
        }

        String first = "";
        String second = "";
        String third = "";
        if (digits.contains("T")) {
            first = "T";
        } else {
            first = "F";
        }

        if (digits.contains("C")) {
            second = "T";
        } else {
            second = "F";
        }

        if (digits.contains("M")) {
            third = "T";
        } else {
            third = "F";
        }

        String result = "";
        for (int i = 0; i < 7; i++) {
            if (foodInfo[i][0].equals(first) && foodInfo[i][1].equals(second) && foodInfo[i][2].equals(third)) {
                result = foodInfo[i][3];
                break;
            }
        }

        return result;
    }

    public static Person findAttack(boolean[][] visited, Person startMan) {
        PriorityQueue<Person> attackQueue = new PriorityQueue<>(new Comparator<Person>(){  // 대표자 선정 규칙
            @Override
            public int compare(Person o1, Person o2) {
                if (o2.sum == o1.sum) {
                    if (o1.row == o2.row) {
                        return o1.col - o2.col;
                    }
                    return o1.row - o2.row;
                }
                return o2.sum - o1.sum;
            }
        });

        int memberCnt = 0;  // 그룹 내 멤버 수

        Queue<Person> queue = new LinkedList<>();
        visited[startMan.row][startMan.col] = true;
        queue.add(startMan);
        attackQueue.add(startMan);
        memberCnt += 1;

        // 같은 그룹 속 멤버 탐색
        while (!queue.isEmpty()) {
            Person current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int next_row = current.row + direction_row[i];
                int next_col = current.col + direction_col[i];

                if (isValid(next_row, next_col) && !visited[next_row][next_col]) {
                    // 같은 음식을 신봉하고 있어야 함
                    Person next = map[next_row][next_col];
                    if (next.food.equals(current.food)) {
                        queue.add(next);
                        attackQueue.add(next);
                        visited[next_row][next_col] = true;
                        memberCnt += 1;
                    }
                }
            }
        }

        // 대표자 선정
        Person attackPerson = attackQueue.poll();

        // 대표자에게 멤버 수만큼의 신앙심 몰아주기
        attackPerson.sum += memberCnt;

//        // 혼자라면 대표자가 될 수 없음
//        if (memberCnt == 1) {
//            return null;
//        }
        return attackPerson;
    }

    static boolean isValid(int row, int col) {
        if (row >= 0 && col >= 0 && row < N && col < N) {
            return true;
        }
        return false;
    }

    static class Person {
        int row;
        int col;
        String food;
        int sum;
        int cnt;

        public Person(int row, int col, String food, int number) {
            this.row = row;
            this.col = col;
            this.food = food;
            this.sum = number;
            this.cnt = food.length();
        }
    }
}
