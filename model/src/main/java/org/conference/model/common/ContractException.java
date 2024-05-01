package org.conference.model.common;

/**
 * Used in case a precondition is violated.
 */
public class ContractException extends Exception {

    public ContractException() {
        super();
    }

    public ContractException(String message) {
        super(message);
    }

    public ContractException(String message, Throwable cause) {
        super(message, cause);
    }
}
