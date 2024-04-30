package org.conference.model.backoffice;

import lombok.Getter;

public class Participant {
    @Getter
    private final String firstName;
    @Getter
    private final String lastName;

    public Participant(String firstName, String lastName) {
        this.firstName = cleanupName(firstName);
        this.lastName = cleanupName(lastName);
    }

    private static String cleanupName(String name) {
        if (name == null)
            return "";
        else
            return name.strip();
    }

    public String getFullName() {
        return getFormattedName(this.firstName, this.lastName);
    }

    public String getObfuscatedName() {
        return getFormattedName(this.firstName, obfuscateName(this.lastName));
    }

    private static String getFormattedName(String firstName, String lastName) {
        return "%s %s".formatted(firstName, lastName).trim();
    }

    private static String obfuscateName(String name) {
        if (name.isBlank())
            return "";
        else
            return name.charAt(0) + "**********";
    }
}
