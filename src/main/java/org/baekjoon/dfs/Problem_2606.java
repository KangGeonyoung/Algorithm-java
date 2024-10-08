package org.baekjoon.dfs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * DFS 알고리즘 - 백준: 2606번 (실버 3)
 */
public class Problem_2606 {
    static int computerCnt;
    static int connectionCnt;
    static Node[] nodes;
    static List<Integer> virusList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // 컴퓨터의 수
        st = new StringTokenizer(br.readLine());
        computerCnt = Integer.parseInt(st.nextToken());

        // 연결되어 있는 컴퓨터의 수
        st = new StringTokenizer(br.readLine());
        connectionCnt = Integer.parseInt(st.nextToken());

        nodes = new Node[101];

        for (int i = 0; i < connectionCnt; i++) {
            st = new StringTokenizer(br.readLine());

            int first = Integer.parseInt(st.nextToken());
            int second = Integer.parseInt(st.nextToken());

            if (nodes[first] == null) {
                Node node = new Node();
                if (!node.nodeList.contains(first)) {
                    node.nodeList.add(first);
                }
                if (!node.nodeList.contains(second)) {
                    node.nodeList.add(second);
                }
                nodes[first] = node;
            } else {
                if (!nodes[first].nodeList.contains(second)) {
                    nodes[first].nodeList.add(second);
                }
            }

            if (nodes[second] == null) {
                Node node = new Node();
                if (!node.nodeList.contains(second)) {
                    node.nodeList.add(second);
                }
                if (!node.nodeList.contains(first)) {
                    node.nodeList.add(first);
                }
                nodes[second] = node;
            } else {
                if (!nodes[second].nodeList.contains(first)) {
                    nodes[second].nodeList.add(first);
                }
            }
        }

        if (nodes[1] != null) {
            List<Integer> virus = nodes[1].nodeList;
            virusList.add(virus.get(0));

            for (int i = 0; i < virus.size(); i++) {
                Integer compare = virus.get(i);
                if (!virusList.contains(compare)) {
                    virusList.add(compare);
                    List<Integer> newVirusList = nodes[compare].nodeList;
                    findVirus(newVirusList);
                }
            }

            bw.write(Integer.toString(virusList.size() - 1));
        } else {
            bw.write("0");
        }

        bw.close();
    }

    private static void findVirus(List<Integer> virus) {
        for (int i = 0; i < virus.size(); i++) {
            Integer compare = virus.get(i);
            if (!virusList.contains(compare)) {
                virusList.add(compare);
                List<Integer> newVirusList = nodes[compare].nodeList;
                findVirus(newVirusList);
            }
        }
    }

    static class Node {
        List<Integer> nodeList = new ArrayList<>();
    }
}
