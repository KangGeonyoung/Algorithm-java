package org.baekjoon.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 그래프 알고리즘 - 백준: 11725번 (실버 2)
 * - 꽤나 시간이 많이 들었던 그래프 문제이다.
 * - 다익스트라 할때와 마찬가지로 그래프를 만들어 주고, 탐색을 할 때는 재귀를 이용하면 된다.
 * - 내가 실수한 점
 *   - 그래프를 만든 후 탐색할 때, 재귀를 이용하지 않은 점
 *   - 위에서부터 탐색할 때 방문 처리를 이용해서 위로 역행하지 않도록 막아주면 된다.
 */
public class Problem_11725 {

    static int N;
    static List<Node>[] nodes;
    static int[] result;
    static boolean[] visited;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        nodes = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            nodes[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            nodes[start].add(new Node(end));
            nodes[end].add(new Node(start));
        }

        result = new int[N + 1];
        visited = new boolean[N + 1];

        search(1);

        for (int i = 2; i <= N; i++) {
            System.out.println(result[i]);
        }
    }

    public static void search(int index) {
        for (int i = 0; i < nodes[index].size(); i++) {
            Node next = nodes[index].get(i);

            if (!visited[next.index]) {
                result[next.index] = index;
                visited[next.index] = true;
                search(next.index);
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
