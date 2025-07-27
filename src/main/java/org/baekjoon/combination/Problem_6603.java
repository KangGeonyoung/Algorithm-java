package org.baekjoon.combination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 조합 알고리즘 - 백준: 6603 (실버 2)
 * - 조합론 문제이다.
 * - StringBuilder 사용법을 잘 알아두자.
 */
public class Problem_6603 {

    static int k;
    static List<Integer> numbers = new ArrayList<>();
    static List<String> lottos = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());
            k = Integer.parseInt(st.nextToken());
            numbers.clear();
            lottos.clear();

            // 종료 조건
            if (k == 0) {
                break;
            }

            // 주어진 번호들 담기
            for (int i = 0; i < k; i++) {
                numbers.add(Integer.parseInt(st.nextToken()));
            }

            // 6개로 된 로또 번호 만들기
            List<Integer> lotto = new ArrayList<>();
            dfs(0, lotto);

            // 생성된 번호 출력
            for (String result : lottos) {
                System.out.println(result);
            }
            System.out.println();
        }
    }

    public static void dfs(int index, List<Integer> lotto) {
        if (lotto.size() == 6) {
            StringBuilder sb = new StringBuilder();
            for (Integer number : lotto) {
                sb.append(number + " ");
            }
            lottos.add(String.valueOf(sb));
            return;
        }

        for (int i = index; i < k; i++) {
            int select = numbers.get(i);
            lotto.add(select);
            dfs(i + 1, lotto);
            lotto.remove(Integer.valueOf(select));
        }
    }
}
