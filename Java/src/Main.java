import com.moazmahmud.BangladeshiNumberFormatter;
import com.moazmahmud.BangladeshiNumberInWord;
import com.moazmahmud.CharProcessors;
import com.moazmahmud.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        List<String> inputs = Arrays.asList(
                "-456444064",
                "9",
                "11",
                "50",
                "69",
                "100",
                "103",
                "150",
                "200",
                "404",
                "420",
                "85154646464",
                "-64679793.141556",
                "-9790106"
        );
        inputs.forEach(Main::testInput);

        List<String> banglaInputs =
                inputs.stream()
                      .map(num -> CharProcessors.convertString(num, Constants.ENGLISH, Constants.BANGLA))
                      .collect(Collectors.toList());
        banglaInputs.forEach(Main::testInput);
    }

    private static void testInput(String input) {
        System.out.println("input >>> " + input);

        String formattedNumber = BangladeshiNumberFormatter.getFormattedNumber(input);
        System.out.println("formatted number >>> " + formattedNumber);

        try {
            String numberInWord = BangladeshiNumberInWord.convertToWord(input);
            System.out.println("number in word >>> " + numberInWord);
            System.out.println("-------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
