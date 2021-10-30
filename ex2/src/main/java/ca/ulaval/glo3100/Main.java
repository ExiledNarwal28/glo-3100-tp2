package ca.ulaval.glo3100;

import ca.ulaval.glo3100.keypass.KeyPassOperation;
import ca.ulaval.glo3100.keypass.KeyPassOperationAssembler;

public class Main {

    public static void main(String[] args) {
        KeyPassOperation keyPassOperation = KeyPassOperationAssembler.assemble(args);
        keyPassOperation.execute();
    }
}

