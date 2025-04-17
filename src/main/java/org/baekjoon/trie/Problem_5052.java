package org.baekjoon.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Trie 알고리즘 - 백준: 5052번 (골드 4)
 * - root를 설정하여 트리를 만드는 것에 익숙해져야 한다.
 * - 또한, 만들어진 트리를 출력하는 방식에도 익숙해져야 한다.
 * - 보통 재귀 형태의 메서드를 통해 트리를 출력할 수 있다.
 *   - 이 재귀 함수에 조건문을 걸어서 트리의 엔드포인트를 카운팅 할 수 있다.
 */
public class Problem_5052 {
    static int t, n;
    static Node root;
    static int count = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        t = Integer.parseInt(br.readLine());


        // 테스트 케이스만큼 반복
        for (int i = 0; i < t; i++) {
            // 초기화
            count = 0;
            root = new Node();
            Node cur = root;

            n = Integer.parseInt(br.readLine());

            for (int j = 0; j < n; j++) {
                String line = br.readLine();

                cur = root;
                for (int k = 0; k < line.length(); k++) {
                    int digit = Integer.parseInt(String.valueOf(line.charAt(k)));

                    if (!cur.child.containsKey(digit)) {
                        cur.child.put(digit, new Node());
                    }
                    cur = cur.child.get(digit);
                }
            }

            cur = root;
            calculate(cur);
            if (count != n) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
            }
        }
    }

    private static void calculate(Node cur) {
        ArrayList<Integer> keySet = new ArrayList<>(cur.child.keySet());
        if (keySet.size() == 0) {
            count += 1;
        }

        for(Integer num : keySet) {
            calculate(cur.child.get(num));
        }
    }

    static class Node {
        HashMap<Integer, Node> child;

        public Node() {
            child = new HashMap<>();
        }
    }
}
