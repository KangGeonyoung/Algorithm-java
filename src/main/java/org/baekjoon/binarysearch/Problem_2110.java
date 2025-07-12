package org.baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 이분 탐색 알고리즘 - 백준: 2110번 (골드 4)
 * - 변수의 범위가 long이고, 최대 또는 최소 길이를 찾아야 하는 문제일 경우, 이분 탐색을 고려해봐야 한다.
 * - 내가 실수한 점
 *   - start = mid - 1로 한 것
 *   - start = mid + 1, end = mid - 1 기억할 것
 */
public class Problem_2110 {

    static int N, C;
    static List<Long> homes = new ArrayList<>();
    static long result = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            long home = Long.parseLong(br.readLine());
            homes.add(home);
        }
        Collections.sort(homes);

        long start = 1;
        long end = homes.get(homes.size() - 1) - homes.get(0);
        while (start <= end) {
            long mid = (start + end) / 2;

            if (isPossible(mid)) {
                result = Math.max(result, mid);
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        System.out.println(result);
    }

    public static boolean isPossible(long dist) {
        long matchCnt = 1;
        long start = homes.get(0);

        for (int i = 1; i < homes.size(); i++) {
            long compare = homes.get(i);
            if (compare - start >= dist) {
                matchCnt += 1;
                start = compare;
            }
        }

        // 문제에서 요구하는 공유기 설치 수를 만족했을 경우
        if (matchCnt >= C) {
            return true;
        }
        return false;
    }
}
