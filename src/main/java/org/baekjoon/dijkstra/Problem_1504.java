package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 다익스트라 알고리즘 - 백준: 1504번 (골드 4)
 * - 특정 노드를 경유해서 최단경로를 찾는 문제이다.
 * - 따라서 많은 엣지 케이스가 존재하기 때문에 구조를 잘 짜야 한다.
 * - 만약 코드에 수많은 if문이 쓰이고 있다면 정답에서 멀어지고 있을 확률이 클 수도 있다.
 * - 주어진 변수의 범위 특히 최솟값, 최댓값을 테스트 케이스로 꼭 사용해보자. ex) 0 ≤ E ≤ 200,000
 */
public class Problem_1504 {

    static int N, E;
    static int v1, v2;
    static List<Node>[] nodes;
    static int[] result, result1, result2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        init();

        for (int i = 1; i <= E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            nodes[a].add(new Node(b, c));
            nodes[b].add(new Node(a, c));
        }

        st = new StringTokenizer(br.readLine());
        v1 = Integer.parseInt(st.nextToken());
        v2 = Integer.parseInt(st.nextToken());

        result = dijkstra(1);
        result1 = dijkstra(v1);
        result2 = dijkstra(v2);

        int output = -1;
        output = Math.min(result[v1] + result1[v2] + result2[N], result[v2] + result2[v1] + result1[N]);
        if (output >= 200000000) {
            output = -1;
        }
        System.out.println(output);
    }

    public static int[] dijkstra(int start) {
        int[] result = new int[N + 1];
        Arrays.fill(result, 200000000);

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

        return result;
    }

    public static void init() {
        nodes = new List[N + 1];

        for (int i = 1; i <= N; i++) {
            nodes[i] = new ArrayList<>();
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
