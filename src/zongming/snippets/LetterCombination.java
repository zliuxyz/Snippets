package zongming.snippets;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by harding on 2017-04-26.
 */
public class LetterCombination {

    private List<String> letterCombinations(String digits) {
        LinkedList<String> ans = new LinkedList<String>();
        if (digits.length() == 0) {
            return ans;
        }
        String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        for(int i = 0; i < digits.length(); i++) {
            int x = Character.getNumericValue(digits.charAt(i));
            while(ans.peek().length() == i){
                String t = ans.remove();
                for(char s : mapping[x].toCharArray())
                    ans.add(t+s);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        LetterCombination letterCombination = new LetterCombination();
        String input = "01";
        System.out.println(letterCombination.letterCombinations(input));
    }
}
