package zongming.snippets;

import java.util.Arrays;

/**
 * Created by harding on 2017-04-21.
 */
public class FindLongestPalindrome {
    private int lo, maxLen;

    private String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        } else {
            for (int i = 0; i < len-1; i++) {
                extendPalindrome(s, i, i);
                extendPalindrome(s, i, i+1);
            }
            return s.substring(lo, lo + maxLen);
        }
    }

    private void extendPalindrome(String s, int j, int k) {
        while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
            j--;
            k++;
        }
        if (maxLen < k - j - 1) {
            lo = j+1;
            maxLen = k - j - 1;
        }
    }

    private static String longestCommonSubstring(String s) {
        int len = s.length();
        char[] arr = s.toCharArray();
        int pairs = len / 2;
        for (int i = 0; i < pairs; i++) {
            char temp = arr[i];
            arr[i] = arr[len - 1 - i];
            arr[len - 1 - i] = temp;
        }
        String reversed = new String(arr);
        String ans = "";
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                if (s.substring(i, j + 1).equals(reversed.substring(len - 1 - j, len - i)) && s.substring(i, j + 1).length() > ans.length()) {
                    ans = s.substring(i, j + 1);
                }
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        String myString = "cbbd";
        System.out.println(longestCommonSubstring(myString));
    }
}
