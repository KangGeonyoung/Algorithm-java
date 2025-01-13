package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 다익스트라 알고리즘 - 백준: 1753번 (골드 4)
 * - BFS와 비슷한 구조를 가지고 있음.
 * - 간선에 대한 노트 리스트를 만들어서 정보를 저장해야 한다.
 * - 시작점 노드부터 출발해서 갈 수 있는 노드들을 파악한 후, 그 노드에 갔을 때 최소 거리라면 가준다.
 * - 큐를 사용한다.
 */
public class Problem_1753 {

    static int V, E, K;
    static List<Node>[] nodes;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(br.readLine());

        nodes = new ArrayList[V + 1];
        int[] result = new int[V + 1];

        for (int i = 1; i <= V; i++) {
            nodes[i] = new ArrayList<>();
            result[i] = Integer.MAX_VALUE;
        }

        for (int i = 1; i <= E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            nodes[u].add(new Node(v, w));
        }

        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.weight - o2.weight);
        result[K] = 0;
        queue.add(new Node(K, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < nodes[current.end].size(); i++) {
                Node next = nodes[current.end].get(i);
                if (current.weight + next.weight < result[next.end]) {
                    result[next.end] = current.weight + next.weight;
                    queue.add(new Node(next.end, result[next.end]));
                }
            }
        }

        for (int i = 1; i <= V; i++) {
            if (result[i] == Integer.MAX_VALUE) {
                System.out.println("INF");
            } else {
                System.out.println(result[i]);
            }
        }
    }

    static class Node {
        int end;
        int weight;

        public Node(int end, int weight) {
            this.end = end;
            this.weight = weight;
        }
    }
}
