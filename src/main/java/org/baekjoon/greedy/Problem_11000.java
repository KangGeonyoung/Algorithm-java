package org.baekjoon.greedy;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Problem_11000 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[][] schedule = new int[N][2];
        PriorityQueue<Integer> pq = new PriorityQueue<>();  // 낮은 숫자가 높은 우선순위

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            schedule[i][0] = Integer.parseInt(st.nextToken());  // 시작 시간
            schedule[i][1] = Integer.parseInt(st.nextToken());  // 종료 시간
        }

        Arrays.sort(schedule, new Comparator<int[]>() {  // 시작 시간으로 오름차순 정렬, 같으면 종료 시간으로 정렬
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });

        for (int i = 0; i < N; i++) {
            if (pq.size() == 0) {  // 첫 번째 값일 경우
                pq.add(schedule[i][1]);
                continue;
            }

            if (pq.peek() > schedule[i][0]) {
                pq.add(schedule[i][1]);
            } else {
                pq.poll();
                pq.add(schedule[i][1]);
            }
        }
        bw.write(Integer.toString(pq.size()));
        bw.close();
    }
}
