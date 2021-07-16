/*
 * This is not actually implemented with Functional Style, but Kept it in here as I have no other dedicated Java resources
 * @author Moaz Mahmud
 * @Date 13 July 2021
 * @Usage
 * static String getFormattedNumber(String numStr)
 *               given a number String, return the Bangladeshi Number Format
 *               Bangladeshi Number Format is the Crore-Lakh System seperated by Comma
 *
 * static String convertToWord(String numStr, String language)
 *               given a number String and language in {"bangla", "english"}
 *               return the number in words of the specified language
 * */

import java.util.HashMap;
import java.util.Map;

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

    // Language Constant Data
    private static final Map<String, String> numberToWord = new HashMap<>();
    private static final Map<String, String> constantByLanguage = new HashMap<>();

    static {
        constantByLanguage.put("bangla0", "০");
        constantByLanguage.put("bangla00", "০০");
        constantByLanguage.put("bangla100", "১০০");
        constantByLanguage.put("banglaCrore", "কোটি");
        constantByLanguage.put("english0", "0");
        constantByLanguage.put("english00", "00");
        constantByLanguage.put("english100", "100");
        constantByLanguage.put("englishCrore", "crore");

        numberToWord.put("0", "zero");
        numberToWord.put("1", "one");
        numberToWord.put("2", "two");
        numberToWord.put("3", "three");
        numberToWord.put("4", "four");
        numberToWord.put("5", "five");
        numberToWord.put("6", "six");
        numberToWord.put("7", "seven");
        numberToWord.put("8", "eight");
        numberToWord.put("9", "nine");
        numberToWord.put("10", "ten");
        numberToWord.put("11", "eleven");
        numberToWord.put("12", "twelve");
        numberToWord.put("13", "thirteen");
        numberToWord.put("14", "fourteen");
        numberToWord.put("15", "fifteen");
        numberToWord.put("16", "sixteen");
        numberToWord.put("17", "seventeen");
        numberToWord.put("18", "eighteen");
        numberToWord.put("19", "nineteen");
        numberToWord.put("20", "twenty");
        numberToWord.put("30", "thirty");
        numberToWord.put("40", "forty");
        numberToWord.put("50", "fifty");
        numberToWord.put("60", "sixty");
        numberToWord.put("70", "seventy");
        numberToWord.put("80", "eighty");
        numberToWord.put("90", "ninety");
        numberToWord.put("100", "hundred");
        numberToWord.put("comma-split-1", "thousand");
        numberToWord.put("comma-split-2", "lakh");
        numberToWord.put("comma-split-3", "crore");

        // bangla, scrapped from wikipedia and processed with python
        numberToWord.put("০", "শুন্য");
        numberToWord.put("১", "এক");
        numberToWord.put("৩৫", "পঁয়ত্রিশ");
        numberToWord.put("৬৯", "ঊনসত্তর");
        numberToWord.put("২", "দুই");
        numberToWord.put("৩৬", "ছত্রিশ");
        numberToWord.put("৭০", "সত্তর");
        numberToWord.put("৩", "তিন");
        numberToWord.put("৩৭", "সাঁইত্রিশ");
        numberToWord.put("৭১", "একাত্তর");
        numberToWord.put("৪", "চার");
        numberToWord.put("৩৮", "আটত্রিশ");
        numberToWord.put("৭২", "বাহাত্তর");
        numberToWord.put("৫", "পাঁচ");
        numberToWord.put("৩৯", "ঊনচল্লিশ");
        numberToWord.put("৭৩", "তিয়াত্তর");
        numberToWord.put("৬", "ছয়");
        numberToWord.put("৪০", "চল্লিশ");
        numberToWord.put("৭৪", "চুয়াত্তর");
        numberToWord.put("৭", "সাত");
        numberToWord.put("৪১", "একচল্লিশ");
        numberToWord.put("৭৫", "পঁচাত্তর");
        numberToWord.put("৮", "আট");
        numberToWord.put("৪২", "বিয়াল্লিশ");
        numberToWord.put("৭৬", "ছিয়াত্তর");
        numberToWord.put("৯", "নয়");
        numberToWord.put("৪৩", "তেতাল্লিশ");
        numberToWord.put("৭৭", "সাতাত্তর");
        numberToWord.put("১০", "দশ");
        numberToWord.put("৪৪", "চুয়াল্লিশ");
        numberToWord.put("৭৮", "আটাত্তর");
        numberToWord.put("১১", "এগারো");
        numberToWord.put("৪৫", "পঁয়তাল্লিশ");
        numberToWord.put("৭৯", "ঊনআশি");
        numberToWord.put("১২", "বারো");
        numberToWord.put("৪৬", "ছেচল্লিশ");
        numberToWord.put("৮০", "আশি");
        numberToWord.put("১৩", "তেরো");
        numberToWord.put("৪৭", "সাতচল্লিশ");
        numberToWord.put("৮১", "একাশি");
        numberToWord.put("১৪", "চৌদ্দ");
        numberToWord.put("৪৮", "আটচল্লিশ");
        numberToWord.put("৮২", "বিরাশি");
        numberToWord.put("১৫", "পনেরো");
        numberToWord.put("৪৯", "ঊনপঞ্চাশ");
        numberToWord.put("৮৩", "তিরাশি");
        numberToWord.put("১৬", "ষোল");
        numberToWord.put("৫০", "পঞ্চাশ");
        numberToWord.put("৮৪", "চুরাশি");
        numberToWord.put("১৭", "সতেরো");
        numberToWord.put("৫১", "একান্ন");
        numberToWord.put("৮৫", "পঁচাশি");
        numberToWord.put("১৮", "আঠারো");
        numberToWord.put("৫২", "বাহান্ন");
        numberToWord.put("৮৬", "ছিয়াশি");
        numberToWord.put("১৯", "উনিশ");
        numberToWord.put("৫৩", "তিপ্পান্ন");
        numberToWord.put("৮৭", "সাতাশি");
        numberToWord.put("২০", "কুড়ি");
        numberToWord.put("৫৪", "চুয়ান্ন");
        numberToWord.put("৮৮", "অষ্টআশি");
        numberToWord.put("২১", "একুশ");
        numberToWord.put("৫৫", "পঞ্চান্ন");
        numberToWord.put("৮৯", "ঊননব্বই");
        numberToWord.put("২২", "বাইশ");
        numberToWord.put("৫৬", "ছাপ্পান্ন");
        numberToWord.put("৯০", "নব্বই");
        numberToWord.put("২৩", "তেইশ");
        numberToWord.put("৫৭", "সাতান্ন");
        numberToWord.put("৯১", "একানব্বই");
        numberToWord.put("২৪", "চব্বিশ");
        numberToWord.put("৫৮", "আটান্ন");
        numberToWord.put("৯২", "বিরানব্বই");
        numberToWord.put("২৫", "পঁচিশ");
        numberToWord.put("৫৯", "ঊনষাট");
        numberToWord.put("৯৩", "তিরানব্বই");
        numberToWord.put("২৬", "ছাব্বিশ");
        numberToWord.put("৬০", "ষাট");
        numberToWord.put("৯৪", "চুরানব্বই");
        numberToWord.put("২৭", "সাতাশ");
        numberToWord.put("৬১", "একষট্টি");
        numberToWord.put("৯৫", "পঁচানব্বই");
        numberToWord.put("২৮", "আটাশ");
        numberToWord.put("৬২", "বাষট্টি");
        numberToWord.put("৯৬", "ছিয়ানব্বই");
        numberToWord.put("২৯", "ঊনত্রিশ");
        numberToWord.put("৬৩", "তেষট্টি");
        numberToWord.put("৯৭", "সাতানব্বই");
        numberToWord.put("৩০", "ত্রিশ");
        numberToWord.put("৬৪", "চৌষট্টি");
        numberToWord.put("৯৮", "আটানব্বই");
        numberToWord.put("৩১", "একত্রিশ");
        numberToWord.put("৬৫", "পঁয়ষট্টি");
        numberToWord.put("৯৯", "নিরানব্বই");
        numberToWord.put("৩২", "বত্রিশ");
        numberToWord.put("৬৬", "ছেষট্টি");
        numberToWord.put("৩৩", "তেত্রিশ");
        numberToWord.put("৬৭", "সাতষট্টি");
        numberToWord.put("৩৪", "চৌত্রিশ");
        numberToWord.put("৬৮", "আটষট্টি");
        numberToWord.put("১০০", "শত");
        numberToWord.put("comma-split-১", "হাজার");
        numberToWord.put("comma-split-২", "লক্ষ");
        numberToWord.put("comma-split-৩", "কোটি");
    }
}
