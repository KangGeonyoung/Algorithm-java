package org.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 에라토스테네스의 체
 * - 에라토스테네스의 체를 이용한 소수 판별 코드이다.
 * - 숫자 범위만큼 배열을 선언한 뒤 2부터 시작해서 해당 숫자의 배수를 지워나가면 된다.
 */
public class Prime_Number {
    static int N;
    static int[] number;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        number = new int[N + 1];

        for (int i = 0; i <= N; i++) {
            number[i] = i;
        }

        for (int i = 2; i <= N; i++) {
            if (number[i] == 0) {
                continue;
            }
            for (int j = 2; j <= N / i; j++) {
                number[i * j] = 0;
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 2; i <= N; i++) {
            if (number[i] != 0) {
                result.add(number[i]);
            }
        }

        System.out.println(result);
    }
}
