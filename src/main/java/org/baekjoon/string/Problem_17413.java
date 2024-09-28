package org.baekjoon.string;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 문자열 알고리즘 - 백준: 17413번 (실버 3)
 * - charAt으로 한 문자씩 읽으면서 조건에 따라 분류 시켜주는 형식으로 풀었다.
 * - 해당 문제에서는 태그(<>)를 읽기 시작하면 isTag라는 flag를 사용해서 일반 문자로 읽히지 않도록 구분해줬다.
 * - 문자열 같은 경우에는 endPoint와 같은 예외사항 처리를 잘 해줘야 하는 것 같다.
 */
public class Problem_17413 {
    static Queue<String> words = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String line = br.readLine();
        int length = line.length();

        // 단어를 덩어리로 분리해서 큐에 넣기
        boolean isTag = false;
        int tag_start = -1;
        int tag_end = -1;
        String normalWord = "";

        for (int i = 0; i < length; i++) {
            char digit = line.charAt(i);

            if (digit == '<') {
                if (!normalWord.isEmpty()) {
                    words.add(normalWord);
                    normalWord = "";
                }
                isTag = true;
                tag_start = i;
            }

            if (!isTag) {
                if (digit == ' ') {
                    words.add(normalWord);
                    words.add(" ");
                    normalWord = "";
                } else {
                    normalWord += digit;
                }

                if (i == length - 1) {
                    words.add(normalWord);
                    normalWord = "";
                }
            }

            if (digit == '>') {
                isTag = false;
                tag_end = i;
                String tagWord = line.substring(tag_start, tag_end + 1);
                words.add(tagWord);
            }
        }

        int size = words.size();

        for (int i = 0; i < size; i++) {
            String word = words.poll();

            if (word.charAt(0) == '<') {  // 태그일 경우 그대로 출력
                bw.write(word);
            } else {  // 태그가 아닌 경우, 역순 출력
                inversePrint(bw, word);
            }
        }

        bw.close();
    }

    private static void inversePrint(BufferedWriter bw, String word) throws IOException {
        int wordLength = word.length();
        for (int j = wordLength-1; j >= 0; j--) {
            bw.write(word.charAt(j));
        }
    }
}
