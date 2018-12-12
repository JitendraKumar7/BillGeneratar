package com.agira.bhinfotech.utility;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

//number to convert words android
public class EnglishNumberToWords {

    private static final String[] tens = {"", "", " Twenty", " Thirty",
            " Forty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"};

    private static final String[] units = {"", " One", " Two", " Three", " Four", " Five",
            " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve", " Thirteen",
            " Fourteen", " Fifteen", " Sixteen", " Seventeen", " Eighteen", " Nineteen"};


    private static String convert(final int number) {
        if (number < 0) {
            return String.format("minus %s", convert(-number));
        }

        if (number < 20) {
            return units[number];
        }

        if (number < 100) {
            return String.format("%s%s%s", tens[number / 10], (number % 10 != 0) ? " " : "", units[number % 10]);
        }

        if (number < 1000) {
            return String.format("%s Hundred %s%s", units[number / 100], (number % 100 != 0) ? " " : "", convert(number % 100));
        }

        if (number < 1000000) {
            return String.format("%s Thousand %s%s", convert(number / 1000), (number % 1000 != 0) ? " " : "", convert(number % 1000));
        }

        if (number < 1000000000) {
            return String.format("%s Million %s%s", convert(number / 1000000), (number % 1000000 != 0) ? " " : "", convert(number % 1000000));
        }

        return String.format("%s Billion %s%s", convert(number / 1000000000), (number % 1000000000 != 0) ? " " : "", convert(number % 1000000000));
    }

    public static String doubleConvert(final double number) {
        String result = String.valueOf(number);

        StringTokenizer token = new StringTokenizer(result, ".");
        String first = token.nextToken();
        String last = token.nextToken();
        try {
            result = String.format("%s ", convert(Integer.parseInt(first)));
            result = String.format("%s AND", result);
            for (int i = 0; i < last.length(); i++) {
                String get = convert(Integer.parseInt(String.valueOf(last.charAt(i))));
                if (get.isEmpty()) {
                    result = String.format("%s ZERO", result);
                } else {
                    result = String.format("%s %s", result, get);
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return String.format("INR %s Only!", result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " "));
    }

}
