package org.baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 이분 탐색 알고리즘 - 백준: 2343번 (실버 1)
 * - 실버지만, 가장 헷갈렸던 이분 탐색 문제이다.
 * - 문제에서는 M개의 블루레이에 담고 싶다고 했지만, cnt <= M 이라는 조건을 사용해야 정답으로 통과됐다.
 * - 내가 실수한 점
 *   - start를 당연하게 1로 잡은 것 -> 문제를 잘 읽어보면 블루레이의 최소 크기는 강의 중 가장 시간이 긴 동영상의 크기였다.
 * - start와 end 초기값을 지정할 때, 유심히 생각할 필요가 있다.
 */
public class Problem_2343 {

    static int N, M;
    static List<Long> lessons = new ArrayList<>();
    static long total = 0;
    static long maxLesson = 0;
    static long result = Long.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            long lesson = Long.parseLong(st.nextToken());
            lessons.add(lesson);
            total += lesson;
            maxLesson = Math.max(maxLesson, lesson);
        }

        long start = maxLesson;
        long end = total;

        while (start <= end) {
            long mid = (start + end) / 2;

            if (isPossible(mid)) {
                result = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        System.out.println(result);
    }

    public static boolean isPossible(long size) {
        long cnt = 1;
        long sum = 0;

        for (long lesson : lessons) {
            if (sum + lesson > size) {
                cnt += 1;
                sum = lesson;
            } else {
                sum += lesson;
            }
        }

        return cnt <= M;
    }
}
