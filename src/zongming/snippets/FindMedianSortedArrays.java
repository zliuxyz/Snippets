package zongming.snippets;

public class FindMedianSortedArrays {
    public static void main(String[] args) {
        int[] nums1 = {};
        int[] nums2 = {2, 3};
        System.out.println(findMedianSortedArrays(nums1, nums2));

    }
    private static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length + nums2.length];
        int len = result.length;
        int i = 0;
        int j = 0;
        for (int k = 0; k < len; k++) {
            if (i < nums1.length && j < nums2.length) {
                if (nums1[i] < nums2[j]) {
                    result[k] = nums1[i];
                    i++;
                } else {
                    result[k] = nums2[j];
                    j++;
                }
            } else if (i < nums1.length) {
                result[k] = nums1[i];
                i++;
            } else if (j < nums2.length) {
                result[k] = nums2[j];
                j++;
            }
        }
        if (len % 2 != 0) {
            return (double)result[(len - 1) / 2];
        } else {
            return (result[(len - 1) / 2] + result[(len - 1) / 2 + 1]) / 2;
        }
    }
}
