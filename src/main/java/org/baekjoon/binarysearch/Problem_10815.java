package org.baekjoon.binarysearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 이분 탐색 알고리즘 - 백준: 10815번 (실버 5)
 * - start = 0, end = N-1, mid = (start+end) / 2로 설정해야 한다.
 * - 내가 실수한 점
 *   - end를 배열의 크기로 설정했더니, indexOutOf 오류가 발생했다. 항상 (배열 크기 - 1)로 설정하자.
 *   - StringBuilder를 사용하려면 new StringBuilder()로 인스턴스를 생성해줘야 한다.
 */
public class Problem_10815 {
    static int N, M;
    static long[] myCard;
    static long[] cardList;
    static StringBuilder str = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        myCard = new long[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            myCard[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        cardList = new long[M];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            cardList[i] = Integer.parseInt(st.nextToken());
        }

        // 주어진 카드들 오름차순 정렬
        Arrays.sort(myCard);

        for (int i = 0; i < M; i++) {
            // 찾았을 경우
            if (search(cardList[i])) {
                str.append("1 ");
            } else {
                str.append("0 ");
            }
        }

        System.out.println(str);
    }

    public static boolean search(long findNum) {
        int start = 0;
        int end = myCard.length - 1;
        int mid;

        while (start <= end) {
            mid = (start + end) / 2;
            if (myCard[mid] < findNum) {
                start = mid + 1;
            }
            if (findNum < myCard[mid]) {
                end = mid - 1;
            }
            if (findNum == myCard[mid]) {
                return true;
            }
        }
        return false;
    }
}
