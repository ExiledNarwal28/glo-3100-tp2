package ca.ulaval.glo3100.keypass;

public class DecryptKeyPassOperation extends BaseKeyPassOperation {
    private final int index;
    private final boolean decryptUser;
    private final boolean decryptPassword;

    public DecryptKeyPassOperation(String mainPassword, int index, boolean decryptUser, boolean decryptPassword) {
        super(mainPassword);
        this.index = index;
        this.decryptUser = decryptUser;
        this.decryptPassword = decryptPassword;
    }

    @Override
    public void execute() {
        // TODO : Execute decrypt operation
    }
}
