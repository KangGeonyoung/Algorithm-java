package org.baekjoon.greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Problem_1931 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int N = kb.nextInt();
        int[][] time = new int[N][2];

        for (int i = 0; i < N; i++) {
            time[i][0] = kb.nextInt();  // 시작 시간
            time[i][1] = kb.nextInt();  // 끝나는 시간
        }

        // 종료 시간으로 오름차순 정렬, 같다면 시작 시간으로 오름차순 정렬
        Arrays.sort(time, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] == o2[1]) {
                    return o1[0] - o2[0];
                }
                return o1[1] - o2[1];
            }
        });

        int count = 0;
        int startTime = 0;

        for (int i = 0; i < N; i++) {
            if (time[i][0] >= startTime) {
                count += 1;
                startTime = time[i][1];
            }
        }
        System.out.println(count);
    }
}
