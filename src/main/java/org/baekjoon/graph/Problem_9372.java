package org.baekjoon.graph;

import java.io.*;
import java.util.StringTokenizer;

/**
 * 그래프 알고리즘 - 백준: 9372번 (실버 4)
 * - 그래프의 최단 거리 노선은 (노드 개수 - 1)개이다.
 */
public class Problem_9372 {
    static int testcase;
    static int country;
    static int plane;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        testcase = Integer.parseInt(st.nextToken());

        for (int i = 0; i < testcase; i++) {
            st = new StringTokenizer(br.readLine());
            country = Integer.parseInt(st.nextToken());
            plane = Integer.parseInt(st.nextToken());

            for (int j = 0; j < plane; j++) {
                st = new StringTokenizer(br.readLine());
            }

            bw.write(Integer.toString(country-1));
            bw.newLine();
        }
        bw.close();
    }
}
