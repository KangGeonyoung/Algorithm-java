package org.baekjoon.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Problem_16934_2 {

    static int N;
    static Node root;
    static Node current;
    static HashMap<String, Integer> wordInfo = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        root = new Node();
        N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            String word = br.readLine();
            String result = makeTree(word);
            System.out.println(result);
        }
    }

    public static String makeTree(String word) {
        current = root;
        boolean isFlag = true;
        int resultIdx = word.length();

        for (int i = 0; i < word.length(); i++) {
            String digit = String.valueOf(word.charAt(i));

            if (!current.child.containsKey(digit)) {
                current.child.put(digit, new Node());
                if (isFlag) {
                    resultIdx = i + 1;
                    isFlag = false;
                }
            }
            current = current.child.get(digit);
        }

        // 닉네임 맵 작업 처리
        if (!wordInfo.containsKey(word)) {
            wordInfo.put(word, 1);
        } else {
            wordInfo.put(word, wordInfo.get(word) + 1);
        }

        String result = word.substring(0, resultIdx);
        if (wordInfo.get(word) > 1) {
            result += wordInfo.get(word);
        }

        return result;
    }

    static class Node {
        HashMap<String, Node> child;

        public Node() {
            child = new HashMap<>();
        }
    }
}
