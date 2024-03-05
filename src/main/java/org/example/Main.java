package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // 그리디 저울 - 2437번 문제
        // 0. 비교 무게는 1부터 시작
        // 1. 비교 무게보다 작거나 같은 추들 중 가장 max값을 찾아서 빼준다.
        // 2. 1번 과정을 계속 반복하면서 비교 무게가 0이 될때까지 진행한다.
        // 3. 추를 다 사용했는데도 비교 무게가 0이 되지 않으면 해당 무게가 답이 된다.

        // 3 4 8 4 12 15 10

        bw.close();
    }
}