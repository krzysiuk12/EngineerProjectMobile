package pl.edu.agh.tools;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public class StringTools {

    public static final char DEFAULT_SEPARATOR = ' ';

    public static String concatenateString(String... arguments) {
        StringBuilder builder = new StringBuilder();
        for(String arg : arguments) {
            builder.append(arg).append(DEFAULT_SEPARATOR);
        }
        builder.replace(builder.length() - 1, builder.length(), "");
        return builder.toString();
    }

    public static String concatenateString(char separator, String... arguments) {
        StringBuilder builder = new StringBuilder();
        for(String arg : arguments) {
            builder.append(arg).append(separator);
        }
        builder.replace(builder.length() - 1, builder.length(), "");
        return builder.toString();
    }

    public static boolean isNullOrEmpty(String testedString) {
        return testedString == null || testedString.trim().isEmpty();
    }

}
