package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 다익스트라 알고리즘 - 백준: 1238번 (골드 3)
 * - X라는 도착지점이 있을 경우,
 *   - 주어진 그래프에서 X를 시작지점으로 지정하여 최단경로를 구하면, 그것은 X에서 각 노드들로 돌아오는 최단경로가 된다.
 *   - 역방향 그래프(주어진 간선의 방향을 반대로 하여 생성)에서 X를 시작지점으로 지정하여 최단경로를 구하면, 그것은 각 노드에서 X로 가는 최단경로가 된다.
 *   - 결과적으로 도착지점까지 오고 가는 최단경로를 구할 수 있게 된다.
 * - 역방향 그래프의 특징을 잘 사용하자.
 * - 참고 : https://jyeonnyang2.tistory.com/349
 */
public class Problem_1238 {

    static int N, M, X;

    static List<Node>[] nodes;
    static List<Node>[] reverse_nodes;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());  // 마을의 개수
        M = Integer.parseInt(st.nextToken());  // 도로의 개수
        X = Integer.parseInt(st.nextToken());  // 도착점

        nodes = new List[N + 1];
        reverse_nodes = new List[N + 1];

        for (int i = 1; i <= N; i++) {
            nodes[i] = new ArrayList<>();
            reverse_nodes[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            nodes[start].add(new Node(end, time));
            reverse_nodes[end].add(new Node(start, time));
        }

        int[] result;
        int[] reverse_result;
        result = dijkstra(X, nodes);
        reverse_result = dijkstra(X, reverse_nodes);

        for (int i = 1; i <= N; i++) {
            max = Math.max(max, result[i] + reverse_result[i]);
        }

        System.out.println(max);
    }

    public static int[] dijkstra(int start, List<Node>[] nodes) {
        int[] result = new int[N + 1];
        Arrays.fill(result, Integer.MAX_VALUE);

        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.time - o2.time);
        result[start] = 0;
        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < nodes[current.index].size(); i++) {
                Node next = nodes[current.index].get(i);
                if (current.time + next.time < result[next.index]) {
                    result[next.index] = current.time + next.time;
                    queue.add(new Node(next.index, result[next.index]));
                }
            }
        }

        return result;
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
