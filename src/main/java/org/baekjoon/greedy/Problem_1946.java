package org.baekjoon.greedy;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Problem_1946 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int i = 0; i < T; i++) {
            int N = Integer.parseInt(br.readLine());
            int[][] grade = new int[N][2];

            for (int j = 0; j < N; j++) {  // 성적 입력 받기
                st = new StringTokenizer(br.readLine());
                grade[j][0] = Integer.parseInt(st.nextToken());
                grade[j][1] = Integer.parseInt(st.nextToken());
            }

            Arrays.sort(grade, new Comparator<int[]>() {  // 첫 번째 성적으로 오름차순 정렬
                @Override
                public int compare(int[] o1, int[] o2) {
                    return Integer.compare(o1[0], o2[0]);
                }
            });

            int min = grade[0][1];
            int matchCnt = 1;
            for (int j = 1; j < N; j++) {
                if (min > grade[j][1]) {
                    min = grade[j][1];
                    matchCnt += 1;
                }
            }
            bw.write(Integer.toString(matchCnt));
            bw.newLine();
        }
        bw.close();
    }
}
