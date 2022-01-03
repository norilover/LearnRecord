package nori.programming.algorithm_datastructure.sort;

import com.sun.istack.internal.NotNull;

/***
 * 复杂度分析：快速排序在较优情况下是 O(NlgN)，在较差情况下是 O(N2)。
 */
public class QuickSort {
    public void sort(@NotNull int[] nums){
        quickSort(nums, 0, nums.length);
    }
    /***
     * 快速排序
     * 使用三路切分法(tupleCut())
     * @param arr array
     * @param b begin
     * @param e end
     */
    private void quickSort(int[] arr, int b, int e) {

        // if(b >= e || b + 1 >= e)
        if(e - b <= 1)
            return;

        int m = b + ((e - b) >> 1);
        // pivot
        int p = arr[m];

        // Abbreviation
        // int p = nums[s + ((e - s) >> 1)];

        int l = b;
        int i = b;
        int r = e - 1;

        while(i <= r){
            if(arr[i] < p){
                swap(arr, l++, i++);
            }else if(arr[i] == p){
                i++;
            }else{
                swap(arr, r--, i);
            }
        }

        quickSort(arr, b, l);
        quickSort(arr, i, e);
    }


    /***
     * Other Way
     * @param arr array
     * @param b begin
     * @param e end
     */
    private void quickSort1(int[] arr, int b, int e) {
        if( e - b <= 1)
            return;

        // pivot
        // 这里可以优化一下，保证pivot不是b到e的边界数, 可以防止出现 O(N2)的情况
        int p = arr[b];

        int l = b;
        int r = e - 1;

        while(l < r){
            while(r > l && arr[r] >= p)
                r--;
            arr[l] = arr[r];

            while (l < r && arr[l] <= p)
                l++;
            arr[r] = arr[l];
        }

        // 确定一个元素的最终位置
        arr[l] = p;

        quickSort1(arr, b, l);
        quickSort1(arr, l + 1, e);
    }

    public int tripleCut(int[] arr, int b, int e){
        // 结束
        if(b >= e)
            return 0;

        // 确定进行比较的值
        int m = b + ((e - b) >> 1);
        int pivot = arr[m];

        int l = b;
        int i = b;
        int r = e - 1;
        while(i <= r){
            if(arr[i] < pivot){
                swap(arr, l++, i++);
            }else if(arr[i] == pivot){
                i++;
            }else{
                // if(arr[i] > pivot)
                swap(arr, r--, i);
            }
        }

        // 取出元素数量为奇数的部分
        // 区间为：[b,l), [l,i), [i,e)
        if(i - l == 1){
            // 中间的区间
           return arr[l];
        }else if(((l - b) & 0x01) == 1){
            // 左边的区间
            return tripleCut(arr, b, l);
        }else{
            // 右边的区间
            // else if(((e - i) & 0x01) == 1)
            return tripleCut(arr, i, e);
        }

    }
    private void swap(int[] arr, int i1, int i2) {
        if(i1 == i2)
            return;

        arr[i1] ^= arr[i2];
        arr[i2] ^= arr[i1];
        arr[i1] ^= arr[i2];
    }
}
