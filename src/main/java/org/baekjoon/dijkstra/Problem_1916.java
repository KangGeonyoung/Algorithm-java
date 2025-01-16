package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 다익스트라 알고리즘 - 백준: 1916번 (골드 5)
 * - 특정 목적지에 대한 최단 경로가 필요하면, 도착지점에 도달했을 경우 break문을 걸어줘야 한다.
 * - 하지만, 모든 노드의 최단 경로를 계산해야 할 때는 break문을 걸어주지 않는다.
 */
public class Problem_1916 {

    static int N, M;
    static List<Node>[] nodes;
    static int[] result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        nodes = new List[N + 1];
        result = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            nodes[i] = new ArrayList<>();
            result[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            nodes[start].add(new Node(end, cost));
        }

        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        dijkstra(start, end);

        System.out.println(result[end]);
    }

    public static void dijkstra(int start, int end) {
        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);

        result[start] = 0;
        Node startNode = new Node(start, 0);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.index == end) {
                break;
            }

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
