package nori.programming.algorithm_datastructure.search;

public class BinarySearch {
    /***
     * 寻找最左边的元素
     * Q：
     *      数组 ：{1,2,2,2,3}
     *      查找最靠左边的元素2对应的下标
     * A:
     *      循环次数    l   r   m   arr[m] < target
     *         1      0   5   2    false => r = m
     *         2      0   2   1    false => r = m
     *         3      0   1   0    true  => l = m + 1
     *         4      1   1   => 条件不符合退出循环
     *
     *      返回 下标 为 1 的 l
     * @param arr
     * @param b
     * @param e
     * @param target
     * @return
     */
    public int lowerBound(int[] arr, int b, int e, int target){
        int l = b;
        int r = e;

        while(l < r){
            final int m = l + ((r - l) >> 1);
            if(arr[m] < target){
                l = m + 1;
            }else{
                r = m;
            }
        }

        return l;
    }
    /***
     * 寻找最右边的元素
     * Q：
     *      数组 ：{1,2,2,2,3}
     *      查找最靠左边的元素2对应的下标
     * A:
     *      循环次数    l   r   m   arr[m] < target
     *         1      0   5   2    true => l = m + 1
     *         2      3   5   4    false => r = m
     *         3      3   4   3    true  => l = m + 1
     *         4      4   4   => 条件不符合退出循环
     *
     *      返回 下标 为 4 的 l
     *      实际最左边元素的下标为 l - 1
     * @param arr array
     * @param b begin
     * @param e end
     * @param target
     * @return
     */
    public int upperBound(int[] arr, int b, int e, int target){
        int l = b;
        int r = e;

        while(l < r){
            final int m = l + ((r - l) >> 1);
            if(arr[m] <= target){
                l = m + 1;
            }else{
                r = m;
            }
        }

        return l;
    }
}
