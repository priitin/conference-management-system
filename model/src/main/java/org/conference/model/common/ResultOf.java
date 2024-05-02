package org.conference.model.common;

import lombok.SneakyThrows;

/**
 * Represents an operation that can succeed or fail. Includes a value if the operation succeeds.
 */
public class ResultOf<T> extends Result {
    private final T value;

    protected ResultOf(T value, boolean isSuccess, String error) {
        super(isSuccess, error);
        this.value = value;
    }

    @SneakyThrows
    public T getValue() {
        var asd = this.getClass();
        Contract.Requires(this.isSuccess(),
                "Cannot get the value from %s with failed result: %s".formatted(
                        this.getClass().getSimpleName(), this.getErrorMessage()));

        return this.value;
    }
}
