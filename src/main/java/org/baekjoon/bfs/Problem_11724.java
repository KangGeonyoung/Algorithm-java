package org.baekjoon.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * BFS 알고리즘 - 백준: 11724번 문제 (실버 2)
 * - 그래프, BFS가 섞인 문제이다.
 * - 연결 요소의 개수를 구해야 하는데 문젠데, 방향성이 없는 그래프일 때는 양방향으로 그래프를 만들어 줘야 한다.
 * - 예) 1->2->3과 3->2->1은 서로 다르게 인식되기 때문
 * - 내가 실수한 점
 *   - 양방향으로 연결 안한 점
 *   - 고립된 정점을 아예 배제를 한 점 -> 고립된 정점도 하나의 연결 요소로 취급해야 한다.
 */
public class Problem_11724 {

    static int N, M;
    static List<Node>[] nodes;
    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        nodes = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            nodes[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            nodes[start].add(new Node(end));
            nodes[end].add(new Node(start));
        }

        boolean[] visited = new boolean[N + 1];
        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                search(i, visited);
                result += 1;
            }
        }

        System.out.println(result);
    }

    public static void search(int start, boolean[] visited) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start));
        visited[start] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < nodes[current.index].size(); i++) {
                Node next = nodes[current.index].get(i);

                if (!visited[next.index]) {
                    visited[next.index] = true;
                    queue.add(new Node(next.index));
                }
            }
        }
    }

    static class Node {
        int index;

        public Node(int index) {
            this.index = index;
        }
    }
}
