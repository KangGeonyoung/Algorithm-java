package org.baekjoon.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Problem_16934_3 {

    static int N;
    static Node root = new Node();
    static Node cur;
    static List<String> result = new ArrayList<>();
    static HashMap<String, Integer> wordMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            boolean isFound = false;
            cur = root;

            for (int j = 0; j < line.length(); j++) {
                String digit = String.valueOf(line.charAt(j));

                if (!cur.child.containsKey(digit)) {
                    cur.child.put(digit, new Node());
                    if (!isFound) {
                        result.add(line.substring(0, j+1));
                        wordMap.put(line, 1);
                        isFound = true;
                    }
                }
                cur = cur.child.get(digit);
            }

            if (!isFound) {
                Integer curCnt = wordMap.get(line);
                if (curCnt == null) {
                    result.add(line);
                    wordMap.put(line, 1);
                    continue;
                }

                if (curCnt >= 1) {
                    result.add(line + (curCnt+1));
                    wordMap.put(line, (curCnt + 1));
                }
            }
        }

        for (String id : result) {
            System.out.println(id);
        }
    }

    static class Node {
        HashMap<String, Node> child;

        public Node() {
            child = new HashMap<>();
        }
    }
}
