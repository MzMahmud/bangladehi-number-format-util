package bangladehi_number_format_util;

import static bangladehi_number_format_util.Constants.numberToWord;
import static bangladehi_number_format_util.Constants.constantByLanguage;

public class BangladeshiNumberFormatUtil {

    public static String getFormattedNumber(String numStr) {
        if (numStr.length() <= 7)
            return getFormattedNumberBelowCrore(numStr);

        String[] lakhCrore = getLakhCroreSplit(numStr);

        return getFormattedNumber(lakhCrore[1])
                .concat(",")
                .concat(getFormattedNumberBelowCrore(lakhCrore[0]));
    }

    public static String convertToWord(String numStr, String language) {
        language = language.toLowerCase();
        if (numStr.length() <= 7)
            return convertToWordBelowCrore(getFormattedNumber(numStr), language);

        String[] lakhCrore = getLakhCroreSplit(numStr);

        return convertToWord(lakhCrore[1], language)
                .concat(" ")
                .concat(constantByLanguage.get(language + "Crore"))
                .concat(" ")
                .concat(convertToWordBelowCrore(
                        getFormattedNumber(lakhCrore[0]),
                        language
                ));
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

    private static String[] getLakhCroreSplit(String numStr) {
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

    private static String convertToWordBelowCrore(String bangladeshiCommaFormattedNumber, String language) {
        language = language.toLowerCase();

        String[] splitByComma = bangladeshiCommaFormattedNumber.split(",");
        reverseStrArrayInPlace(splitByComma);

        StringBuilder numberInEng = new StringBuilder();
        for (int commaSplitIndex = splitByComma.length - 1; commaSplitIndex > 0; commaSplitIndex--) {

            String smallerNumInWord = convertToWordBelowHundred(splitByComma[commaSplitIndex], language).trim();

            char indexChar = convertToBanglaDigitWithLanguage(commaSplitIndex, language);
            if (!"".equals(smallerNumInWord)) {
                numberInEng.append(smallerNumInWord)
                           .append(" ")
                           .append(numberToWord.get("comma-split-" + indexChar))
                           .append(" ");
            }
        }
        return numberInEng.append(convertToWordBelowThousand(splitByComma[0], language))
                          .toString();
    }

    private static String convertToWordBelowThousand(String numStr, String language) {
        language = language.toLowerCase();

        if (numStr.length() < 3)
            return convertToWordBelowHundred(numStr, language);

        String hundredthDigit = "" + numStr.charAt(0);
        String belowHundred = numStr.substring(1);

        String const0 = constantByLanguage.get(language + "0");
        if (const0.equals(hundredthDigit))
            return convertToWordBelowHundred(belowHundred, language);

        String const100 = constantByLanguage.get(language + "100");
        return numberToWord.get(hundredthDigit)
                           .concat(numberToWord.get(const100))
                           .concat(" ")
                           .concat(convertToWordBelowHundred(belowHundred, language));
    }

    private static String convertToWordBelowHundred(String numStr, String language) {
        language = language.toLowerCase();

        if (numberToWord.containsKey(numStr))
            return numberToWord.get(numStr);

        String const00 = constantByLanguage.get(language + "00");
        if (const00.equals(numStr))
            return "";

        String tenthsDigit = String.valueOf(numStr.charAt(0));
        String unitsDigit = String.valueOf(numStr.charAt(1));

        String const0 = constantByLanguage.get(language + "0");
        if (const0.equals(tenthsDigit))
            return numberToWord.get(unitsDigit);

        return numberToWord.get(tenthsDigit + const0)
                           .concat(" ")
                           .concat(numberToWord.get(unitsDigit));
    }

    private static void reverseStrArrayInPlace(String[] a) {
        int i = 0, j = a.length - 1;
        while (i <= j) {
            String temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            i++;
            j--;
        }
    }

    private static char convertToBanglaDigitWithLanguage(int digit, String language) {
        char start = "english".equalsIgnoreCase(language) ? '0' : '০';
        return (char) (start + (char) digit);
    }
}
