package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Problem_11779_5 {

    static int n, m;
    static List<Node>[] nodes;
    static Result[] results;
    static int start, end;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        // 초기화 과정
        nodes = new List[n + 1];
        results = new Result[n + 1];

        for (int i = 0; i < n + 1; i++) {
            nodes[i] = new ArrayList<>();
        }
        for (int i = 0; i < n + 1; i++) {
            results[i] = new Result();
        }

        // 버스 정보로 간선 만들기
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            nodes[start].add(new Node(end, cost));
        }

        // 출발점, 도착점 입력 받기
        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        // 다익스트라 실행
        dijkstra();

        // 결과 출력
        System.out.println(results[end].cost);
        List<Integer> path = results[end].path;
        System.out.println(path.size());
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i) + " ");
        }
    }

    static void dijkstra() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start, 0));
        results[start].cost = 0;
        results[start].path.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // results에 담겨있는 cost가 더 작은 경우, 밑으로 갈 필요가 없다.
            if (results[current.end].cost < current.cost) {
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
