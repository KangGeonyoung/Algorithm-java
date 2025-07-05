package org.baekjoon.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Trie 알고리즘 - 백준: 7432번 (골드 3)
 * - 트리를 만든 후, 사전 순서로 정렬하여 출력할 때는 keySet()을 사용하여 Set을 추출하고, 추출한 Set을 List로 변환한다.
 * - 변환한 List를 Collections.sort()를 통해 정렬한다.
 * - 정렬된 key들을 StringBuilder를 사용하여 append하고, 층수는 depth를 사용하여 다음 층으로 재귀함수를 부를 때마다 depth + " " 이런 식으로 구분해주면 된다.
 * - split() 주의점
 *   - .과 같은 특수문자는 \\. 으로, \는 \\\\로 하면 된다.
 */
public class Problem_7432 {

    static int N;
    static Node root;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        root = new Node();

        // 트리 제작
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            String[] split = line.split("\\\\");
            makeTree(split);
        }

        // 트리 출력
        Node current = root;
        printOrderedTree(current, "");

        System.out.println(sb);
    }

    public static void printOrderedTree(Node current, String depth) {
        List<String> keyList = new ArrayList<>(current.child.keySet());
        Collections.sort(keyList);

        for (String key : keyList) {
            sb.append(depth).append(key).append("\n");
            printOrderedTree(current.child.get(key), depth + " ");
        }
    }

    public static void makeTree(String[] words) {
        Node current = root;

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (!current.child.containsKey(word)) {
                current.child.put(word, new Node());
            }
            current = current.child.get(word);
        }
    }

    static class Node {
        HashMap<String, Node> child;

        public Node() {
            child = new HashMap<>();
        }
    }
}
