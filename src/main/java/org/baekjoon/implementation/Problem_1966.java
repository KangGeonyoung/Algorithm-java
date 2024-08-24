package org.baekjoon.implementation;

import java.io.*;
import java.util.*;

/**
 * 구현 알고리즘 - 백준: 1966번 (실버 3)
 * - Queue, PriorityQueue는 굉장히 유용하다.
 * - 특히, max 값을 순서대로 담아두고 싶다면 PriorityQueue를 사용하면 될 것 같다. (단, Collections.reverseOrder()를 적용해야 한다)
 * - 여러가지 정보를 저장해야 한다면 static class를 하나 만들어서 저장하자.
 */
public class Problem_1966 {

    static int testCase;
    static int N, M;
    static Queue<Record> queue = new LinkedList<>();
    static PriorityQueue<Integer> maxInfo = new PriorityQueue<>(Collections.reverseOrder());


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        testCase = Integer.parseInt(st.nextToken());

        // 테스트케이스 반복
        for (int i = 0; i < testCase; i++) {
            // N, M 입력 받기
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());  // 문서의 개수
            M = Integer.parseInt(st.nextToken());  // 궁금한 문서의 번호

            // 문서 정보 추가
            maxInfo.clear();
            queue.clear();
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                Record record = new Record(Integer.parseInt(st.nextToken()), j);
                maxInfo.add(record.score);
                queue.add(record);
            }

            // 프린트 시작
            int printCount = 0;
            while (true) {
                Integer current_max = maxInfo.peek();
                Record head = queue.poll();
                int current_score = head.score;

                if (current_score < current_max) {  // 현재 문서보다 중요도 높은 문서가 있을 경우, 뒤로 보내기
                    queue.add(head);
                } else {  // 현재 문서의 중요도가 가장 높은 경우, 출력하기
                    maxInfo.poll();
                    printCount += 1;

                    // 궁금한 문서의 번호일 경우, 종료
                    if (head.index == M) {
                        bw.write(Integer.toString(printCount));
                        bw.newLine();
                        break;
                    }
                }
            }
        }

        bw.close();
    }
    
    static class Record {
        int score;
        int index;

        public Record(int score, int index) {
            this.score = score;
            this.index = index;
        }
    }
}
