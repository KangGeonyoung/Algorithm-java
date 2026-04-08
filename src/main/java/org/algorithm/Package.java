package org.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Package {

    static int N;  // 짐 개수
    static List<Integer> order = new ArrayList<>();
    static List<Integer> wantedOrder = new ArrayList<>();
    static List<Store> stores = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            order.add(Integer.parseInt(st.nextToken()));
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            wantedOrder.add(Integer.parseInt(st.nextToken()));
        }

        // 짐을 꺼내야 하는 순서의 가장 뒷부분부터 하나씩 꺼내기
        for (int i = N - 1; i >= 0; i--) {
            int itemNum = wantedOrder.get(i);
            storeItem(itemNum);
        }

        System.out.println(stores.size());
    }

    public static void storeItem(int itemNum) {
        // 가장 처음 짐일 경우
        if (stores.size() == 0) {
            stores.add(new Store());
            stores.get(0).items.add(itemNum);
            return;
        }

        // 저장소 찾기
        for(Store store : stores) {
            int lastItem = store.items.get(store.items.size() - 1);
            // 해당 저장소에 넣어도 되는 짐일 경우
            if (isValidPosition(itemNum, lastItem)) {
                store.items.add(itemNum);
                return;
            }
        }

        // 마땅한 저장소가 없을 경우
        stores.add(new Store());
        stores.get(stores.size() - 1).items.add(itemNum);
        return;
    }

    // 짐이 나오는 순서 리스트에서 newItem이 lastItem보다 나중에 나오는지 확인
    public static boolean isValidPosition(int newItem, int lastItem) {
        int newItemIdx = order.indexOf(newItem);
        int lastItemIdx = order.indexOf(lastItem);

        if (newItemIdx > lastItemIdx) {
            return true;
        }
        return false;
    }

    static class Store {
        List<Integer> items = new ArrayList<>();
    }
}
