package org.vorin.bestwords.util;

import java.util.regex.Pattern;

import static com.google.common.base.Strings.isNullOrEmpty;

public class LangUtil {

    /**
     * 1. if matches the below remove
     * -
     *
     * 2. if the first word followd by one of the below take just the first word:
     * a algo
     * algo a
     * a alguien
     * alguien a
     * a
     * algo
     * alguien
     *
     * 3. if 2 words and ending with the below remove the ending
     * que
     * con
     * en
     * de
     *
     * 4. if more than 3 words remove unless matches the below:
     * word de word
     * word la word
     * word el word
     *
     */
    public static String santizeSpanishMeaning(String meaning) {
        meaning = meaning.trim();

        // 1.
        if (meaning.equals("-")) {
            return "";
        }

        // 2.
        int spaceIdx = meaning.indexOf(" ");
        String[] stringsToMatch = {" a algo", " algo a", " a alguien", " alguien a", " a", " algo", " alguien"};

        for (String match : stringsToMatch) {
            int idx = meaning.indexOf(match);
            if (idx > 0 && idx == spaceIdx) {
                return meaning.substring(0, idx);
            }
        }

        int wordCount = wordCount(meaning);

        // 3.
        String[] stringsToMatch2 = {" que", " con", " de", " en"};
        if (wordCount == 2) {
            for (String match : stringsToMatch2) {
                if (meaning.substring(meaning.length() - match.length()).equals(match)) {
                    return meaning.substring(0, meaning.length() - match.length());
                }
            }
        }

        // 4.
        String[] stringsToMatch3 = {" de ", " la ", " el "};
        spaceIdx = meaning.indexOf(" ");
        if (wordCount == 3) {
            for (String match : stringsToMatch3) {
                int idx = meaning.indexOf(match);
                if (idx > 0 && idx == spaceIdx) {
                    return meaning;
                }
            }
        }
        if (wordCount > 2) {
            return "";
        }


        return meaning;
    }


    public static int wordCount(String meaning) {
        if (isNullOrEmpty(meaning)) {
            return 0;
        }
        int spaceCount = 0;
        String meaningPart = meaning;

        int spaceIdx = meaningPart.indexOf(" ");
        while (spaceIdx > 0) {
            meaningPart = meaningPart.substring(spaceIdx + 1);
            spaceIdx = meaningPart.indexOf(" ");
            spaceCount++;
        }
        return spaceCount + 1;
    }


    public static int wordCount2(String s){

        int wordCount = 0;

        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            // if the char is a letter, word = true.
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
                // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
                // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }

}
