import bangladehi_number_format_util.BangladeshiNumberFormatUtil;

public class Main {
    public static void main(String[] args) {
        String numStr = "৮৮১২১৭";
        String input = BangladeshiNumberFormatUtil.getFormattedNumber(numStr);
        System.out.println(input);
        System.out.println(
                BangladeshiNumberFormatUtil.convertToWord(numStr, "bangla")
        );
    }
}
