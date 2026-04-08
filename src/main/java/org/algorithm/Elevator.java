package org.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Elevator {

    static int high;  // 건물 층수
    static int count;
    static List<Integer> dist = new ArrayList<>();
    static List<Integer> result = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        high = Integer.parseInt(st.nextToken());
        count = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < count; i++) {
            dist.add(Integer.parseInt(st.nextToken()));
        }

        // 1층부터 high층까지 시작점 세팅
        for (int i = 1; i <= high; i++) {
            int cur = 0;
            checkValid(i, cur);
        }

        for (Integer floor : result) {
            System.out.println(floor);
        }
    }

    public static void checkValid(int curFloor, int curIdx) {
        // 종료 조건
        if (curIdx == dist.size()) {
            if (!result.contains(curFloor)) {
                result.add(curFloor);
            }
            return;
        }

        // 내가 이동해야 하는 거리
        int myDist = dist.get(curIdx);

        // 위로 올라가는 계산과 아래로 내려가는 계산 실행
        int upFloor = curFloor + myDist;
        int downFloor = curFloor - myDist;

        // 올바른 범위일 경우 이동
        if (upFloor >= 1 && upFloor <= high) {
            checkValid(upFloor, curIdx + 1);
        }
        if (downFloor >= 1 && downFloor <= high) {
            checkValid(downFloor, curIdx + 1);
        }
    }
}
