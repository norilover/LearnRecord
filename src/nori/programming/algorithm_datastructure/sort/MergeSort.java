package nori.programming.algorithm_datastructure.sort;

import com.sun.istack.internal.NotNull;

public class MergeSort {

    /***
     * 合并两个有序数组template
     * @param arr original array
     * @param b begin
     * @param m middle
     * @param e end
     * @return
     */
    public int[] merge(@NotNull int[] arr, int b, int m, int e){

        int i = b;
        int j = m;

        int[] rArr = new int[arr.length];
        int to = b;

        while(i < m || j < e){

            // @I
            // arr[i] <= arr[j] 可以保证合并排序是稳定的
            if(j >= e || i < m && arr[i] <= arr[j]){
                // 取左边的元素
                rArr[to++] = arr[i++];
            }else {
                // 取右边的元素
                rArr[to++] = arr[j++];
            }

            // Abbreviation
            // rArr[to++] = j >= e || i < m && arr[i] <= arr[j] ? arr[i++] : arr[j++];
        }

        return rArr;
    }


}
