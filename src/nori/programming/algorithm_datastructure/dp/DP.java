package nori.programming.algorithm_datastructure.dp;

import java.util.Arrays;

public class DP {
    public static void main(String[] args) {
        DP dp = new DP();

//        System.out.println(Integer.valueOf(0x0001));

        System.out.println(dp.canPartition(new int[]{2,2,2,4,1}));
    }
    /***
     * 198. 打家劫舍
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     *
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     *
     *
     *
     * 示例 1：
     *
     * 输入：[1,2,3,1]
     * 输出：4
     * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     *      偷窃到的最高金额 = 1 + 3 = 4 。
     * 示例 2：
     *
     * 输入：[2,7,9,3,1]
     * 输出：12
     * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
     *
     *
     * 提示：
     *
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 400
     *
     *
     * dp 函数分析:
     * dp[0] = max(dp[0 - 1], dp[0 - 2] + nums[0])
     * dp[1] = max(dp[1 - 1], dp[1 - 2] + nums[1])
     * dp[2] = max(dp[2 - 1], dp[2 - 2] + nums[2])
     * ...
     * ...
     * ...
     * dp[x] = max(dp[x - 1], dp[x - 2] + nums[x])
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums){
        final int len = nums == null ? 0 : nums.length;

        if(len <= 0)
            return 0;

        int[] dp = new int[len];

        dp[0] = Math.max(0, nums[0]);
        if(len == 1)
           return dp[0];

        dp[1] = Math.max(dp[0], nums[1]);

        for (int i = 2; i < len; i++){
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[len - 1];
    }

    /***
     * 213. 打家劫舍 II
     * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
     *
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
     *
     *
     *
     * 示例 1：
     *
     * 输入：nums = [2,3,2]
     * 输出：3
     * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
     * 示例 2：
     *
     * 输入：nums = [1,2,3,1]
     * 输出：4
     * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
     *      偷窃到的最高金额 = 1 + 3 = 4 。
     * 示例 3：
     *
     * 输入：nums = [0]
     * 输出：0
     *
     *
     * 提示：
     *
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 1000
     * @param nums
     * @return
     */
    public int rob2(int[] nums){
        final int len = nums == null ? 0 : nums.length;

        if(len <= 0)
            return 0;

        int[] dp = new int[len];

        dp[0] = Math.max(0, nums[0]);
        if(len == 1)
            return dp[0];

        dp[1] = Math.max(dp[0], nums[1]);

        // TODO

        return dp[len - 1];

    }


    /***
     * 87. 扰乱字符串
     * 使用下面描述的算法可以扰乱字符串 s 得到字符串 t ：
     * 如果字符串的长度为 1 ，算法停止
     * 如果字符串的长度 > 1 ，执行下述步骤：
     * 在一个随机下标处将字符串分割成两个非空的子字符串。即，如果已知字符串 s ，则可以将其分成两个子字符串 x 和 y ，且满足 s = x + y 。
     * 随机 决定是要「交换两个子字符串」还是要「保持这两个子字符串的顺序不变」。即，在执行这一步骤之后，s 可能是 s = x + y 或者 s = y + x 。
     * 在 x 和 y 这两个子字符串上继续从步骤 1 开始递归执行此算法。
     * 给你两个 长度相等 的字符串 s1 和 s2，判断 s2 是否是 s1 的扰乱字符串。如果是，返回 true ；否则，返回 false 。
     *
     *
     *
     * 示例 1：
     *
     * 输入：s1 = "great", s2 = "rgeat"
     * 输出：true
     * 解释：s1 上可能发生的一种情形是：
     * "great" --> "gr/eat" // 在一个随机下标处分割得到两个子字符串
     * "gr/eat" --> "gr/eat" // 随机决定：「保持这两个子字符串的顺序不变」
     * "gr/eat" --> "g/r / e/at" // 在子字符串上递归执行此算法。两个子字符串分别在随机下标处进行一轮分割
     * "g/r / e/at" --> "r/g / e/at" // 随机决定：第一组「交换两个子字符串」，第二组「保持这两个子字符串的顺序不变」
     * "r/g / e/at" --> "r/g / e/ a/t" // 继续递归执行此算法，将 "at" 分割得到 "a/t"
     * "r/g / e/ a/t" --> "r/g / e/ a/t" // 随机决定：「保持这两个子字符串的顺序不变」
     * 算法终止，结果字符串和 s2 相同，都是 "rgeat"
     * 这是一种能够扰乱 s1 得到 s2 的情形，可以认为 s2 是 s1 的扰乱字符串，返回 true
     * 示例 2：
     *
     * 输入：s1 = "abcde", s2 = "caebd"
     * 输出：false
     * 示例 3：
     *
     * 输入：s1 = "a", s2 = "a"
     * 输出：true
     *
     * @param s1
     * @param s2
     * @return
     */
    public boolean isScramble(String s1, String s2){

        return false;
    }


    /***
     * 416. 分割等和子集
     * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     *
     *
     *
     * 示例 1：
     *
     * 输入：nums = [1,5,11,5]
     * 输出：true
     * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
     * 示例 2：
     *
     * 输入：nums = [1,2,3,5]
     * 输出：false
     * 解释：数组不能分割成两个元素和相等的子集。
     *
     *
     * 提示：
     *
     * 1 <= nums.length <= 200
     * 1 <= nums[i] <= 100
     */
    public boolean canPartition(int[] nums){

        // Cal the sum
        int sum = Arrays.stream(nums).sum();

        // Return false if the sum is a odd number
        if((sum & 0x01) == 0x01)
            return false;

        // Get the half value of sum
        int halfVal = sum >> 1;

        // dp
        boolean[] dp = new boolean[halfVal + 1];
        // Fill dp arr
        dp[0] = true;
        for (Integer ele : nums) {
            for(int val = halfVal; val - ele >= 0; val--){

                final int prevVal = val - ele;
                final int nowVal = val;
                if(dp[prevVal])
                    dp[nowVal] = true;
            }
        }

        return dp[halfVal];
    }


    /***
     * 337. 打家劫舍 III
     * 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
     *
     * 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
     *
     * 示例 1:
     *
     * 输入: [3,2,3,null,3,null,1]
     *
     *      3
     *     / \
     *    2   3
     *     \   \
     *      3   1
     *
     * 输出: 7
     * 解释: 小偷一晚能够盗取的最高金额 = 3 + 3 + 1 = 7.
     * 示例 2:
     *
     * 输入: [3,4,5,1,3,null,1]
     *
     *      3
     *     / \
     *    4   5
     *   / \   \
     *  1   3   1
     *
     * 输出: 9
     * 解释: 小偷一晚能够盗取的最高金额 = 4 + 5 = 9.
     */
    /***
     * Can practise
     * https://leetcode-cn.com/problems/diameter-of-binary-tree/description/
     */
    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    public int rob(TreeNode root){
        int[] re = postRecursiveTraverse(root);
        return Math.max(re[0], re[1]);
    }

    private int[] postRecursiveTraverse(TreeNode root) {
        int[] re = new int[2];

        // Return zero if no node
        if(root == null) {
            return re;
        }

        // Get the child result
        int[] lre = postRecursiveTraverse(root.left);
        int[] rre = postRecursiveTraverse(root.right);

        // 1.using present root node
        int ur = root.val;
        // Exclude the present child of root, just get the max child of child of root
        ur += (lre[1] + rre[1]);

        // 2.no use present root node
        int nur = Math.max(lre[0], lre[1]) + Math.max(rre[0], rre[1]);

        // Fill the result
        re[0] = ur;
        re[1] = nur;

        return re;
    }





}
