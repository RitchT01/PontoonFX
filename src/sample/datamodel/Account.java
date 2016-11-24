package sample.datamodel;

/**
 * Created by andyr on 20/09/2016.
 */
public class Account {

    private String name;
    private String emailAddress;
    private String password;
    private int accountBalance;


    public Account(String name, String emailAddress, String password, int depositAmount) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.password = password;
        this.accountBalance = depositAmount;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }
}
