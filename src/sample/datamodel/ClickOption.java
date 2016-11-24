package sample.datamodel;

/**
 * Created by andyr on 29/07/2016.
 */
public class ClickOption {

    private String action;

    public ClickOption(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return action;
    }
}
