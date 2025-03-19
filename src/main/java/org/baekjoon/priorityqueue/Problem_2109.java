package org.baekjoon.priorityqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 우선순위 큐 알고리즘 - 백준: 2109번 문제 (골드 3)
 * - 비교 변수가 여러 개 들어가면 compare 함수를 오버라이드 하여 2차적인 정렬 조건을 넣어줘야 한다.
 * - 이 문제에서는 시간을 효율적으로 사용하기 위해 유통기간이 긴 강연을 제일 뒤에 배치하였다.
 *   - 그래서 node.day ~ 1일까지 선택지를 주며 배치하였다.
 *   - 페이가 제일 높고 유통기간이 긴 강연을 뒤에 배치하였기 때문에, 해당 위치랑 겹치는 다른 강연이 오면 그 강연은 포기해야 하는 방식이다.
 * - 코드는 복잡하지 않지만 생각을 해야하는 문제이기 때문에 골드 3인 것 같다.
 */
public class Problem_2109 {

    static int N;
    static PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.pay == o2.pay) {
                return o2.day - o1.day;
            }
            return o2.pay - o1.pay;
        }
    });
    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int pay = Integer.parseInt(st.nextToken());
            int day = Integer.parseInt(st.nextToken());

            queue.add(new Node(pay, day));
        }

        boolean[] checked = new boolean[10001];
        while (!queue.isEmpty()) {
            Node node = queue.poll();

            for (int i = node.day; i > 0; i--) {
                if (!checked[i]) {
                    checked[i] = true;
                    result += node.pay;
                    break;
                }
            }
        }

        System.out.println(result);
    }

    static class Node {
        int pay;
        int day;

        public Node(int pay, int day) {
            this.pay = pay;
            this.day = day;
        }
    }
}
