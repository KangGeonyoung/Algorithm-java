package org.baekjoon.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Problem_11779_6 {

    static int n, m;
    static List<Bus>[] buses;
    static Result[] results;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        buses = new List[n + 1];
        results = new Result[n + 1];

        // 초기화
        for (int i = 1; i <= n; i++) {
            buses[i] = new ArrayList<>();
            results[i] = new Result();
        }

        // 버스 정보로 그래프 만들기
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            buses[start].add(new Bus(end, cost));
        }

        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        dijkstra(start);

        System.out.println(results[end].total);
        List<Integer> path = results[end].path;
        System.out.println(path.size());
        for (Integer number : path) {
            System.out.print(number + " ");
        }
    }

    public static void dijkstra(int start) {
        Queue<Bus> queue = new LinkedList<>();
        queue.add(new Bus(start, 0));

        results[start].total = 0;
        results[start].path.add(start);

        while (!queue.isEmpty()) {
            Bus current = queue.poll();

            if (current.cost > results[current.index].total) {
                continue;
            }

            for (int i = 0; i < buses[current.index].size(); i++) {
                Bus next = buses[current.index].get(i);

                if (current.cost + next.cost < results[next.index].total) {
                    results[next.index].total = current.cost + next.cost;
                    results[next.index].path = new ArrayList<>(results[current.index].path);
                    results[next.index].path.add(next.index);

                    queue.add(new Bus(next.index, results[next.index].total));
                }
            }
        }
    }

    static class Bus {
        int index;
        int cost;

        public Bus(int index, int cost) {
            this.index = index;
            this.cost = cost;
        }
    }

    static class Result {
        int total;
        List<Integer> path;

        public Result() {
            total = Integer.MAX_VALUE;
            path = new ArrayList<>();
        }
    }
}
