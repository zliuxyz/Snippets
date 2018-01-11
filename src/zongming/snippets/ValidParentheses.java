package zongming.snippets;

import java.util.Stack;

/**
 * Created by harding on 2017-04-25.
 */
public class ValidParentheses {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ValidParentheses anVP = new ValidParentheses();
        String aString = "((()))";
        boolean result = anVP.isValid(aString);
        System.out.println(result);
    }
}
