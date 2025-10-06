package utils;

public class StringUtils {
    public static String extractQuotedString(String[] parts, int startIndex) {
        if (parts[startIndex].startsWith("\"")) {
            StringBuilder sb = new StringBuilder();
            int i = startIndex;
            while (i < parts.length) {
                if (i > startIndex) sb.append(" ");
                sb.append(parts[i]);
                if (parts[i].endsWith("\"")) {
                    break;
                }
                i++;
            }
            String result = sb.toString();
            return result.substring(1, result.length() - 1);
        }
        return parts[startIndex];
    }

    public static int getNextIndexAfterQuoted(String[] parts, int startIndex) {
        if (parts[startIndex].startsWith("\"")) {
            int i = startIndex;
            while (i < parts.length && !parts[i].endsWith("\"")) {
                i++;
            }
            return i + 1;
        }
        return startIndex + 1;
    }
}
