package nori.programming.algorithm_datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
 * @lc app=leetcode.cn id=784 lang=java
 *
 * [784] 字母大小写全排列
 *
 * https://leetcode-cn.com/problems/letter-case-permutation/description/
 *
 * algorithms
 * Medium (66.73%)
 * Likes:    256
 * Dislikes: 0
 * Total Accepted:    30.6K
 * Total Submissions: 45.8K
 * Testcase Example:  '"a1b2"'
 *
 * 给定一个字符串S，通过将字符串S中的每个字母转变大小写，我们可以获得一个新的字符串。返回所有可能得到的字符串集合。
 *
 *
 *
 * 示例：
 * 输入：S = "a1b2"
 * 输出：["a1b2", "a1B2", "A1b2", "A1B2"]
 *
 * 输入：S = "3z4"
 * 输出：["3z4", "3Z4"]
 *
 * 输入：S = "12345"
 * 输出：["12345"]
 *
 *
 *
 *
 * 提示：
 *
 *
 * S 的长度不超过12。
 * S 仅由数字和字母组成。
 *
 *
 */
public class Test {

    public static void main(String[] args) {
//        System.out.println(Arrays.deepToString(new Test().findAllPossible("WZ1e").toArray()));

        StringBuilder stringBuilder = new StringBuilder();

        System.out.println(stringBuilder.length());
        stringBuilder.append("111");
        stringBuilder.append("");

        stringBuilder.append("111");
        System.out.println(stringBuilder.length());

        int[] arr = new int[10];
        Arrays.fill(arr, 0);
        arr[0] = 111;
        byte ind = '1' - '0';

        System.out.println(arr[ind]);
    }

    public List<String> findAllPossible(String baseStr) {
        List<String> rList = new ArrayList<>();

        if(baseStr == null || baseStr.length() == 0)
            return rList;

        backtrace(baseStr, new StringBuilder(), rList);

        return rList;
    }

    private void backtrace(String str, StringBuilder rStr, List<String> rList){
        if(str == null || str.length() == 0){
            rList.add(rStr.toString());
            return;
        }
        int i = 0;

        //
        if(i < str.length()){
            final char c = str.charAt(i);

            rStr.append(c);

            backtrace(str.substring(i + 1), rStr, rList);

            rStr.deleteCharAt(rStr.length() - 1);


//            if(Character.isAlphabetic(c)){
            if(c < '0' || c > '9'){
//                rStr.append((char)(Character.isLowerCase(c) ? (c - 32) : (c + 32)));

                // 根据 'a' - 'A' == 32
                rStr.append((char)(c >= 'a' && c <= 'z' ? c - 32 : c + 32));

                backtrace(str.substring(i + 1), rStr, rList);

                rStr.deleteCharAt(rStr.length() - 1);
            }
        }
    }
}