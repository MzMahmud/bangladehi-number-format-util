package com.moazmahmud;

import java.util.*;
import java.util.stream.Collectors;

public class CharProcessors {
    private static final Map<String, List<String>> digitsByLanguage = new HashMap<>();

    static {
        digitsByLanguage.put(
                Constants.BANGLA, Arrays.asList("০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯")
        );
        digitsByLanguage.put(
                Constants.ENGLISH, Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        );
    }

    public static String findOutLanguage(String numStr) throws Exception {
        List<String> numStrDigitSet = numStr.chars()
                                            .mapToObj(c -> (char) c)
                                            .map(String::valueOf)
                                            .collect(Collectors.toList());
        numStrDigitSet.remove(".");
        numStrDigitSet.remove("+");
        numStrDigitSet.remove("-");

        return digitsByLanguage.entrySet()
                               .stream()
                               .filter(langDigits -> isSubsetOf(numStrDigitSet, langDigits.getValue()))
                               .map(Map.Entry::getKey)
                               .findFirst()
                               .orElseThrow(() -> new Exception("unsupported language for digits"));
    }

    public static String convertString(String numStr, String fromLanguage, String toLanguage) {
        return numStr.chars()
                     .mapToObj(c -> String.valueOf((char) c))
                     .map(c -> convertCharacter(c, fromLanguage, toLanguage))
                     .collect(Collectors.joining(""));
    }

    public static String convertIndex(int index, String toLanguage) throws Exception {
        if (!isDigit(index))
            throw new Exception("Must be a digit [0,9]");

        return digitsByLanguage.get(toLanguage).get(index);
    }

    public static void reverseStrArrayInPlace(String[] a) {
        int i = 0, j = a.length - 1;
        while (i <= j) {
            String temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            i++;
            j--;
        }
    }

    private static boolean isDigit(int digit) {
        return 0 <= digit && digit <= 9;
    }

    // returns a isSubSet b
    private static boolean isSubsetOf(List<String> a, List<String> b) {
        return new HashSet<>(b).containsAll(a);
    }

    private static String convertCharacter(String character, String fromLanguage, String toLanguage) {
        int digitIndex = digitsByLanguage.get(fromLanguage).indexOf(character);
        if (digitIndex < 0) return character;
        return digitsByLanguage.get(toLanguage).get(digitIndex);
    }
}
