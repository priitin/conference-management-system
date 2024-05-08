package org.conference.model.common;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Base class for all Value Objects. Value Objects differ from Entities in that they have structural equality. This
 * means that two Value Objects are equal if all their members match. Value Objects do not have a corresponding table
 * in a database.
 * <p>
 * More info on Entities and Value Objects:<br>
 * <a href="https://enterprisecraftsmanship.com/posts/entity-vs-value-object-the-ultimate-list-of-differences">
 *     https://enterprisecraftsmanship.com/posts/entity-vs-value-object-the-ultimate-list-of-differences
 * </a>
 */
public abstract class ValueObject {

    protected abstract Stream<Object> getEqualityComponents();

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (this.getClass() != obj.getClass())
            return false;

        var valueObject = (ValueObject)obj;
        return Arrays.equals(
                this.getEqualityComponents().toArray(),
                valueObject.getEqualityComponents().toArray());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.getEqualityComponents().toArray());
    }
}
