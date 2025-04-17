package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 다익스트라 알고리즘 - 백준: 11779번 (3) (골드 3)
 * - result의 초기 값을 무엇으로 지정하냐에 따라 결과가 달라짐 -> 따라서 주어진 입력값의 범위를 확인해서 Integer.MAX_VALUE로 할지 다른 값으로 할지 정해야 한다.
 */
public class Problem_11779_3 {

    static int n, m;
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

        for (int i = 0; i < m; i++) {
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

            if (current.cost > results[current.end].cost) {
                continue;
            }

            for (int i = 0; i < nodes[current.end].size(); i++) {
                Node next = nodes[current.end].get(i);
                if (current.cost + next.cost < results[next.end].cost) {
                    results[next.end].cost = current.cost + next.cost;
                    results[next.end].path = new ArrayList<>(results[current.end].path);
                    results[next.end].path.add(next.end);

                    queue.add(new Node(next.end, results[next.end].cost));
                }
            }
        }
    }

    static class Node {
        int end;
        int cost;

        public Node(int end, int cost) {
            this.end = end;
            this.cost = cost;
        }
    }

    static class Result {
        int cost;
        List<Integer> path;

        public Result() {
            cost = Integer.MAX_VALUE;
            path = new ArrayList<>();
        }
    }
}
