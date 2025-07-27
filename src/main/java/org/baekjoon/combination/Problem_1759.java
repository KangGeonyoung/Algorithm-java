package org.baekjoon.combination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 조합 알고리즘 - 백준: 1759 (골드 5)
 * - 조합론 문제이다.
 * - 주로 dfs를 사용해서 모든 조합을 만들고, 조합이 만들어졌을 때 조건에 맞으면 결과 리스트에 추가해 주는 방식을 많이 사용한다.
 */
public class Problem_1759 {

    static int L, C;
    static List<Character> digits = new ArrayList<>();
    static List<String> passwords = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        String line = br.readLine().replace(" ", "");
        char[] chars = line.toCharArray();
        for (int i = 0; i < C; i++) {
            digits.add(chars[i]);
        }
        Collections.sort(digits);

        // 가능한 조합 탐색
        dfs(0, "");

        // 결과 출력
        for (String password : passwords) {
            System.out.println(password);
        }
    }

    public static void dfs(int index, String password) {
        if (password.length() == L) {
            if (isValid(password)) {
                passwords.add(password);
            }
            return;
        }

        for (int i = index; i < C; i++) {
            password += digits.get(i);
            dfs(i + 1, password);
            password = password.substring(0, password.length() - 1);
        }
    }

    // 최소 1개의 모음이 포함되는지 & 모음의 개수가 최대 (L - 2)개까지 가능
    public static boolean isValid(String password) {
        String[] conditions = {"a", "e", "i", "o", "u"};
        int cnt = 0;

        for (int i = 0; i < conditions.length; i++) {
            if (password.contains(conditions[i])) {
                cnt += 1;
            }
        }

        if (cnt >= 1 && cnt <= (L - 2)) {
            return true;
        }
        return false;
    }
}
