package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 다익스트라 알고리즘 - 백준: 18352 (실버 2)
 * - 일반적인 다익스트라 알고리즘이다.
 * - 정렬이 필요할 때는 PriorityQueue를 사용하자.
 */
public class Problem_18352 {

    static int N, M, K, X;
    static List<Node>[] nodes;
    static int[] result;
    static PriorityQueue<Integer> output = new PriorityQueue<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        nodes = new List[N + 1];
        result = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            nodes[i] = new ArrayList<>();
            result[i] = 1000000;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            nodes[A].add(new Node(B, 1));
        }

        dijkstra();

        for (int i = 1; i <= N; i++) {
            if (result[i] != 1000000 && result[i] == K) {
                output.add(i);
            }
        }

        if (output.size() == 0) {
            System.out.println(-1);
        } else {
            for (Integer number : output) {
                System.out.println(number);
            }
        }
    }

    public static void dijkstra() {
        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        result[X] = 0;
        queue.add(new Node(X, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < nodes[current.index].size(); i++) {
                Node next = nodes[current.index].get(i);
                if (current.cost + next.cost < result[next.index]) {
                    result[next.index] = current.cost + next.cost;
                    queue.add(new Node(next.index, result[next.index]));
                }
            }
        }
    }

    static class Node {
        int index;
        int cost;

        public Node(int index, int cost) {
            this.index = index;
            this.cost = cost;
        }
    }
}
