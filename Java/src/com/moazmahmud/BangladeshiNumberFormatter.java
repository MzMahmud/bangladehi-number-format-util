package com.moazmahmud;

import java.util.regex.Pattern;

public class BangladeshiNumberFormatter {
    public static String getFormattedNumber(String numStr) {
        if(numStr == null || numStr.isEmpty()) return "";

        StringBuilder formattedNumber = new StringBuilder();

        if (numStr.startsWith("-") || numStr.startsWith("+")) {
            formattedNumber.append(numStr.charAt(0));
            numStr = numStr.substring(1);
        }

        String[] decimalPointSplit = numStr.split(Pattern.quote("."));

        if (numStr.startsWith("."))
            formattedNumber.append(".").append(decimalPointSplit[0]);
        else
            formattedNumber.append(getFormattedInteger(decimalPointSplit[0]));

        if (decimalPointSplit.length > 1)
            formattedNumber.append(".").append(decimalPointSplit[1]);

        return formattedNumber.toString();
    }

    public static String[] getLakhCroreSplit(String numStr) {
        if (numStr.length() <= 7)
            return new String[]{numStr, ""};

        String[] lakhCrore = new String[]{"", ""};
        int charCount = 0, lakhCroreIndex = 0;
        for (int i = numStr.length() - 1; i >= 0; i--) {
            charCount++;
            char charAtI = numStr.charAt(i);

            if (charCount > 7) lakhCroreIndex = 1;
            lakhCrore[lakhCroreIndex] = charAtI + lakhCrore[lakhCroreIndex];
        }
        return lakhCrore;
    }

    private static String getFormattedInteger(String numStr){
        if (numStr.length() <= 7)
            return getFormattedNumberBelowCrore(numStr);

        String[] lakhCrore = getLakhCroreSplit(numStr);

        return getFormattedNumber(lakhCrore[1])
                .concat(",")
                .concat(getFormattedNumberBelowCrore(lakhCrore[0]));
    }

    private static String getFormattedNumberBelowCrore(String numStr) {
        StringBuilder reverseFormattedNumber = new StringBuilder();
        int charCount = 0;
        for (int i = numStr.length() - 1; i >= 0; i--) {
            charCount++;
            char charAtI = numStr.charAt(i);
            if (isSeparator(charCount)) {
                reverseFormattedNumber.append(",");
            }
            reverseFormattedNumber.append(charAtI);
        }
        return reverseFormattedNumber.reverse().toString();
    }

    private static boolean isSeparator(int charOrder) {
        return charOrder == 4
                || (charOrder > 4 && charOrder % 2 == 0);
    }
}
