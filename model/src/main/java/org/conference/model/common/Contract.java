package org.conference.model.common;

/**
 * A specification (like a business contract) used to check that all specified preconditions, in order to invoke an
 * operation, are met.
 * <p>
 * More info on design by contract:
 * <a href="https://en.wikipedia.org/wiki/Design_by_contract">https://en.wikipedia.org/wiki/Design_by_contract</a>
 */
public class Contract {

    /**
     * @throws ContractException if the precondition fails.
     */
    public static void requires(boolean precondition) throws ContractException {
        if (!precondition)
            throw new ContractException();
    }

    /**
     * @throws ContractException if the precondition fails.
     */
    public static void requires(boolean precondition, String message) throws ContractException {
        if (!precondition)
            throw new ContractException(message);
    }

    /**
     * @throws ContractException if the result fails.
     */
    public static void requiresSuccess(Result result) throws ContractException {
        if (result.isFailure())
            throw new ContractException(result.getErrorMessage());
    }
}
