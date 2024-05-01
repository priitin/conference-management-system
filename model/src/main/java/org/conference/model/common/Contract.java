package org.conference.model.common;

/**
 * A specification (like a business contract) used to check that all specified preconditions, in order to invoke an
 * operation, are met.
 * <p>
 * More info on design by contract:
 * <a href="https://en.wikipedia.org/wiki/Design_by_contract">https://en.wikipedia.org/wiki/Design_by_contract</a>
 */
public class Contract {

    public static void Requires(boolean precondition) throws ContractException {
        if (!precondition)
            throw new ContractException();
    }

    public static void Requires(boolean precondition, String message) throws ContractException {
        if (!precondition)
            throw new ContractException(message);
    }
}
