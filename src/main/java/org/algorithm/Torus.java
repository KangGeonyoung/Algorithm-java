package org.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 토러스 구조 시뮬레이션 (블록 밀기 시뮬레이션)
 * - 토러스 구조란 배열 범위 밖으로 나가게 되어도 다시 반대편에서 나타나는 구조이다.
 * - 토러스 구조는 어려운 시뮬레이션 문제에서 자주 등장하는 개념이다.
 * - 블록 한칸 밀기
 *   - 일단 인풋을 입력 받을 때, 물건의 번호에 따라 좌표를 관리하는 객체를 만들어야 한다. -> 객체 배열 형태로 구현
 *   1. 이동할 물건의 번호를 사용해서 현재 해당 물건의 좌표 받아오기
 *   2. 다음으로 이동할 좌표 계산
 *      - 토러스 구조를 위해 일반적인 다음 위치 좌표 계산에 %N을 붙여 순환시키도록 한다.
 *      - 만약 %N의 결과값이 음수라면 +N을 통해 순환할 수 있도록 하자.
 *   3. 연쇄 이동 전에 현재 물건의 좌표를 0으로 비워줘야 한다.
 *   4. 현재 물건 번호를 moved Set에 추가해준다.
 *      - 연쇄 이동할 때 현재 이동 중인 물건인지 확인하기 위함
 *   5. 다음 이동할 좌표로 해당 좌표에 다른 값이 존재하는지 검사
 *      - 만약 다른 물건이 존재한다면, 해당 물건에게 이동 명령을 내린다.
 *   6. 연쇄 이동을 마쳤다면
 *      - 현재 내 물건의 좌표 리스트를 다음으로 이동할 좌표 리스트로 교체해준다.
 *      - 다음으로 이동할 좌표 값으로 맵을 업데이트 해준다.
 *
 * - 내가 실수한 점
 *   - 연쇄 이동 전에 현재 물건의 자리를 비워두지 않은 것 -> 비워두지 않으면 다른 물건들의 연쇄 이동 후 흔적을 현재 물건이 묻어버리게 된다.
 *   - moved라는 set을 이용하지 않은 것 -> moved를 사용함으로써, 연쇄 이동 중에 이동 중인 물건들을 파악할 수 있다.
 *
 *
 * Input)
 * 5
 * 1
 * 2 1 1 0 0
 * 2 1 1 0 0
 * 0 0 0 0 0
 * 3 0 0 0 0
 * 3 3 0 0 0
 * 2 1 2
 *
 * 5
 * 1
 * 2 1 1 0 0
 * 2 0 1 1 0
 * 3 3 0 0 0
 * 0 3 0 0 0
 * 0 0 0 0 0
 * 1 1 2
 */
public class Torus {

    static int N, Q;
    static int[][] map;
    static Position[] positions;
    static Set<Integer> moved = new HashSet<>();

    static int[] direction_row = {-1, 1, 0, 0};  // 상하좌우
    static int[] direction_col = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        Q = Integer.parseInt(br.readLine());

        map = new int[N][N];
        positions = new Position[N + 1];
        for (int i = 0; i <= N; i++) {
            positions[i] = new Position();
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                int tag = map[i][j];
                if (tag > 0) {
                    positions[tag].tag = tag;
                    positions[tag].nodes.add(new Node(i, j));
                }
            }
        }

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int tag = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            moved.clear();
            // dist만큼 한칸씩 이동
            for (int j = 0; j < dist; j++) {
                moveNode(tag, dir);
            }

            printMap();
        }
    }

    public static void moveNode(int tag, int dir) {
        if (positions[tag].nodes.size() == 0) {
            return;
        }

        // 현재 좌표를 사용해서 이동할 다음 좌표 계산
        List<Node> cur = positions[tag].nodes;
        List<Node> next = new ArrayList<>();

        for (Node node : cur) {
            int next_row = (node.row + direction_row[dir]) % N;
            int next_col = (node.col + direction_col[dir]) % N;

            if (next_row < 0) {
                next_row += N;
            }
            if (next_col < 0) {
                next_col += N;
            }

            next.add(new Node(next_row, next_col));
        }

        // 연쇄 이동 전에, 먼저 원래 있던 좌표를 0으로 처리
        for(Node node : cur) {
            map[node.row][node.col] = 0;
        }

        // 경로 중 다른 물체를 발견했을 경우, 연쇄 이동 발생
        moved.add(tag);

        for(Node node : next) {
            int next_num = map[node.row][node.col];  // 계산된 이동할 다음 좌표에 있는 값
            // 내가 이동할 곳에 다른 물체가 있고, 그 물체가 내 tag도 아닌 경우
            if (next_num != 0 && !moved.contains(next_num)) {
                moveNode(next_num, dir);
            }
        }

        // 다음 좌표로 이동 시작
        // 현재 물체 객체의 좌표 리스트를 새로운 좌표 리스트로 교체
        positions[tag].nodes = new ArrayList<>(next);

        // 새로운 이동 좌표로 맵 업데이트
        for(Node node : next) {
            map[node.row][node.col] = tag;
        }
    }

    public static void printMap() {
        System.out.println();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static class Position {
        int tag;
        List<Node> nodes;

        public Position() {
            this.tag = 0;
            this.nodes = new ArrayList<>();
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
