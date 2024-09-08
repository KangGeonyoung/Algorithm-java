package org.baekjoon.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 트리 알고리즘 - 백준: 1991 (실버 1)
 * - 주어진 input으로 트리를 만드는 게 오랜만이라 시간이 걸렸다.
 * - Branch라는 inner class를 만들고, 재귀를 이용해서 left, right 방향으로 가지 치기 하면 된다.
 * - 트리만 잘 생성하면 조회도 재귀를 이용하면 된다.
 */
public class Problem_1991 {
    static int N;  // 노드의 개수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        // root 생성
        char[] treeInfo = br.readLine().replace(" ", "").toCharArray();
        Branch rootBranch = new Branch(treeInfo[0], new Branch(treeInfo[1], null, null), new Branch(treeInfo[2], null, null));

        // 트리 생성
        for (int i = 0; i < N - 1; i++) {
            // 1줄 input
            char[] branchInfo = br.readLine().replace(" ", "").toCharArray();
            char root = branchInfo[0];
            Branch left = new Branch(branchInfo[1], null, null);
            Branch right = new Branch(branchInfo[2], null, null);

            makeBranch(rootBranch.left, root, left, right);
            makeBranch(rootBranch.right, root, left, right);
        }

        printFirst(rootBranch);  // 전위 순회
        System.out.println();
        printSecond(rootBranch);  // 중위 순회
        System.out.println();
        printThird(rootBranch);  // 후위 순회
    }

    public static void printFirst(Branch branch) {
        if (branch == null || branch.root == '.') {
            return;
        }
        System.out.print(branch.root);
        printFirst(branch.left);
        printFirst(branch.right);
    }

    public static void printSecond(Branch branch) {
        if (branch == null || branch.root == '.') {
            return;
        }
        printSecond(branch.left);
        System.out.print(branch.root);
        printSecond(branch.right);
    }

    public static void printThird(Branch branch) {
        if (branch == null || branch.root == '.') {
            return;
        }
        printThird(branch.left);
        printThird(branch.right);
        System.out.print(branch.root);
    }

    public static void makeBranch(Branch branch, char root, Branch left, Branch right) {
        if (branch == null || branch.root == '.') {
            return;
        }
        if (branch.root == root) {
            branch.left = left;
            branch.right = right;
            return;
        }
        makeBranch(branch.left, root, left, right);
        makeBranch(branch.right, root, left, right);
    }

    static class Branch {
        char root;
        Branch left;
        Branch right;

        public Branch(char root, Branch left, Branch right) {
            this.root = root;
            this.left = left;
            this.right = right;
        }
    }
}
