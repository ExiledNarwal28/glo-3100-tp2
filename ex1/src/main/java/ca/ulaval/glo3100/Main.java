package ca.ulaval.glo3100;

import ca.ulaval.glo3100.aes.AESService;
import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.args.ArgsAssembler;

public class Main {

    public static void main(String[] args) {
        Args assembledArgs = ArgsAssembler.assemble(args);
        AESService.execute(assembledArgs);
    }
}

