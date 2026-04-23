package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 다익스트라 알고리즘 - 백준: 1504번(2) (골드 4)
 * - 특정 노드를 경유해서 최단 경로를 찾는 문제이다.
 * - 발생할 수 있는 경우의 수를 고려해야 한다.
 */
public class Problem_1504_2 {

    static int N, E;
    static List<Node>[] nodes;
    static int[] results;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        nodes = new List[N + 1];
        results = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            nodes[i] = new ArrayList<>();
            results[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            nodes[start].add(new Node(end, cost));
            nodes[end].add(new Node(start, cost));
        }

        st = new StringTokenizer(br.readLine());
        int first = Integer.parseInt(st.nextToken());
        int second = Integer.parseInt(st.nextToken());

        /**
         * 1 -> first -> second -> N
         *
         * 1 -> second -> first -> N
         */


        // 1 -> first
        dijkstra(1);
        int oneToFirst = results[first];
        if (oneToFirst == Integer.MAX_VALUE) {
            System.out.println(-1);
            return;
        }

        // 1 -> second
        dijkstra(1);
        int oneToSecond = results[second];
        if (oneToSecond == Integer.MAX_VALUE) {
            System.out.println(-1);
            return;
        }

        // first -> second
        dijkstra(first);
        int firstToSecond = results[second];
        if (firstToSecond == Integer.MAX_VALUE) {
            System.out.println(-1);
            return;
        }

        // first -> N
        dijkstra(first);
        int firstToN = results[N];
        if (firstToN == Integer.MAX_VALUE) {
            System.out.println(-1);
            return;
        }

        // second -> first
        dijkstra(second);
        int secondToFirst = results[first];
        if (secondToFirst == Integer.MAX_VALUE) {
            System.out.println(-1);
            return;
        }

        // second -> N
        dijkstra(second);
        int secondToN = results[N];
        if (secondToN == Integer.MAX_VALUE) {
            System.out.println(-1);
            return;
        }

        int oneResult = oneToFirst + firstToSecond + secondToN;
        int twoResult = oneToSecond + secondToFirst + firstToN;
        int result = Math.min(oneResult, twoResult);

        System.out.println(result);
    }

    public static void dijkstra(int start) {
        for (int i = 0; i <= N; i++) {
            results[i] = Integer.MAX_VALUE;
        }
        Queue<Node> queue = new LinkedList<>();

        queue.add(new Node(start, 0));
        results[start] = 0;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.cost > results[current.end]) {
                continue;
            }

            for (int i = 0; i < nodes[current.end].size(); i++) {
                Node next = nodes[current.end].get(i);

                if (current.cost + next.cost < results[next.end]) {
                    results[next.end] = current.cost + next.cost;
                    queue.add(new Node(next.end, results[next.end]));
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
}
