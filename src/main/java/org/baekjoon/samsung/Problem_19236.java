package org.baekjoon.samsung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Problem_19236 {

    static int[] direction_x = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] direction_y = {0, -1, -1, -1, 0, 1, 1, 1};

    static int max = 0;


    public static void main(String[] args) throws IOException {

        int[][] fishMap = new int[4][4];
        int[][] fishDirection = new int[16][2];  // 0번째 : fishNumber, 1번째 : direction
        int[] sharkInfo = new int[4];  // 0: shark_X, 1: shark_Y, 2: sharkEatAmount, 3: sharkDirection


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // 물고기 번호, 방향정보 저장
        int fishOrder = 0;
        for (int i = 0; i < 4; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++) {
                fishMap[i][j] = Integer.parseInt(st.nextToken());
                fishDirection[fishOrder][0] = fishMap[i][j];
                fishDirection[fishOrder][1] = Integer.parseInt(st.nextToken());
                fishOrder += 1;
            }
        }

        printFishMap(fishMap);

        // 초반 상어를 (0, 0)에 투입해서 물고기 사냥
        sharkInfo = eatFish(fishMap, fishDirection, 0, 0, sharkInfo);

        // 물고기 전부 이동
        fishMap = moveAllFish(fishMap, fishDirection, sharkInfo);

        // 사냥 가능한 물고기 찾기
        List<Integer> possibleFish = new ArrayList<Integer>();
        possibleFish = huntPossibleFish(fishMap, sharkInfo);

        // 상어가 사냥 가능한 물고기가 존재한다면
        if (possibleFish.size() > 0) {
            // 가능한 물고기 dfs에 따라 순서대로 사냥 시작
            huntFish(fishMap, fishDirection, possibleFish, sharkInfo);
        }

        System.out.println("result : " + max);
    }

    public static void start(int[][] fishMap, int[][] fishDirection, int[] sharkInfo) {
        // 물고기 전부 이동
        fishMap = moveAllFish(fishMap, fishDirection, sharkInfo);

        // 사냥 가능한 물고기 찾기
        List<Integer> possibleFish = new ArrayList<Integer>();
        possibleFish = huntPossibleFish(fishMap, sharkInfo);

        // 상어가 사냥 가능한 물고기가 존재한다면
        if (possibleFish.size() > 0) {
            // 가능한 물고기 dfs에 따라 순서대로 사냥 시작
            huntFish(fishMap, fishDirection, possibleFish, sharkInfo);
        }
    }

    // 사냥 가능한 물고기 리스트 반환
    public static List<Integer> huntPossibleFish(int[][] fishMap, int[] sharkInfo) {
        List<Integer> possibleFish = new ArrayList<Integer>();
        int shark_X = sharkInfo[0];
        int shark_Y = sharkInfo[1];
        int sharkDirection = sharkInfo[3];

        while(true) {
            System.out.println(sharkDirection);
            shark_X += direction_x[sharkDirection - 1];
            shark_Y += direction_y[sharkDirection - 1];

            if (shark_X < 0 || shark_Y < 0) {
                break;
            }
            if (shark_X >= 4 || shark_Y >= 4) {
                break;
            }
            if (fishMap[shark_X][shark_Y] == 0) {  // 해당 자리에 물고기가 없다면
                continue;
            }

            // 사냥 가능한 물고기 번호 넣어주기
            possibleFish.add(fishMap[shark_X][shark_Y]);
        }

        return possibleFish;
    }

    public static void huntFish(int[][] fishMap, int[][] fishDirection, List<Integer> possibleFish, int[] sharkInfo) {

        // dfs 탐색
        for (int m = 0; m < possibleFish.size(); m++) {  // 사냥 가능한 물고기 수만큼 반복
            int[][] copyFishMap = cloneDeep(fishMap);
            int[][] copyFishDirection = cloneDeep(fishDirection);
            int[] copyShark = sharkInfo.clone();

            // 사냥 가능한 물고기의 fishNumber를 통해 위치를 찾아서 사냥하기
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (copyFishMap[i][j] == possibleFish.get(m)) {
                        copyShark = eatFish(copyFishMap, copyFishDirection, i, j, copyShark);
                        System.out.println("상어 사냥 이후 물고기 상태");
                        printFishMap(copyFishMap);

                        System.out.println("-------------start 시작------------");
                        start(copyFishMap, copyFishDirection, copyShark);
                        System.out.println("-------------start 종료------------");
//						sharkInfo = undoEatFish()
                        break;
                    }
                }
            }
        }
    }

    public static int[][] cloneDeep(int[][] origin) {
        int[][] copy = new int[origin.length][origin[0].length];

        for (int i = 0; i < origin.length; i++) {
            for (int j = 0; j < origin[0].length; j++) {
                copy[i][j] = origin[i][j];
            }
        }
        return copy;
    }


    // fishMap 출력
    public static void printFishMap(int[][] fishMap) {
        System.out.println();
        System.out.println("현재 물고기 이동 상태");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(fishMap[i][j] + " ");
            }
            System.out.println();
        }
    }

    // fishDirection 출력
    public static void printFishDirection(int[][] fishDirection) {
        System.out.println();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(fishDirection[i][j]);
            }
            System.out.println();
        }
    }

    // 상어가 물고기 잡아 먹기
    public static int[] eatFish(int[][] fishMap, int[][] fishDirection, int fish_x, int fish_y, int[] sharkInfo) {
        sharkInfo[2] += fishMap[fish_x][fish_y];  // sharkEatAmount 업데이트
        sharkInfo[3] = getFishDirection(fishDirection, fishMap[fish_x][fish_y]);  // 잡아먹은 물고기의 방향을 상어가 가짐
        sharkInfo[0] = fish_x;  // 상어 위치 업데이트
        sharkInfo[1] = fish_y;
        if (max < sharkInfo[2]) {  // 최댓 값 업데이트
            max = sharkInfo[2];
            System.out.println("max : " + max);
        }
        fishMap[fish_x][fish_y] = 0;  // 잡아먹은 물고기 없애주기

        System.out.println("변경된 상어의 방향 : " + sharkInfo[3]);
        return sharkInfo;
    }

    // 물고기 전체 이동
    public static int[][] moveAllFish(int[][] fishMap, int[][] fishDirection, int[] sharkInfo) {
        for (int i = 1; i <= 16; i++) {
            fishMap = moveOneFish(i, fishMap, fishDirection, sharkInfo);
        }
        printFishMap(fishMap);
        return fishMap;
    }

    // 물고기 한마리 이동
    public static int[][] moveOneFish(int fishNumber, int[][] fishMap, int[][] fishDirection, int[] sharkInfo) {
        int x = -1;
        int y = -1;

        // fishNumber가 fishMap의 어느 위치에 있는지 찾기
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (fishMap[i][j] == fishNumber) {
                    x = i;
                    y = j;
                }
            }
        }

        // fishMap에 해당 fishNumber가 없을 경우 함수 종료
        if (x == -1 && y == -1) {
            return fishMap;
        }

        // fishNumber로 방향 찾기
        int direction = getFishDirection(fishDirection, fishNumber);

        // 물고기의 현재 방향으로 이동이 가능한지 검사
        while (!isValidPosition(x, y, direction, sharkInfo)) {  // 이동이 불가능할 경우
            // 반시계 방향으로 45도 회전
            fishDirection = changeNextDirection(fishDirection, fishNumber, direction);
            direction = getFishDirection(fishDirection, fishNumber);
        }

        // swap할 위치
        int fish_swap_X = x + direction_x[direction - 1];
        int fish_swap_Y = y + direction_y[direction - 1];

        // 물고기 swap 실행
        fishMap = fishSwap(fishMap, x, y, fish_swap_X, fish_swap_Y);

        return fishMap;
    }

    // 물고기 swap
    public static int[][] fishSwap(int[][] fishMap, int current_x, int current_y, int swap_x, int swap_y) {
        int temp = fishMap[current_x][current_y];
        fishMap[current_x][current_y] = fishMap[swap_x][swap_y];
        fishMap[swap_x][swap_y] = temp;
        return fishMap;
    }

    // 반시계 방향으로 45도 회전
    public static int[][] changeNextDirection(int[][] fishDirection, int fishNumber, int direction) {
        direction += 1;
        if (direction > 8) {
            direction -= 8;
        }
        fishDirection = updateFishDirection(fishDirection, fishNumber, direction);
        return fishDirection;
    }

    // 변경된 fishDirection 업데이트
    public static int[][] updateFishDirection(int[][] fishDirection, int fishNumber, int direction) {
        for (int i = 0; i < 16; i++) {
            if (fishDirection[i][0] == fishNumber) {
                fishDirection[i][1] = direction;
                break;
            }
        }
        return fishDirection;
    }

    // 물고기가 이동해도 되는 곳인지 확인
    public static boolean isValidPosition(int x, int y, int direction, int[] sharkInfo) {
        switch (direction) {
            case 1:
                x -= 1;
                break;

            case 2:
                x -= 1;
                y -= 1;
                break;

            case 3:
                y -= 1;
                break;

            case 4:
                x += 1;
                y -= 1;
                break;

            case 5:
                x += 1;
                break;

            case 6:
                x += 1;
                y += 1;
                break;

            case 7:
                y += 1;
                break;

            case 8:
                x -= 1;
                y += 1;
                break;

            default:
                break;
        }

        if (x < 0 || y < 0) {  // 음수 값 확인
            return false;
        }
        if (x >= 4 || y >= 4) {  // 배열 범위를 벗어나는 경우
            return false;
        }

        if (isSharkPosition(x, y, sharkInfo)) {  // 상어가 있을 경우
            return false;
        }

        return true;
    }

    // 해당 위치가 상어가 있는 곳인지 확인
    public static boolean isSharkPosition(int x, int y, int[] sharkInfo) {
        if (x == sharkInfo[0] && y == sharkInfo[1]) {
            return true;
        }
        return false;
    }

    // 물고기 번호와 매칭되는 방향 찾기
    public static int getFishDirection(int[][] fishDirection, int fishNumber) {
        for (int i = 0; i < fishDirection.length; i++) {
            if (fishDirection[i][0] == fishNumber) {
                return fishDirection[i][1];
            }
        }
        return 0;
    }
}
