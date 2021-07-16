package com.moazmahmud;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.moazmahmud.CharProcessors.*;
import static com.moazmahmud.Constants.numberToWord;
import static com.moazmahmud.Constants.constantByLanguage;

public class BangladeshiNumberInWord {
    public static String convertToWord(String numStr) throws Exception {
        String language = findOutLanguage(numStr);

        StringBuilder inWord = new StringBuilder();

        boolean startsWithMinus = numStr.startsWith("-");
        boolean startsWithPlus = numStr.startsWith("+");
        if (startsWithPlus || startsWithMinus) {
            numStr = numStr.substring(1);
            inWord.append(
                    startsWithMinus ? constantByLanguage.get(language + "-") + " "
                                    : " "
            );
        }

        String[] decimalPointSplit = numStr.split(Pattern.quote("."));

        inWord.append(convertToWordInteger(decimalPointSplit[0], language));

        if (decimalPointSplit.length > 1)
            inWord.append(" ")
                  .append(constantByLanguage.get(language + '.'))
                  .append(" ")
                  .append(convertToWordAfterDecimalPoint(decimalPointSplit[1], language));

        return inWord.toString();
    }

    private static String convertToWordAfterDecimalPoint(String numStr, String language) {
        if (numStr == null || numStr.isEmpty()) return "";

        return numStr.chars()
                     .mapToObj(c -> String.valueOf((char) c))
                     .map(digitStr -> convertToWordBelowHundred(digitStr, language))
                     .collect(Collectors.joining(" "));
    }

    private static String convertToWordInteger(String numStr, String language) throws Exception {
        if (numStr.length() <= 7)
            return convertToWordBelowCrore(
                    BangladeshiNumberFormatter.getFormattedNumber(numStr),
                    language
            );

        String[] lakhCrore = BangladeshiNumberFormatter.getLakhCroreSplit(numStr);

        return convertToWordInteger(lakhCrore[1], language)
                .concat(" ")
                .concat(constantByLanguage.get(language + "Crore"))
                .concat(" ")
                .concat(convertToWordBelowCrore(
                        BangladeshiNumberFormatter.getFormattedNumber(lakhCrore[0]),
                        language
                ));
    }

    private static String convertToWordBelowCrore(String bangladeshiCommaFormattedNumber, String language) throws Exception {
        language = language.toLowerCase();

        String[] splitByComma = bangladeshiCommaFormattedNumber.split(",");
        reverseStrArrayInPlace(splitByComma);

        StringBuilder numberInEng = new StringBuilder();
        for (int commaSplitIndex = splitByComma.length - 1; commaSplitIndex > 0; commaSplitIndex--) {

            String smallerNumInWord = convertToWordBelowHundred(splitByComma[commaSplitIndex], language).trim();

            String indexChar = convertIndex(commaSplitIndex, language);
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
}
