package org.baekjoon.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Problem_16934_4 {

    static int N;
    static HashMap<String, Integer> store = new HashMap<>();
    static Node root = new Node();
    static List<String> result = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        Node cur;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            cur = root;
            boolean isInit = true;
            for (int j = 0; j < line.length(); j++) {
                String key = String.valueOf(line.charAt(j));

                if (!cur.child.containsKey(key)) {
                    cur.child.put(key, new Node());
                    if (isInit) {
                        result.add(line.substring(0, j+1));
                        if (!store.containsKey(line)) {
                            store.put(line, 1);
                        } else {
                            store.put(line, store.get(line) + 1);
                        }
                        isInit = false;
                    }
                }
                cur = cur.child.get(key);
            }

            if (isInit) {
                if (store.containsKey(line)) {
                    store.put(line, store.get(line) + 1);
                    result.add(line + (store.get(line)));
                } else {
                    store.put(line, 1);
                    result.add(line);
                }
            }
        }

        for (String str : result) {
            System.out.println(str);
        }
    }

    static class Node {
        Map<String, Node> child;

        public Node() {
            this.child = new HashMap<>();
        }
    }
}
