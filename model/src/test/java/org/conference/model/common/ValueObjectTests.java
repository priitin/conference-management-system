package org.conference.model.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ValueObjectTests {

    private static Stream<Arguments> valueObjectsDataSource() {
        return Stream.of(
                Arguments.of(new TestValueObject(1, "a"), new TestValueObject(1, "a"), true),
                Arguments.of(new TestValueObject(1, "a"), new TestValueObject(1, "b"), false),
                Arguments.of(new TestValueObject(1, "a"), new TestValueObject2(1, "a"), false),
                Arguments.of(new TestValueObject(1, "a"), null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("valueObjectsDataSource")
    public void two_value_objects_should_only_equal_if_their_values_match(TestValueObject valueObject, Object object,
                                                                          boolean expectedResult) {
        var equals = valueObject.equals(object);

        Assertions.assertEquals(expectedResult, equals);
    }

    public static class TestValueObject extends ValueObject {
        private final int value1;
        private final String value2;

        public TestValueObject(int value1, String value2) {
            this.value1 = value1;
            this.value2 = value2;
        }

        @Override
        protected Stream<Object> getEqualityComponents() {
            return Stream.of(value1, value2);
        }
    }

    public static class TestValueObject2 extends ValueObject {
        private final int value1;
        private final String value2;

        public TestValueObject2(int value1, String value2) {
            this.value1 = value1;
            this.value2 = value2;
        }

        @Override
        protected Stream<Object> getEqualityComponents() {
            return Stream.of(value1, value2);
        }
    }
}
