package org.baekjoon.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 시뮬레이션 알고리즘 - 백준: 14891 (골드 5)
 * - 전형적인 하드코딩 시뮬레이션 문제이다.
 * - 경우의 수가 얼마 되지 않아 하드코딩 해서 풀었다.
 * - 예를 들어 문제의 조건이 상->하, 하->좌, 좌->상 이런 식으로 주어진다면 HashMap을 사용하는 게 코드도 간단해지고 편해진다.
 * - 항상 알아둬야할 건, 시뮬레이션 문제는 꼭 글로 풀어서 단계별로 적어두자.
 */
public class Problem_14891 {

    static int K;
    static Queue<Rotate> rotates = new LinkedList<>();
    static boolean[] isRotated;
    static HashMap<Integer, Integer> direction = new HashMap<>();

    static List<Integer> circle_1 = new ArrayList<>();
    static List<Integer> circle_2 = new ArrayList<>();
    static List<Integer> circle_3 = new ArrayList<>();
    static List<Integer> circle_4 = new ArrayList<>();

    static int result = 0;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 톱니바퀴 정보
        String line = br.readLine();
        for (int i = 0; i < line.length(); i++) {
            circle_1.add(Integer.parseInt(String.valueOf(line.charAt(i))));
        }

        line = br.readLine();
        for (int i = 0; i < line.length(); i++) {
            circle_2.add(Integer.parseInt(String.valueOf(line.charAt(i))));
        }

        line = br.readLine();
        for (int i = 0; i < line.length(); i++) {
            circle_3.add(Integer.parseInt(String.valueOf(line.charAt(i))));
        }

        line = br.readLine();
        for (int i = 0; i < line.length(); i++) {
            circle_4.add(Integer.parseInt(String.valueOf(line.charAt(i))));
        }

        // 회전 정보
        K = Integer.parseInt(br.readLine());
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int number = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());

            rotates.add(new Rotate(number, dir));
        }

        // 방향 정보 넣어주기
        direction.put(1, -1);
        direction.put(-1, 1);

        // 회전 명령 수행
        while (!rotates.isEmpty()) {
            isRotated = new boolean[4];

            Rotate rotate = rotates.poll();
            int number = rotate.number;
            int dir = rotate.dir;

            if (number == 1) {
                rotate_1(dir);
            } else if (number == 2) {
                rotate_2(dir);
            } else if (number == 3) {
                rotate_3(dir);
            } else {
                rotate_4(dir);
            }
        }

        // 회전이 끝난 후, 점수 계산
        calculate();
        System.out.println(result);

        /**
         * 1. K만큼 회전 시키기
         * 2. 한번 회전시킬 때
         *    - 각 톱니바퀴별로 신경써야 하는 번호가 다르다.
         *    - 자신의 번호별로 영향을 줄수 있는 번호의 톱니바퀴 회전 함수를 호출해야 한다.
         *    - 판단 방법
         *      - 자신의 양 옆의 톱니바퀴가 이번 턴동안 돌아간 적이 있는지 확인
         *      - 돌아간 적이 없다면, 본인과 맞물려 있는 톱니를 비교하여 서로 다른 극이라면 자신이 부여받은 회전방향의 반대를 매개변수로 넘겨주면서 함수를 호출한다.
         * 3. 모두 돌리고 나서 점수 계산하기
         */
    }

    public static void calculate() {
        if (circle_1.get(0) == 1) {
            result += 1;
        }

        if (circle_2.get(0) == 1) {
            result += 2;
        }

        if (circle_3.get(0) == 1) {
            result += 4;
        }

        if (circle_4.get(0) == 1) {
            result += 8;
        }
    }

    // 들어온 방향으로 한칸 회전시키기
    public static void rotateMySelf(List<Integer> circle, int dir) {
        // 시계 방향이라면
        if (dir == 1) {
            Integer last = circle.get(circle.size() - 1);
            circle.remove(circle.size() - 1);
            circle.add(0, last);
        } else {  // 반시계 방향이라면
            Integer first = circle.get(0);
            circle.remove(0);
            circle.add(first);
        }
    }

    public static void rotate_1(int dir) {
        isRotated[0] = true;

        // 2번 톱니바퀴 확인 필요
        if (!isRotated[1]) {
            // 서로 다른 극일 경우
            if (!circle_1.get(2).equals(circle_2.get(6))) {
                int reverseDir = direction.get(dir);
                rotate_2(reverseDir);
            }
        }

        // 본인 방향으로 한칸 회전 실행
        rotateMySelf(circle_1, dir);
    }

    public static void rotate_2(int dir) {
        isRotated[1] = true;

        // 1번, 3번 톱니바퀴 확인 필요

        // 1번 톱니바퀴 확인
        if (!isRotated[0]) {
            // 서로 다른 극일 경우
            if (!circle_1.get(2).equals(circle_2.get(6))) {
                int reverseDir = direction.get(dir);
                rotate_1(reverseDir);
            }
        }

        // 3번 톱니바퀴 확인
        if (!isRotated[2]) {
            // 서로 다른 극일 경우
            if (!circle_2.get(2).equals(circle_3.get(6))) {
                int reverseDir = direction.get(dir);
                rotate_3(reverseDir);
            }
        }

        // 본인 방향으로 한칸 회전 실행
        rotateMySelf(circle_2, dir);
    }

    public static void rotate_3(int dir) {
        isRotated[2] = true;

        // 2번, 4번 톱니바퀴 확인 필요

        // 2번 톱니바퀴 확인
        if (!isRotated[1]) {
            // 서로 다른 극일 경우
            if (!circle_2.get(2).equals(circle_3.get(6))) {
                int reverseDir = direction.get(dir);
                rotate_2(reverseDir);
            }
        }

        // 4번 톱니바퀴 확인
        if (!isRotated[3]) {
            // 서로 다른 극일 경우
            if (!circle_3.get(2).equals(circle_4.get(6))) {
                int reverseDir = direction.get(dir);
                rotate_4(reverseDir);
            }
        }

        // 본인 방향으로 한칸 회전 실행
        rotateMySelf(circle_3, dir);
    }

    public static void rotate_4(int dir) {
        isRotated[3] = true;

        // 3번 톱니바퀴 확인 필요
        if (!isRotated[2]) {
            // 서로 다른 극일 경우
            if (!circle_3.get(2).equals(circle_4.get(6))) {
                int reverseDir = direction.get(dir);
                rotate_3(reverseDir);
            }
        }

        // 본인 방향으로 한칸 회전 실행
        rotateMySelf(circle_4, dir);
    }

    static class Rotate {
        int number;
        int dir;

        public Rotate(int number, int dir) {
            this.number = number;
            this.dir = dir;
        }
    }
}
