package org.baekjoon.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Trie 알고리즘 - 백준: 16934번 (골드 3)
 * - 제일 어려웠던 Trie 문제이다.
 * - 트리용 HashMap 1개와, 중복 확인용 HashMap 1개를 함께 사용하는 문제이다.
 * - 이 문제에서는 단어마다 노드가 처음 추가되는 인덱스를 보관하여 결과값으로 리턴하는 방식이다.
 * - 내가 실수한 점
 *   - 트리는 잘 만들었지만, 노드가 처음 추가되는 인덱스 보관 로직을 떠올리지 못했다.
 *   - 중복 확인용 HashMap을 만들 생각을 하지 못했다.
 * - https://wjdtn7823.tistory.com/55
 */
public class Problem_16934 {

    static int N;
    static Node root;
    static HashMap<String, Integer> countMap = new HashMap<>();


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        root = new Node();
        for (int i = 0; i < N; i++) {
            // 한 단어마다 트리를 만들어 주기
            String word = br.readLine();
            String result = makeTree(word);
            System.out.println(result);
        }
    }

    public static String makeTree(String word) {
        // 트리에서 단어를 찾지 못해 HashMap에 노드를 처음 추가하게 되는 인덱스
        int resultIdx = word.length();
        boolean isFlag = false;

        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            String digit = String.valueOf(word.charAt(i));

            // 해당 글자가 없다면 노드 추가
            if (!current.child.containsKey(digit)) {
                current.child.put(digit, new Node());
                if (!isFlag) {
                    resultIdx = i + 1;  // substring을 사용할 것이기 때문에 +1을 해줌
                    isFlag = true;
                }
            }
            // 다음 위치로 옮겨주기
            current = current.child.get(digit);
        }

        // 해당 단어가 countMap에 없다면 새롭게 추가, 있다면 개수 1 증가
        if (!countMap.containsKey(word)) {
            countMap.put(word, 1);
        } else {
            countMap.put(word, countMap.get(word) + 1);
        }

        // 단어에서 구분되는 첫 부분까지 리턴
        String resultDigit = word.substring(0, resultIdx);
        if (countMap.get(word) > 1) {
            return resultDigit + countMap.get(word);
        }
        return resultDigit;
    }

    static class Node {
        Map<String, Node> child;
        int matchCnt;

        public Node() {
            child = new HashMap<>();
            matchCnt = 0;
        }
    }
}
