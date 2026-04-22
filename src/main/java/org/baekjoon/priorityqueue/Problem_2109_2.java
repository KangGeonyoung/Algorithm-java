package org.baekjoon.priorityqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 우선순위 큐 알고리즘 - 백준: 2109번 문제(2) (골드 3)
 * - 이전에 내가 풀었던 방식보다 훨씬 간단한 로직이다.
 * - 로직
 *   1. 날짜순으로 강의를 정렬하여 순서대로 하나씩 먹는다.
 *   2. 먹고나서 day와 현재 먹은 강의들의 개수를 비교한다.
 *   3. day를 초과했다면 pay가 낮은 강의를 제거한다. (pay 오름차순 우선순위 큐 사용)
 *   4. 다 먹었으면 total 계산
 */
public class Problem_2109_2 {

    static int N;

    static PriorityQueue<Lesson> lessons = new PriorityQueue<>(new Comparator<>() {
        @Override
        public int compare(Lesson o1, Lesson o2) {
            return o1.day - o2.day;
        }
    });

    static PriorityQueue<Lesson> queue = new PriorityQueue<>(new Comparator<>() {
        @Override
        public int compare(Lesson o1, Lesson o2) {
            return o1.pay - o2.pay;
        }
    });

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int pay = Integer.parseInt(st.nextToken());
            int day = Integer.parseInt(st.nextToken());

            lessons.add(new Lesson(pay, day));
        }

        while (!lessons.isEmpty()) {
            Lesson lesson = lessons.poll();

            queue.add(lesson);
            if (queue.size() > lesson.day) {
                queue.poll();
            }
        }

        int total = 0;
        while (!queue.isEmpty()) {
            total += queue.poll().pay;
        }

        System.out.println(total);
    }

    static class Lesson {
        int pay;
        int day;

        public Lesson(int pay, int day) {
            this.pay = pay;
            this.day = day;
        }
    }
}
