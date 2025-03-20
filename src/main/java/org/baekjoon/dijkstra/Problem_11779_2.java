package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 다익스트라 알고리즘 - 백준: 11779번 (2) (골드 3)
 * - 다익스트라는 알고리즘이 복잡하니 복습해둘 것
 * - Result의 초기 값은 Integer.MAX_VALUE나 문제에서 주어지는 최댓값으로 지정할 것
 */
public class Problem_11779_2 {

    static int n;  // 도시 개수
    static int m;  // 버스 개수
    static List<Node>[] nodes;
    static Result[] results;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        nodes = new List[n + 1];
        results = new Result[n + 1];

        for (int i = 1; i <= n; i++) {
            nodes[i] = new ArrayList<>();
            results[i] = new Result();
        }

        for (int i = 1; i <= m; i++) {
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

        System.out.println(results[end].cost);
        System.out.println(results[end].path.size());
        for (int i = 0; i < results[end].path.size(); i++) {
            System.out.print(results[end].path.get(i) + " ");
        }
    }

    public static void dijkstra(int start, int end) {
        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        results[start].cost = 0;
        results[start].path.add(start);
        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.cost > results[current.index].cost) {
                continue;
            }

            for (int i = 0; i < nodes[current.index].size(); i++) {
                Node next = nodes[current.index].get(i);
                if (current.cost + next.cost < results[next.index].cost) {
                    results[next.index].cost = current.cost + next.cost;
                    results[next.index].path = new ArrayList<>(results[current.index].path);
                    results[next.index].path.add(next.index);

                    queue.add(new Node(next.index, results[next.index].cost));
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

    static class Result {
        int cost;
        List<Integer> path;

        public Result() {
            this.cost = Integer.MAX_VALUE;
            this.path = new ArrayList<>();
        }
    }
}
