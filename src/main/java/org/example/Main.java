package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        while(kb.hasNext()) {
            System.out.println(kb.nextLine());
        }
    }
}