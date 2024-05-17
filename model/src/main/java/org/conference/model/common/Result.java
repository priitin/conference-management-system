package org.conference.model.common;

/**
 * Represents an operation that can succeed or fail.
 */
public class Result {
    private final boolean isSuccess;
    private final boolean isFailure;
    private final String error;

    protected Result(boolean isSuccess, String error) {
        this.isSuccess = isSuccess;
        this.isFailure = !isSuccess;
        this.error = error;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public boolean isFailure() {
        return this.isFailure;
    }

    public String getErrorMessage() {
        return this.error;
    }

    public static Result succeed() {
        return new Result(true, "");
    }

    public static Result fail() {
        return new Result(false, "");
    }

    public static Result fail(String errorMessage) {
        return new Result(false, errorMessage);
    }

    public static <T> ResultOf<T> succeed(T value) {
        return new ResultOf<>(value, true, "");
    }

    public static <T> ResultOf<T> ofFail() {
        return new ResultOf<>(null, false, "");
    }

    public static <T> ResultOf<T> ofFail(String errorMessage) {
        return new ResultOf<>(null, false, errorMessage);
    }

    @Override
    public String toString() {
        if (this.isSuccess)
            return "Result succeeded";
        else if (StringUtils.isNullOrBlank(this.error))
            return "Result failed: no error message";
        else
            return "Result failed: " + error;
    }
}

