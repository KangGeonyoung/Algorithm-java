package org.baekjoon.priorityqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 우선순위 큐 알고리즘 - 백준: 15903번 문제 (실버 1)
 * - 내가 실수한 점
 *   - 연산의 범위가 int형을 벗어난 걸 놓쳐서 int형을 썼다.
 *   - 주어진 변수의 범위를 봤을 때, 뭔가 10^9를 벗어날 것 같으면 long 타입을 사용해주자.
 */
public class Problem_15903 {

    static int n, m;
    static PriorityQueue<Long> ascQueue = new PriorityQueue<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            long cardNum = Long.parseLong(st.nextToken());
            ascQueue.add(cardNum);
        }

        for (int i = 0; i < m; i++) {
            long first = ascQueue.poll();
            long second = ascQueue.poll();
            long sum = first + second;

            ascQueue.add(sum);
            ascQueue.add(sum);
        }

        long result = 0;
        while (!ascQueue.isEmpty()) {
            long num = ascQueue.poll();
            result += num;
        }

        System.out.println(result);

        /**
         * 1. 카드 중 가장 작은 값 2개를 꺼낸다.
         * 2. 꺼낸 카드 2개를 더해서 해당 값을 큐에 두번 넣는다.
         */
    }
}
