package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import sample.datamodel.Account;

/**
 * Created by andyr on 19/09/2016.
 */
public class createAccountController {

    @FXML
    private TextField name;

    @FXML
    private TextField emailAddress;

    @FXML
    private TextField password;

    @FXML
    private Spinner accountBalance;


    public Account processResults(){
        String loginName = name.getText().trim();
        String loginEmailAddress = emailAddress.getText().trim();
        String loginPassword = password.getText().trim();
        int loginDeposit = (Integer) accountBalance.getValue();

        if(loginName.isEmpty() || loginEmailAddress.isEmpty() || loginPassword.isEmpty()){
            return null;
        }else{
            Account newAccount = new Account(loginName, loginEmailAddress, loginPassword, loginDeposit);
            return newAccount;
        }

    }
}
