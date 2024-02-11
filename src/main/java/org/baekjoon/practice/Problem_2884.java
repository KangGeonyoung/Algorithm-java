package org.baekjoon.practice;

import java.util.Scanner;

public class Problem_2884 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        long hour = kb.nextLong();
        long minute = kb.nextLong();
        long convertedMinute = hour * 60 + minute;

        convertedMinute -= 45;
        if (convertedMinute < 0) {
            convertedMinute = 1440 + convertedMinute;
        }
        hour = convertedMinute / 60;
        minute = convertedMinute % 60;

        System.out.println(hour + " " + minute);
    }
}
