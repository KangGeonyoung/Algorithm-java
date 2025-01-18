package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 다익스트라 알고리즘 - 백준: 13549번 (골드 5)
 * - 방향에 따른 가중치가 주어진 경우, 해당 케이스에 따라 큐를 추가하는 코드를 작성해 주면 된다.
 * - 하지만 큐에 노드를 추가하기 전, 결과 값을 업데이트 해주고 추가해줘야 함을 잊지 말자.
 * - 예시 케이스만 생각하지 말고 항상 엣지 케이스도 생각해보자.
 */
public class Problem_13549 {

    static int N, K;
    static int[] result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        result = new int[100000 + 1];

        Arrays.fill(result, Integer.MAX_VALUE);

        if (N > K) {
            System.out.println(N - K);
        } else {
            dijkstra();
            System.out.println(result[K]);
        }
    }

    public static void dijkstra() {
        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.time - o2.time);
        result[N] = 0;
        queue.add(new Node(N, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            int forward_position = current.index + 1;
            if (isValid(forward_position) && result[forward_position] > current.time + 1) {
                result[forward_position] = current.time + 1;
                queue.add(new Node(forward_position, result[forward_position]));
            }

            int back_position = current.index - 1;
            if (isValid(back_position) && result[back_position] > current.time + 1) {
                result[back_position] = current.time + 1;
                queue.add(new Node(back_position, result[back_position]));
            }

            int teleport_position = current.index * 2;
            if (isValid(teleport_position) && result[teleport_position] > current.time) {
                result[teleport_position] = current.time;
                queue.add(new Node(teleport_position, result[teleport_position]));
            }
        }
    }

    public static boolean isValid(int index) {
        if (index >= 0 && index <= 100000) {
            return true;
        }
        return false;
    }

    static class Node {
        int index;
        int time;

        public Node(int index, int time) {
            this.index = index;
            this.time = time;
        }
    }
}
