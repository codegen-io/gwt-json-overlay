package io.codegen.gwt.jsonoverlay.processor.builder;

import java.util.Locale;

public class NameConverter {

    /**
     * E.g. convert "someCaseName" to "SOME_CASE_NAME"
     */
    public static String convertCamelcaseToSnakecase(String name) {
        return name.replaceAll("(.)(\\p{Upper})", "$1_$2");
    }

    /**
     * E.g. convert "SOME_CASE_NAME" to "someCaseName"
     */
    public static String convertSnakeCaseToLowerCamelCase(String name) {
        String[] values = name.split("_");

        StringBuilder result = new StringBuilder();
        for (String value : values) {
            result.append(value.substring(0, 1).toUpperCase(Locale.ROOT));
            if (value.length() > 1) {
                result.append(value.substring(1, value.length()).toLowerCase(Locale.ROOT));
            }
        }
        result.setCharAt(0, Character.toLowerCase(result.charAt(0)));
        return result.toString();
    }

    /**
     * E.g. convert "SomeCaseName" to "someCaseName"
     */
    public static String convertUpperCamelCaseToLowerCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        result.append(Character.toLowerCase(name.charAt(0)));
        result.append(name.substring(1));
        return result.toString();
    }

}
