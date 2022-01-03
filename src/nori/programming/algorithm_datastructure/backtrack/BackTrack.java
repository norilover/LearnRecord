package nori.programming.algorithm_datastructure.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackTrack {
    public static void main(String[] args) {
        BackTrack bt = new BackTrack();
//        System.out.println(Arrays.deepToString(bt.subSets(new int[]{1,2,3}).toArray()));

//        System.out.println(Arrays.deepToString(bt.kSets(3, 2).toArray()));

//        System.out.println(Arrays.deepToString(bt.combinationSum(new int[]{1,2,3}, 3).toArray()));

//        System.out.println(Arrays.deepToString(bt.combinationSum2(new int[]{10,1,2,7,6,1,5}, 8).toArray()));

//        System.out.println(Arrays.deepToString(bt.permute(new int[]{10,1,2}).toArray()));

//        System.out.println(Arrays.deepToString(bt.subsetsWithDup(new int[]{1, 1,2}).toArray()));

        System.out.println(Arrays.deepToString(bt.permuteUnique(new int[]{0, 1, 0, 0, 9}).toArray()));
    }

    /***
     * 例 2：子集
        【题目】给定一个互不相同的数的数组，返回这个数组里面所有的可能的子集（包括空集）。要求里面的子集不能重复。比如 [[1,2], [2,1]] 不合要求。因为这两个集合是一样的。

        输入：A = [1, 2, 3]

        输出：[[],[1],[1,2],[1,2,3],[1,3],[2],[2,3],[3]]

        解释：A = [1, 2,3] 集合的子集有 : 分别是 [[],[1],[1,2],[1,2,3],[1,3],[2],[2,3],[3]]。
     *
     * 复杂度分析：
     *  时间复杂度，由于一共有 N 个元素，每个元素可能被放到子集中，也可能不被放到子集中，一共有 O(2N) 个子集，每个子集都需要一次遍历。假设都按最差情况处理，单个子集遍历时间复杂度为 O(N)。所以时间复杂度为 O(N* 2N)。不算上返回值 answer，那么空间复杂度为O(N) 。
     * @param arr
     * @return
     */
    public List<List<Integer>> subSets(int[] arr){
        List<List<Integer>> ans = new ArrayList<>();

        if(arr == null)
            return ans;

        List<Integer> box = new ArrayList<>();

        backTrack(arr, 0, box, ans);

        return ans;
    }

    private void backTrack(int[] arr, int s, List<Integer> box, List<List<Integer>> ans) {
        // Add ans
        ans.add(new ArrayList<>(arr.length));
        for (var e : box)
            ans.get(ans.size() - 1).add(e);

        // Judge whether finish
        if(s >= arr.length)
            return;

        for (int i = s; i < arr.length; i++) {
            // Add
            box.add(arr[i]);

            // BackTrack
            backTrack(arr, i + 1, box, ans);

            // Remove
            box.remove(box.size() - 1);
        }
    }


    /***
     * 练习题 2：在[1, 2, ..., n] 这几个数中，选出 k 个数出来组成集合。输出所有的解。
     *
     * 输入：n = 2, k = 1
     *
     * 输出：[[1], [2]]
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> kSets(int n, int k){
        List<List<Integer>> ans = new ArrayList<>();

        if(n == 0 || k == 0 || n < k)
            return ans;

        List<Integer> box = new ArrayList<>(k);

        // Fill data
//        int[] arr = new int[n];
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = i + 1;
//        }
//
//        System.out.println(Arrays.toString(arr));
//
//        backTrack1(arr, 0, k, box, ans);


        // No use array
        backTrack1OtherWay1(1, n, k, box, ans);

        return ans;
    }

    private void backTrack1OtherWay1(int s, int e, int k, List<Integer> box, List<List<Integer>> ans) {
        if(box.size() == k){
            ans.add(new ArrayList<>(k));
            for (var ele : box) {
                ans.get(ans.size() - 1).add(ele);
            }

            return;
        }

        if(s > e)
            return;

        for (int i = s; i <= e; i++) {
            box.add(i);

            // BackTrack
            backTrack1OtherWay1(i + 1, e, k, box, ans);

            box.remove(box.size() - 1);
        }
    }

    private void backTrack1(int[] arr, int s, int k, List<Integer> box, List<List<Integer>> ans) {
        if(box.size() == k){
            ans.add(new ArrayList<>(k));
            for (var e : box) {
                ans.get(ans.size() - 1).add(e);
            }

            return;
        }

        if(s >= arr.length)
            return;

        for (int i = s; i < arr.length; i++) {
            box.add(arr[i]);

            // BackTrack
            backTrack1(arr, i + 1, k, box, ans);

            box.remove(box.size() - 1);
        }
    }


    /***
     *39. 组合总和
     * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * candidates 中的数字可以无限制重复被选取。
     *
     * 说明：
     *
     * 所有数字（包括 target）都是正整数。
     * 解集不能包含重复的组合。
     * 示例 1：
     *
     * 输入：candidates = [2,3,6,7], target = 7,
     * 所求解集为：
     * [
     *   [7],
     *   [2,2,3]
     * ]
     * 示例 2：
     *
     * 输入：candidates = [2,3,5], target = 8,
     * 所求解集为：
     * [
     *   [2,2,2,2],
     *   [2,3,3],
     *   [3,5]
     * ]
     *
     *
     * 提示：
     *
     * 1 <= candidates.length <= 30
     * 1 <= candidates[i] <= 200
     * candidate 中的每个元素都是独一无二的。
     * 1 <= target <= 500
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target){
        // Answer collection
        List<List<Integer>> ans = new ArrayList<>();

        // Check validate
        if (ans == null)
            throw new IllegalArgumentException("Candidates is null!");

        // Aux list
        List<Integer> box = new ArrayList<>();

        // BackTack
        backTrack3(candidates, 0, 0, target, box, ans);

        return ans;
    }

    /***
     *
     * @param candidates
     * @param s start position
     * @param sum the total sum
     * @param target finish condition
     * @param box temp save the result sub set
     * @param ans result
     */
    private void backTrack3(int[] candidates, int s, int sum, int target, List<Integer> box, List<List<Integer>> ans) {
        // Save result and return directly
        if(sum == target){
            ans.add(new ArrayList<>());
            for (var ele : box){
                ans.get(ans.size() - 1).add(ele);
            }

            return;
        }

        // Return directly
        if(sum > target)
            return;

        // Cycle back track
        for (int i = s; i < candidates.length; i++) {
            box.add(candidates[i]);

            backTrack3(candidates, i, sum + candidates[i], target, box, ans);

            box.remove(box.size() - 1);
        }
    }


    /***
     * 40. 组合总和 II
     * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * candidates 中的每个数字在每个组合中只能使用一次。
     *
     * 注意：解集不能包含重复的组合。
     *
     *
     *
     * 示例 1:
     *
     * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
     * 输出:
     * [
     * [1,1,6],
     * [1,2,5],
     * [1,7],
     * [2,6]
     * ]
     * 示例 2:
     *
     * 输入: candidates = [2,5,2,1,2], target = 5,
     * 输出:
     * [
     * [1,2,2],
     * [5]
     * ]
     *
     *
     * 提示:
     *
     * 1 <= candidates.length <= 100
     * 1 <= candidates[i] <= 50
     * 1 <= target <= 30
     * 复杂度分析：时间复杂度 O(N!)，如果不算返回值占用的空间，那么空间复杂度为 O(N)（因为递归栈也会占用空间）。
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target){
        // Answer collection
        List<List<Integer>> ans = new ArrayList<>();

        // Check validate
        if (ans == null)
            throw new IllegalArgumentException("Candidates is null!");

        // Aux list
        List<Integer> box = new ArrayList<>();

        // Sort array
        Arrays.sort(candidates);

        // BackTack
        backTrack4(candidates, 0, 0, target, box, ans);

        return ans;
    }

    /***
     *
     * @param candidates
     * @param s start position
     * @param sum the total sum
     * @param target finish condition
     * @param box temp save the result sub set
     * @param ans result
     */
    private void backTrack4(int[] candidates, int s, int sum, int target, List<Integer> box, List<List<Integer>> ans) {
        // Save result and return directly
        if(sum == target){
            ans.add(new ArrayList<>());
            for (var ele : box){
                ans.get(ans.size() - 1).add(ele);
            }

            return;
        }

        // Return directly
        if(sum > target)
            return;

        // Cycle back track
        for (int i = s; i < candidates.length; i++) {
            box.add(candidates[i]);

            backTrack4(candidates, i + 1, sum + candidates[i], target, box, ans);

            // [IM]
            // Skip the same element
            while(i + 1 < candidates.length && candidates[i] == candidates[i + 1]){
                i++;
            }

            box.remove(box.size() - 1);
        }
    }


    public List<List<Integer>> permute(int[] nums){
        // Answer collection
        List<List<Integer>> ans = new ArrayList<>();

        // Check validate
        if (ans == null)
            throw new IllegalArgumentException("Candidates is null!");

        // BackTack
        backTrack5(nums, 0, ans);

        return ans;
    }

    private void backTrack5(int[] nums, int s, List<List<Integer>> ans) {
        if(nums.length == s){
            ans.add(new ArrayList<>());

            for (var ele : nums)
                ans.get(ans.size() - 1).add(ele);

            return;
        }

        // Check validate

        // BackTrack
        for (int i = s; i < nums.length; i++) {
            // [IM]
            // Swap
            swap(nums, i, s);

            // s + 1
            backTrack5(nums, s + 1, ans);

            swap(nums, i, s);
        }
    }

    private void swap(int[] nums, int i, int s) {
        if(i == s)
            return;

        nums[i] ^= nums[s];
        nums[s] ^= nums[i];
        nums[i] ^= nums[s];
    }

    /***
     * 90. 子集 II
     * 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。
     *
     * 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
     *
     *
     *
     * 示例 1：
     *
     * 输入：nums = [1,2,2]
     * 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
     * 示例 2：
     *
     * 输入：nums = [0]
     * 输出：[[],[0]]
     *
     *
     * 提示：
     *
     * 1 <= nums.length <= 10
     * -10 <= nums[i] <= 10
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsWithDup(int[] nums){
        // Answer collection
        List<List<Integer>> ans = new ArrayList<>();

        // Check validate
        if (ans == null)
            throw new IllegalArgumentException("nums is null!");

        // Sort
        Arrays.sort(nums);

        ArrayList<Integer> box = new ArrayList<>();
        // BackTack
        backTrack6(nums, 0, box, ans);

        return ans;
    }

    private void backTrack6(int[] nums, int s, ArrayList<Integer> box, List<List<Integer>> ans) {
        ans.add(new ArrayList<>());
        for (var ele : box){
            ans.get(ans.size() - 1).add(ele);
        }

        // Check validate
        if(s >= nums.length)
            return;

        for (int i = s; i < nums.length; i++) {
            box.add(nums[i]);

            backTrack6(nums,i + 1, box, ans);

            // [IM]
            while (i + 1 < nums.length && nums[i] == nums[i + 1])
                i++;

            box.remove(box.size() - 1);
        }
    }


    /***
     * [IM]
     * 47. 全排列 II
     * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
     *
     *
     *
     * 示例 1：
     *
     * 输入：nums = [1,1,2]
     * 输出：
     * [[1,1,2],
     *  [1,2,1],
     *  [2,1,1]]
     * 示例 2：
     *
     * 输入：nums = [1,2,3]
     * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     *
     *
     * 提示：
     *
     * 1 <= nums.length <= 8
     * -10 <= nums[i] <= 10
     * @param nums
     * @return
     */
    public List<List<Integer>> permuteUnique(int[] nums){
        // Answer collection
        List<List<Integer>> ans = new ArrayList<>();

        // Check validate
        if (ans == null)
            throw new IllegalArgumentException("nums is null!");

        // Sort
//        Arrays.sort(nums);

        // BackTack
        backTrack7(nums, 0, ans);

//        System.out.println(ans.size());

        return ans;
    }

    private void backTrack7(int[] nums, int s, List<List<Integer>> ans) {
        if(s == nums.length){
            ans.add(new ArrayList<>());
            for (var ele : nums){
                ans.get(ans.size() - 1).add(ele);
            }
        }

        // Check validate
        if(s >= nums.length)
            return;

        for (int i = s; i < nums.length; i++) {
            // [IM]
//            if (i + 1 < nums.length && nums[i] == nums[i + 1]) {
//                continue;
//            }
            if(findSameValue(nums, s, i))
                continue;

            swap(nums, i, s);

            backTrack7(nums,s + 1, ans);

            swap(nums, i, s);
        }
    }

    private boolean findSameValue(int[] nums, int s, int e) {
        var val = nums[e];
        for (int j = s; j < e; j++) {
            if (nums[e] == nums[j])
                return true;
        }

        return false;
    }


}
