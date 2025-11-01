package org.baekjoon.priorityqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 우선순위 큐 알고리즘 - 백준: 11000번 문제 (골드 4)
 * - 우선순위 큐와, 그리디 알고리즘이 섞여 있는 문제
 * - 내가 실수한 점
 *   - 문제를 잘못 읽어 강의실 수를 출력해야 하는 것을 강의 수를 출력한 것
 *   - 우선순위 큐를 2개 사용하는 것도 좋은 방법
 */
public class Problem_11000_2 {

    static int N;

    static PriorityQueue<Schedule> pq = new PriorityQueue<>(new Comparator<Schedule>(){
        @Override
        public int compare(Schedule o1, Schedule o2) {
            if (o1.start == o2.start) {
                return o1.end - o2.end;
            }
            return o1.start - o2.start;
        }
    });

    static PriorityQueue<Integer> classRoom = new PriorityQueue<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            pq.add(new Schedule(start, end));
        }

        for (int i = 0; i < N; i++) {
            Schedule lesson = pq.poll();
            // 첫 수업인 경우
            if (classRoom.size() == 0) {
                classRoom.add(lesson.end);
                continue;
            }

            // 운영 중인 교실에서 가장 빨리 끝나는 수업의 종료 시간과 비교,
            // 아직 끝날 시간이 아니라면, 교실 1개 추가
            if (classRoom.peek() > lesson.start) {
                classRoom.add(lesson.end);
            } else {
                classRoom.poll();
                classRoom.add(lesson.end);
            }
        }

        System.out.println(classRoom.size());
    }

    static class Schedule {
        int start;
        int end;

        public Schedule(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
