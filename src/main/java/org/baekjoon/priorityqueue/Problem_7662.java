package org.baekjoon.priorityqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * 우선순위 큐 알고리즘 - 백준: 7662번 문제 (골드 4)
 * - 처음에는 우선순위 큐 여러 개로 풀었지만, 시간 초과가 발생해서 TreeMap을 사용함
 * - TreeMap은 레드-블랙 트리로 이루어진 자료구조로, 자동으로 오름차순 정렬을 진행해 준다.
 *   - map.firstKey()는 제일 왼쪽에 있는 값이기 때문에 최솟값을 반환하고, map.lastKey()는 제일 오른쪽에 있는 값이기 때문에 최댓값을 반환한다.
 */
public class Problem_7662 {

    static int T, k;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = Integer.parseInt(br.readLine());
        for (int i = 0; i < T; i++) {
            k = Integer.parseInt(br.readLine());
            TreeMap<Integer, Integer> map = new TreeMap<>();

            for (int j = 0; j < k; j++) {
                st = new StringTokenizer(br.readLine());
                String instruction = st.nextToken();
                int number = Integer.parseInt(st.nextToken());

                // 삽입 명령어
                if (instruction.equals("I")) {
                    map.put(number, map.getOrDefault(number, 0) + 1);
                } else {  // 삭제 명령어
                    if (!map.isEmpty()) {
                        int value = 0;
                        if (number == 1) {  // 최댓값 탐색
                            value = map.lastKey();
                        } else {  // 최솟값 탐색
                            value = map.firstKey();
                        }

                        // 맵에 있는 해당 value의 개수를 1 감소해줌
                        map.put(value, map.get(value) - 1);
                        if (map.get(value) == 0) {  // 개수가 0이 되었다면, 맵에서 삭제
                            map.remove(value);
                        }
                    }
                }
            }

            if (map.isEmpty()) {
                System.out.println("EMPTY");
            } else {
                System.out.println(map.lastKey() + " " + map.firstKey());
            }
        }
    }
}
