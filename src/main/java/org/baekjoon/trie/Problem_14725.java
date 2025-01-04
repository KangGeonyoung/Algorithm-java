package org.baekjoon.trie;

import java.io.*;
import java.util.*;

/**
 * Trie 알고리즘 - 백준: 14725번 (골드 3)
 * - trie 알고리즘은 HashMap<String, Node> child 와 같은 구조로 트리를 만들어야 한다.
 * - 트리를 만든 후, 조건에 맞게 출력해 주면 된다.
 * - 유형 : 특정 접두사를 가진 단어 찾기, 공통 접두사 찾기, 사전순 정렬 등
 */
public class Problem_14725 {

    static int N;
    static Node root = new Node();
    static StringBuilder sb = new StringBuilder();


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Node cur = root;

        // 주어진 정보로 트리 만들기
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());

            cur = root;
            for (int j = 0; j < K; j++) {
                String info = st.nextToken();
                if (!cur.child.containsKey(info)) {
                    cur.child.put(info, new Node());
                }
                cur = cur.child.get(info);
            }
        }

        generateResult(root, "");
        bw.write(sb + "");
        bw.close();
    }

    // 만들어진 정보를 사전순으로 형식에 맞게 출력
    public static void generateResult(Node node, String floor) {
        ArrayList<String> infos = new ArrayList<>(node.child.keySet());
        Collections.sort(infos);

        for(String info : infos) {
            sb.append(floor).append(info).append("\n");
            generateResult(node.child.get(info), floor + "--");
        }
    }

    static class Node {
        HashMap<String, Node> child;

        public Node() {
            child = new HashMap<>();
        }
    }
}
