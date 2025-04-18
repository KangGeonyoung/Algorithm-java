package org.baekjoon.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Trie 알고리즘 - 백준: 14426번 (실버 1)
 * - 트리를 재귀로 탐색할 때, 다음 child로 이동시켜주는 거 누락하지 않기.
 */
public class Problem_14426 {

    static Node root = new Node();
    static Node cur;
    static int N, M;

    static int invalid = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());


        // 주어진 문자열
        for (int i = 0; i < N; i++) {
            String word = br.readLine();

            cur = root;
            // 트리에 단어 삽입
            for (int j = 0; j < word.length(); j++) {
                String digit = String.valueOf(word.charAt(j));
                if (!cur.child.containsKey(digit)) {
                    cur.child.put(digit, new Node());
                }
                cur = cur.child.get(digit);
            }
        }

        // 검사해야 하는 문자열
        for (int i = 0; i < M; i++) {
            String word = br.readLine();
            cur = root;

            search(word, 0, cur);
        }

        System.out.println(M - invalid);
    }

    public static void search(String word, int index, Node cur) {
        if (word.length() == index) {
            return;
        }

        String digit = String.valueOf(word.charAt(index));
        if (!cur.child.containsKey(digit)) {
            invalid += 1;
            return;
        } else {
            search(word, index + 1, cur.child.get(digit));
        }
    }

    static class Node {
        HashMap<String, Node> child;

        public Node() {
            this.child = new HashMap<>();
        }
    }
}
