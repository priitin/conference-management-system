package org.conference.model.common;

import lombok.Getter;

/**
 * Base class for all Entities. Entities differ from Value Objects in that they have a unique identifier ({@code id})
 * and usually have a corresponding table in a database.
 * <p>
 * More info on Entities and Value Objects:<br>
 * <a href="https://enterprisecraftsmanship.com/posts/entity-vs-value-object-the-ultimate-list-of-differences">
 *     https://enterprisecraftsmanship.com/posts/entity-vs-value-object-the-ultimate-list-of-differences
 * </a>
 */
@Getter
public abstract class Entity {
    protected int id;

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (this.getClass() != obj.getClass())
            return false;

        var entity = (Entity)obj;

        if (this.id == 0 || entity.id == 0)
            return false;

        return this.id == entity.id;
    }

    @Override
    public int hashCode() {
        return (this.getClass().toString() + this.id).hashCode();
    }
}
