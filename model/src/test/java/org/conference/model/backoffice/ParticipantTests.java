package org.conference.model.backoffice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ParticipantTests {

    @Test
    public void full_name_should_be_formatted() {
        var participant = new Participant("Priit", "Aarne");

        var fullName = participant.getFullName();

        Assertions.assertEquals("Priit Aarne", fullName);
    }

    private static Stream<Arguments> emptyNamesDataSource() {
        return Stream.of(
                Arguments.of("Priit", " ", "Priit"),
                Arguments.of(null, "Aarne", "Aarne"),
                Arguments.of(null, null, ""),
                Arguments.of(" ", "   ", ""),
                Arguments.of("", "", "")
        );
    }

    @ParameterizedTest
    @MethodSource("emptyNamesDataSource")
    public void empty_name_should_be_formatted(String firstName, String lastName, String expectedName) {
        var participant = new Participant(firstName, lastName);

        var fullName = participant.getFullName();

        Assertions.assertEquals(expectedName, fullName);
    }

    @ParameterizedTest
    @CsvSource({
            "Priit,Aarne,Priit A**********",
            "Priit,A,Priit A**********",
            "Priit,,Priit",
            ",Aarne,A**********",
    })
    public void name_should_be_obfuscated_to_first_name_and_asterisks(String firstName, String lastName, String expectedName) {
        var participant = new Participant(firstName, lastName);

        var obfuscatedName = participant.getObfuscatedName();

        Assertions.assertEquals(expectedName, obfuscatedName);
    }
}
