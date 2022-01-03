package nori.programming;


import javax.jws.soap.SOAPBinding;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Test {
    public static void main(String[] args) {

        System.out.println("Hello ");



        HashMap<Integer, int[]> map= new HashMap<>();
        int[][] arr = new int[2][];
        arr[0] = new int[]{1,2};
        arr[1] = new int[]{2,3};

        List<int[]> list = new ArrayList<>();
        list.add(arr[0]);

        list.add(arr[1]);

        int[][] ans = list.toArray(arr);

        System.out.println((int)('R' & 'G' & 'B'));

        System.out.println((int)('R' & 'B'));
        System.out.println((int)('R' & 'G'));
        int[][] arrs = new int[2][];
        arrs[0] = new int[]{3,2};
        arrs[1] = new int[]{1,2};
//        Arrays.sort(arrs, new Comparator<int[]>() {
//            @Override
//            public int compare(int[] o1, int[] o2) {
//                return o1[0] > o2[0] ? 1 : ((o1[0] < o2[0]) ? -1  : 0);
//            }
//        });

        Arrays.sort(arrs, (o1,o2)-> o1[0] > o2[0] ? 1 : ((o1[0] < o2[0]) ? -1  : 0));

        System.out.println(arrs[0][0] + ", " + arrs[1][0]);

//        extracted();
    }

    private static void extracted() {
        int[] nums = {1,1,1,1,1,1,1,2,2,2,3,3,3,3,3,4,4,4,4,4,4,4,4,4,4,4,4};
        int k = 3;


        HashMap<Integer, Integer> hashMap = new HashMap();
        for (Integer i : nums ) {
            if(!hashMap.containsKey(i))
                hashMap.put(i, 1);
            else
                hashMap.put(i, hashMap.get(i) + 1);
        }

        LinkedHashMap<Integer, Integer> resultMap = hashMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        int[] resultArr = new int[k];

        int ind = 1;
        int startInd = hashMap.size() - k;
        int index = 0;
        Set<Map.Entry<Integer, Integer>> entries = resultMap.entrySet();
        for (Map.Entry<Integer, Integer> set: entries)
            if(ind++ > startInd)
                resultArr[index++] = set.getKey();


        for (Integer i: resultArr
             ) {
            System.out.println(i);
        }

        resultMap.forEach((item, item1) -> {
            System.out.println(item + "  " + item1);
        });
    }
}
