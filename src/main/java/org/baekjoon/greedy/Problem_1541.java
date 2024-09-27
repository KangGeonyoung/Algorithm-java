package org.baekjoon.greedy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 그리디 알고리즘 - 백준: 1541번 (실버 2)
 * - 우선 순위를 찾아야 한다.
 * - 이 문제에서는 부호에 따라 연산의 우선순위가 달랐다.
 * - 내가 실수한 점
 *   - 리스트에서 remove를 사용하면 한칸씩 자동으로 당겨진다.
 *   - Arrays.asList()는 사용하지 말자. -> 해당 메서드는 만들어진 List에서 add나 remove 기능을 사용하지 못하게 한다.
 */
public class Problem_1541 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String line = br.readLine();
        // 부호를 구분자로 하여 숫자만 리스트에 저장
        String[] input = line.split("-|\\+");
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            numbers.add(Integer.parseInt(input[i]));
        }

        // 부호만 리스트에 저장
        List<Character> signs = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            char digit = line.charAt(i);
            if (digit == '-' || digit == '+') {
                signs.add(digit);
            }
        }

        // 모든 덧셈부터 연산
        int findIndex = 0;
        while (numbers.size() - 1 != findIndex) {
            Character sign = signs.get(findIndex);
            if (sign.equals('+')) {
                int first = numbers.get(findIndex);
                int second = numbers.get(findIndex + 1);
                int calculate = first + second;

                // 계산된 사용된 숫자와 부호를 삭제 & 새로운 결과 값을 numbers에 추가
                numbers.remove(findIndex + 1);
                numbers.remove(findIndex);
                numbers.add(findIndex, calculate);
                signs.remove(findIndex);

                findIndex = 0;
                continue;
            }
            findIndex += 1;
        }

        // 모든 뺄셈 연산
        findIndex = 0;
        while (signs.size() > 0) {
            Character sign = signs.get(findIndex);
            if (sign.equals('-')) {
                int first = numbers.get(findIndex);
                int second = numbers.get(findIndex + 1);
                int calculate = first - second;

                // 계산된 사용된 숫자와 부호를 삭제 & 새로운 결과 값을 numbers에 추가
                numbers.remove(findIndex + 1);
                numbers.remove(findIndex);
                numbers.add(findIndex, calculate);
                signs.remove(findIndex);

                findIndex = 0;
                continue;
            }
            findIndex += 1;
        }

        bw.write(Integer.toString(numbers.get(0)));
        bw.close();
    }
}
