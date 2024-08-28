package org.baekjoon.string;

import java.io.*;
import java.util.Stack;

/**
 * 문자열 알고리즘 - 백준: 9935번 (골드 4)
 * - 문자열 문제는 메모리 및 시간 초과 문제를 신경 써 줘야 한다.
 * - 결과를 출력할 때는 StringBuilder를 사용하여 append로 이어 붙이는 게 가장 효율적이다.
 * - String끼리의 연산은 굉장히 비효율적이다.
 * - 문자열은 자료구조를 자주 사용하는 것 같다. ex) Stack
 */
public class Problem_9935 {
    static String line;
    static char[] explosion;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        Stack<Character> stack = new Stack<>();

        // 문자열 입력 받기
        line = br.readLine();
        int line_length = line.length();

        // 폭발 문자열 입력 받기
        explosion = br.readLine().toCharArray();
        int explosion_length = explosion.length;

        // 문자열 탐색 : 스택 이용해서 실시간으로 폭발 문자열 검사
        for (int i = 0; i < line_length; i++) {
            stack.push(line.charAt(i));

            // 스택에 폭발 문자열 길이만큼 추가됐을 때 검사
            if (stack.size() >= explosion_length) {
                int matchCount = 0;

                // 폭발 문자열을 한 글자씩 검사
                for (int j = 0; j < explosion_length; j++) {
                    Character origin = stack.get(stack.size() - explosion_length + j);
                    if (explosion[j] == origin) {
                        matchCount += 1;
                    }
                }

                // 폭발 문자열을 찾은 경우, 폭발 문자열 크기만큼 pop을 실행해서 스택에서 제거해 주기
                if (matchCount == explosion_length) {
                    for (int j = 0; j < explosion_length; j++) {
                        stack.pop();
                    }
                }
            }
        }

        if (stack.isEmpty()) {
            bw.write("FRULA");
        }

        // 속도 향상을 위해 StringBuilder 사용
        StringBuilder result = new StringBuilder();
        int stack_size = stack.size();
        for (int i = 0; i < stack_size; i++) {
            result.append(stack.get(i));
        }

        // 결과 출력
        bw.write(result.toString());
        bw.close();
    }
}
