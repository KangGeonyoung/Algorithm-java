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
 * - List<Node>[]과 같은 타입을 초기화 해줄 때는 Arrays.fill과 같은 함수 사용하지 말고, for문으로 초기화 해주기.
 */
public class Problem_1753_2 {

    static int V, E, K;
    static List<Node>[] nodes;
    static int[] result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(br.readLine());

        nodes = new List[V + 1];
        result = new int[V + 1];

        for (int i = 1; i <= V; i++) {
            nodes[i] = new ArrayList<>();
            result[i] = Integer.MAX_VALUE;
        }

        // 그래프 생성
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            nodes[u].add(new Node(v, w));
        }

        dijkstra();

        for (int i = 1; i <= V; i++) {
            if (result[i] == Integer.MAX_VALUE) {
                System.out.println("INF");
            } else {
                System.out.println(result[i]);
            }
        }
    }

    public static void dijkstra() {
        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.weight - o2.weight);

        result[K] = 0;
        Node start = new Node(K, 0);
        queue.add(start);

        while(!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < nodes[current.index].size(); i++) {
                Node next = nodes[current.index].get(i);
                if (current.weight + next.weight < result[next.index]) {
                    result[next.index] = current.weight + next.weight;
                    queue.add(new Node(next.index, result[next.index]));
                }
            }
        }
    }

    static class Node {
        int index;
        int weight;

        public Node(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }
    }
}
