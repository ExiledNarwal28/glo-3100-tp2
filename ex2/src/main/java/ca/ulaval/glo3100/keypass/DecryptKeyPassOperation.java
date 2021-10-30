package ca.ulaval.glo3100.keypass;

public class DecryptKeyPassOperation extends BaseKeyPassOperation {
    private int index;
    private boolean decryptUser;
    private boolean decryptPassword;

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
