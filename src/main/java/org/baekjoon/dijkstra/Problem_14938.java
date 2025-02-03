package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 다익스트라 알고리즘 - 백준: 14938번 (골드 4)
 * - 탐색 범위가 주어진 경우, 일단 다익스트라를 통해 노드에 대한 최단경로를 계산해두고 탐색 범위에 맞추어서 필터링 해주면 된다.
 * - 다익스트라를 돌리는 도중에 탐색 범위 조건을 거는 것보다, 만들어진 결과에 탐색 범위 조건을 거는 게 더 좋을 듯 하다.
 */
public class Problem_14938 {

    static int n, m, r;
    static int[] items;
    static List<Node>[] nodes;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        items = new int[n + 1];
        nodes = new List[n + 1];

        for (int i = 1; i <= n; i++) {
            nodes[i] = new ArrayList<>();
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            items[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= r; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            nodes[start].add(new Node(end, cost));
            nodes[end].add(new Node(start, cost));
        }

        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            max = Math.max(max, dijkstra(i));
        }
        System.out.println(max);
    }

    public static int dijkstra(int start) {
        int total = 0;
        int[] result = new int[n + 1];
        Arrays.fill(result, Integer.MAX_VALUE);

        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        result[start] = 0;
        queue.add(new Node(start, 0));

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

        for (int i = 1; i <= n; i++) {
            if (result[i] <= m) {
                total += items[i];
            }
        }

        return total;
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
