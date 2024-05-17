package org.conference.model.common;

public class StringUtils {

    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }
}
