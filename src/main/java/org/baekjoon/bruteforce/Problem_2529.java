package org.baekjoon.bruteforce;

import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 완전탐색 알고리즘 - 백준: 2529번 (실버 1)
 * - 완탐이지만 백트래킹 알고리즘을 가지고 있다.
 * - 내가 실수한 것 : String에 공백을 못 봐서 공백을 사용해버렸다. -> 날린 시간 2시간
 * - input을 읽을 때 항상 조심하자.
 * - 계산할 때 int형 범위를 넘어서는지 봐야 한다. 대략 10^9을 넘어가면 long을 사용하자.
 */
public class Problem_2529 {
    static int k;
    static boolean[] visited;
    static char[] input;
    static Stack<Integer> result = new Stack<>();
    static long max = Long.MIN_VALUE;
    static long min = Long.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // k 입력 받기
        st = new StringTokenizer(br.readLine());
        k = Integer.parseInt(st.nextToken());

        // 공백 제거 후, 부등호 입력 받기
        input = br.readLine().replace(" ", "").toCharArray();

        visited = new boolean[10];  // 0-9까지의 방문 공간 생성

        for (int i = 0; i < 10; i++) {
            search(i, 0);
        }

        String MAX = String.format("%0" + (k+1) + "d", max);
        String MIN = String.format("%0" + (k+1) + "d", min);
        bw.write((MAX));
        bw.newLine();
        bw.write(MIN);
        bw.close();
    }

    public static void search(int start, int depth) {
        if (!visited[start]) {
            visited[start] = true;
            result.add(start);
        } else {
            return;
        }

        // 탐색 종료 조건
        if (depth == k) {
            long calculate = calculate(result);
            if (max < calculate) {
                max = calculate;
            }
            if (min > calculate) {
                min = calculate;
            }

            visited[start] = false;
            result.pop();
            return;
        }

        char sign = input[depth];
        if (sign == '<') {
            int next_start = start + 1;
            for (int i = next_start; i < 10; i++) {
                if (!visited[i]) {
                    search(i, depth + 1);
                }
            }
        } else {
            int next_start = start - 1;
            for (int i = next_start; i >= 0; i--) {
                if (!visited[i]) {
                    search(i, depth + 1);
                }
            }
        }

        visited[start] = false;
        result.pop();
    }

    public static long calculate(Stack<Integer> result) {
        int size = result.size();
        long unit = (long) Math.pow(10, k);

        long total = 0;
        for (int i = 0; i < size; i++) {
            long digit = (long)result.get(i);
            total += digit * unit;
            unit /= 10;
        }
        return total;
    }
}
