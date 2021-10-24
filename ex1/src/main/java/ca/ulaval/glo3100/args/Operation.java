package ca.ulaval.glo3100.args;

import java.util.HashMap;
import java.util.Map;

public enum Operation {
    ENCRYPT("enc"),
    DECRYPT("dec");

    private final String operation;
    private static final Map<String, Operation> lookup = new HashMap<>();

    static {
        for (Operation operation : Operation.values()) {
            lookup.put(operation.toString(), operation);
        }
    }

    Operation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return operation;
    }

    public static Operation get(String operation) {
        if (operation == null) throwInvalidOperation();

        Operation foundOperation = lookup.get(operation.toLowerCase());

        if (foundOperation == null) throwInvalidOperation();

        return foundOperation;
    }

    private static void throwInvalidOperation() {
        throw new IllegalArgumentException("Invalid operation");
    }
}