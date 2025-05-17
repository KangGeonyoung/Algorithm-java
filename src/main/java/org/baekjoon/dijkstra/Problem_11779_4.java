package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Problem_11779_4 {
    static int n, m;
    static List<Node>[] nodes;
    static Result[] result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        nodes = new List[n + 1];
        result = new Result[n + 1];
        for (int i = 0; i <= n; i++) {
            nodes[i] = new ArrayList<>();
            result[i] = new Result();
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
        System.out.println(result[end].cost);
        System.out.println(result[end].path.size());
        for (Integer number : result[end].path) {
            System.out.print(number + " ");
        }
    }

    public static void dijkstra(int start) {
        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        result[start].path.add(start);
        result[start].cost = 0;
        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.cost > result[current.end].cost) {
                continue;
            }

            for (int i = 0; i < nodes[current.end].size(); i++) {
                Node next = nodes[current.end].get(i);
                if (current.cost + next.cost < result[next.end].cost) {
                    result[next.end].cost = current.cost + next.cost;
                    result[next.end].path = new ArrayList<>(result[current.end].path);
                    result[next.end].path.add(next.end);

                    queue.add(new Node(next.end, result[next.end].cost));
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
            this.cost = Integer.MAX_VALUE;
            this.path = new ArrayList<>();
        }
    }
}
