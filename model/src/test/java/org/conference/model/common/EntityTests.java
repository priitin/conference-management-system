package org.conference.model.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class EntityTests {

    private static Stream<Arguments> entitiesDataSource() {
        return Stream.of(
                Arguments.of(new TestEntity(1), new TestEntity(1), true),
                Arguments.of(new TestEntity(1), new TestEntity(2), false),
                Arguments.of(new TestEntity(), new TestEntity(), false),
                Arguments.of(new TestEntity(1), new TestEntity2(1), false),
                Arguments.of(new TestEntity(1), null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("entitiesDataSource")
    public void two_entities_should_only_equal_if_their_identities_match(TestEntity entity, Object object,
                                                                         boolean expectedResult) {
        var equals = entity.equals(object);

        Assertions.assertEquals(expectedResult, equals);
    }

    public static class TestEntity extends Entity {

        public TestEntity() {
        }

        public TestEntity(int id) {
            this.id = id;
        }
    }

    public static class TestEntity2 extends Entity {

        public TestEntity2(int id) {
            this.id = id;
        }
    }
}
