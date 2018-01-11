package zongming.snippets;

import java.util.*;

public class TwoSum {
    public static void main(String[] args) {
        int[] nums = new int[3];
        nums[0] = 3;
        nums[1] = 2;
        nums[2] = 4;
        int[] result = twoSum(nums, 6);
        System.out.println(Arrays.toString(result));
    }

    private static int[] twoSum(int[] nums, int target) {
        int[] ans = new int[2];
        HashMap<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                ans[0] = map.get(nums[i]);
                ans[1] = i;
            } else {
                map.put(target - nums[i], i);
            }
        }
        return ans;
    }
}
