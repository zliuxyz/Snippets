package zongming.snippets;

public class Test {
    public static void main(String[] args) {
        String s = "kyyrjtdplseovzwjkykrjwhxquwxsfsorjiumvxjhjmgeueafubtonhlerrgsgohfosqssmizcuqryqomsipovhhodpfyudtusjhonlqabhxfahfcjqxyckycstcqwxvicwkjeuboerkmjshfgiglceycmycadpnvoeaurqatesivajoqdilynbcihnidbizwkuaoegmytopzdmvvoewvhebqzskseeubnretjgnmyjwwgcooytfojeuzcuyhsznbcaiqpwcyusyyywqmmvqzvvceylnuwcbxybhqpvjumzomnabrjgcfaabqmiotlfojnyuolostmtacbwmwlqdfkbfikusuqtupdwdrjwqmuudbcvtpieiwteqbeyfyqejglmxofdjksqmzeugwvuniaxdrunyunnqpbnfbgqemvamaxuhjbyzqmhalrprhnindrkbopwbwsjeqrmyqipnqvjqzpjalqyfvaavyhytetllzupxjwozdfpmjhjlrnitnjgapzrakcqahaqetwllaaiadalmxgvpawqpgecojxfvcgxsbrldktufdrogkogbltcezflyctklpqrjymqzyzmtlssnavzcquytcskcnjzzrytsvawkavzboncxlhqfiofuohehaygxidxsofhmhzygklliovnwqbwwiiyarxtoihvjkdrzqsnmhdtdlpckuayhtfyirnhkrhbrwkdymjrjklonyggqnxhfvtkqxoicakzsxmgczpwhpkzcntkcwhkdkxvfnjbvjjoumczjyvdgkfukfuldolqnauvoyhoheoqvpwoisniv";
        String ans = longestPalindrome(s);
        System.out.println(ans);
    }

    private static String longestPalindrome(String s) {
        String ans = s.substring(0, 1);
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                String cur = s.substring(i, j + 1);
                if (cur.length() > ans.length()) {
                    int pairs = cur.length() / 2;
                    int x = 0;
                    int y = cur.length() - 1;
                    while (pairs > 0) {
                        if (cur.charAt(x) == cur.charAt(y)) {
                            x++;
                            y--;
                            pairs--;
                        } else {
                            break;
                        }
                    }
                    if (pairs <= 0 && cur.length() > ans.length()) {
                        ans = cur;
                    }
                }
            }
        }
        return ans;
    }

}
