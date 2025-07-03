package org.baekjoon.priorityqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 우선순위 큐 알고리즘 - 백준: 1374번 문제 (골드 5)
 * - 우선순위 큐를 2개 사용하는 문제이다.
 * - 시간초과가 나는 경우, 우선순위 큐를 2개 사용하는 방법도 생각해 보자.
 */
public class Problem_1374 {

    static int N;
    static PriorityQueue<Class> lectures = new PriorityQueue<>(new Comparator<Class>(){
        @Override
        public int compare(Class o1, Class o2) {
            if (o1.start == o2.start) {
                return (int) (o1.end - o2.end);
            }
            return (int) (o1.start - o2.start);
        }
    });
    static PriorityQueue<Long> pq = new PriorityQueue<>();  // 종료 시간 관리용 큐

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            long number = Long.parseLong(st.nextToken());
            long start = Long.parseLong(st.nextToken());
            long end = Long.parseLong(st.nextToken());

            lectures.add(new Class(number, start, end));
        }

        while (!lectures.isEmpty()) {
            Class lecture = lectures.poll();
            long number = lecture.number;
            long start = lecture.start;
            long end = lecture.end;

            // 재사용 가능한 강의실이 존재하는 경우
            if (!pq.isEmpty() && pq.peek() <= start) {
                pq.poll();
            }

            // 새로운 강의실 추가 or 강의실 갱신
            pq.add(end);
        }

        System.out.println(pq.size());
    }

    static class Class {
        long number;
        long start;
        long end;

        public Class(long number, long start, long end) {
            this.number = number;
            this.start = start;
            this.end = end;
        }
    }
}
