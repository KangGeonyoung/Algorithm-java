package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 다익스트라 알고리즘 - 백준: 11779번 (골드 3)
 * - 최소 경로를 기록하고 싶을 때는 List<Integer>를 사용하자.
 *   - path = new ArrayList<>(path_copy);
 * - 시간 초과가 날 경우, 간선에 대한 코스트보다 result의 코스트가 더 낮은 경우 continue 실행할 것
 */
public class Problem_11779 {

    static int n, m;
    static int start, end;
    static List<Node>[] nodes;
    static Result[] results;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());;

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
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        dijkstra();

        System.out.println(results[end].cost);
        System.out.println(results[end].path.size());
        for(Integer path : results[end].path) {
            System.out.print(path + " ");
        }
    }

    public static void dijkstra() {
        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        results[start].cost = 0;
        results[start].path.add(start);
        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // 검사하려는 간선의 코스트보다 현재 계산해둔 최소 경로 results보다 크다면 패스
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
            cost = Integer.MAX_VALUE;
            path = new ArrayList<>();
        }
    }
}
