package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Problem_11779_7 {

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
        for (int i = 0; i <= n; i++) {
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

        dijkstra(start);

        System.out.println(results[end].total);
        List<Integer> path = results[end].path;
        System.out.println(path.size());
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i) + " ");
        }
    }

    public static void dijkstra(int start) {
        Queue<Node> queue = new LinkedList<>();
        results[start].total = 0;
        results[start].path.add(start);

        queue.add(new Node(start, 0));
        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.cost > results[current.end].total) {
                continue;
            }

            for (int i = 0; i < nodes[current.end].size(); i++) {
                Node next = nodes[current.end].get(i);

                if (current.cost + next.cost < results[next.end].total) {
                    results[next.end].total = current.cost + next.cost;
                    results[next.end].path = new ArrayList<>(results[current.end].path);
                    results[next.end].path.add(next.end);

                    queue.add(new Node(next.end, results[next.end].total));
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
        int total;
        List<Integer> path = new ArrayList<>();

        public Result() {
            this.total = Integer.MAX_VALUE;
            this.path = new ArrayList<>();
        }
    }
}
