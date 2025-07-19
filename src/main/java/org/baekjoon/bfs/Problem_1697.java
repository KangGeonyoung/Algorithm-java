package org.baekjoon.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BFS 알고리즘 - 백준: 1697번 (실버 1)
 * - 좌표를 사용하지 않는 BFS 문제이다.
 * - 경우의 수를 BFS 조건으로 걸어서 큐에 추가해주는 방식이다.
 * - 내가 실수한 점
 *   - 큐에 값을 추가해줄 때, 변수의 범위를 확인하지 않은 것
 *   - 3가지 경우의 수를 배열로 초기화 하면 더 편했을 것
 */
public class Problem_1697 {

    static int N, K;
    static int result = 0;
    static int MAX = 100000;
    static boolean[] visited = new boolean[MAX + 1];


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        bfs(N, 0);
        System.out.println(result);
    }

    public static void bfs(int current, int count) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(current, count));
        visited[current] = true;

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.index == K) {
                result = cur.count;
                break;
            }

            int[] nexts = {cur.index - 1, cur.index + 1, cur.index * 2};
            for (int next : nexts) {
                if (next >= 0 && next <= MAX && !visited[next]) {
                    visited[next] = true;
                    queue.add(new Node(next, cur.count + 1));
                }
            }
        }
    }

    static class Node {
        int index;
        int count;

        public Node(int index, int count) {
            this.index = index;
            this.count = count;
        }
    }
}
