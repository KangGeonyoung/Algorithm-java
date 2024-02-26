package org.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int N = kb.nextInt();
        int[][] time = new int[N][2];

        for (int i = 0; i < N; i++) {
            time[i][0] = kb.nextInt();  // 시작 시간
            time[i][1] = kb.nextInt();  // 끝나는 시간
        }

        // 시작 시간으로 오름차순 정렬, 시작 시간이 같다면 끝나는 시간으로 정렬
        Arrays.sort(time, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });

        int startTime = 0;
        int count = 0;
        for (int i = 0; i < N; i++) {
            if (time[i][0] < startTime) {  // 시작 시간 검사
                continue;
            }
            if (i == N - 1) {  // 마지막 회의 일정 검사일 경우
                count += 1;
                break;
            }
            if (time[i][1] <= time[i+1][0]) {  // 현재 종료 시간이 다음 시작 시간보다 빠를 때
                count += 1;
                startTime = time[i][1];
                continue;
            }

            int currentUseTime = time[i][1] - time[i][0];
            int nextUseTime = time[i+1][1] - time[i+1][0];
            if (currentUseTime <= nextUseTime) {
                count += 1;
                startTime = time[i][1];
            }
        }

        System.out.println(count);
    }
}