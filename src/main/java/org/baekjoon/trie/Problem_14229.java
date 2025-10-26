package org.baekjoon.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Trie 알고리즘 - 백준: 14229번 (골드 3)
 * - 트라이 알고리즘 보다는 조합 문제에 가까운 문제이다.
 * - 1자리 ~ 원하는 조건이 나올 때까지 자릿수를 추가하면서 가능한 조합을 통해 결과를 출력하는 문제이다.
 * - 조합 만드는 방법이 조금 색다른 문제였다.
 * - 내가 실수한 점
 *   - 나는 모든 조합을 먼저 만들어 두고 판별하려고 했다.
 *   - 밑에 코드는 조합을 한자리부터 만들면서 서서히 판별해 나가는 방식이어서 더 빠르다.
 */
public class Problem_14229 {

    static String S;
    static String[] dna = {"A", "C", "G", "T"};


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        S = br.readLine();

        for (int i = 1; i <= 10; i++) {
            String result = findMissing(i);
            if (result != null) {
                System.out.println(result);
                return;
            }
        }
    }

    public static String findMissing(int len) {
        Queue<String> queue = new LinkedList<>();
        queue.add("");

        while (!queue.isEmpty()) {
            String cur = queue.poll();

            if (cur.length() == len) {
                if (!S.contains(cur)) {
                    return cur;
                }
                continue;
            }

            for (String digit : dna) {
                queue.add(cur + digit);
            }
        }

        return null;
    }

}
