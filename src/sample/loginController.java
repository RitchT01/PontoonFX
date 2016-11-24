package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.datamodel.Account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;



/**
 * Created by andyr on 22/09/2016.
 */
public class loginController {

    private static String fileName = "CustomerAccountDetails.txt";

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    public Account processResults(){
        String loginName = name.getText().trim();
        String loginPassword = password.getText().trim();

        if(loginName.isEmpty() || loginPassword.isEmpty()){
            return null;
        }else{
            Account account = getAccountDetails(loginName, loginPassword);
            return account;
        }
    }

    private Account getAccountDetails(String name, String password){
        Scanner scanner;
        Account account;
        try{
            scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
            scanner.useDelimiter(",");
            while(scanner.hasNextLine()){
                //Get data from file line by line
                String input = scanner.nextLine();
                if (input.contains(",")){
                    //Seperate input data by delimiter using split method
                    String[] data = input.split(",");
                    double d = Double.parseDouble(data[3]);
                    Integer i = (int) d;
                    String name2 = data[0];
                    int x = name2.compareTo(name.trim());
                    if(x == 0) {
                        account = new Account(data[0], data[1], data[2], i);
                        return account;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
