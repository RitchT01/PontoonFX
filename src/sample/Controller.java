package sample;

import javafx.application.Platform;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import sample.datamodel.*;

import java.io.*;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Controller {

    private Image cardsFile;
    private Image bustFile;
    private Image pontoonFile;
    private SortedList<ClickOption> sortedList;
    private Deck deck;
    private Hand hand;
    private Hand dealersHand;
    private boolean bust = false;
    private boolean dealerBust = false;
    private boolean stick = false;
    private boolean fiveCards = false;
    private boolean dealersTurn = false;
    private final String scoreString = "SCORE        ";
    private Thread thread;
    private Account loggedInAccount;
    private int currentBetAmount;

    private static String fileName = "CustomerAccountDetails.txt";
    private static String tempFile = "tempFile.txt";

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private BorderPane dealerBorderPane;

    @FXML
    private ListView<ClickOption> optionsListView;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;

    @FXML
    private ImageView imageView5;

    @FXML
    private ImageView imageView6;

    @FXML
    private Label playersScore;

    @FXML
    private Label score;

    @FXML
    private Label login;


    public void initialize() {
        sortedList = new SortedList<ClickOption>(ClickOptionData.getInstance().getClickOptionList());
        optionsListView.setItems(sortedList);
        Deck deck = new Deck("PontoonFX");

        InputStream is = getClass().getResourceAsStream("Pontoon.jpg");
        pontoonFile = new Image(is);
        imageView6.setImage(pontoonFile);

        is = getClass().getResourceAsStream("PlayingCards.jpg");
        cardsFile = new Image(is);

        is = getClass().getResourceAsStream("Bust.jpg");
        bustFile = new Image(is);

        mainBorderPane.setStyle("-fx-border-color:green; -fx-border-style:dotted; -fx-border-width:2;");
        try {
            File file = new File(fileName);
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleMouseClick() {

        optionsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getClickCount() == 2) {
                    ClickOption selectedItem = optionsListView.getSelectionModel().getSelectedItem();
                    if (selectedItem == null) {
                        System.out.println("Error");

                    } else if (selectedItem.toString() == "Deal Cards") {

                        dealCards();

                    } else if (selectedItem.toString() == "Twist") {

                        if (hand != null && bust == false && stick == false && fiveCards == false) {
                            Cards card = createCard(hand);
                            int cardNumber = hand.addCardToHand(card);
                            if (cardNumber == 3) {
                                imageView3.setImage(cardsFile);
                                imageView3.setViewport(createViewport(hand.getCard3()));
                            } else if (cardNumber == 4) {
                                imageView4.setImage(cardsFile);
                                imageView4.setViewport(createViewport(hand.getCard4()));
                            } else if (cardNumber == 5) {
                                imageView5.setImage(cardsFile);
                                imageView5.setViewport(createViewport(hand.getCard5()));
                                fiveCards = true;
                            }

                            int handTotal = hand.getHandTotals();
                            if (handTotal == -1) {

                                bust = true;
                                imageView6.setImage(bustFile);
                                int dealerTotal = 0;
                                int playerTotal = hand.getHandTotals();
                                showNewGamePopUp(playerTotal, dealerTotal);


                            } else if (fiveCards == true) {
                                setupAndRunThread();
                                setupNextTurn();
                            }
                        }

                    } else if (selectedItem.toString() == "Stick" && hand != null && bust == false && stick == false) {
                        stick = true;
                        setupAndRunThread();
                        setupNextTurn();

                    } else if (selectedItem.toString() == "Play again") {
                        resetGame();
                    }
                }
            }
        });
    }

    private void setupAndRunThread(){
        thread = new Thread(){
            @Override
            public void run() {
                try{
                    Thread.sleep(50000);
                }catch(InterruptedException e){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showNextGamePopUp();
                        }
                    });
                    return;
                }
            }
        };
        thread.start();
    }

    private void showNextGamePopUp(){
        int dealerTotal = dealersHand.getHandTotals();
        int playerTotal = hand.getHandTotals();
        showNewGamePopUp(playerTotal, dealerTotal);
    }

    private void setupNextTurn() {
        imageView1.setImage(null);
        imageView2.setImage(null);
        imageView3.setImage(null);
        imageView4.setImage(null);
        imageView5.setImage(null);
        setScore();
        showPreparePopUp();
        runDealersGame();
    }

    private void setScore() {
        String sb = "    ";
        sb = sb + String.valueOf(hand.getHandTotals());
        playersScore.setText(sb);
        score.setText(scoreString);
    }

    private void dealCards() {

        if (hand == null) {
            if(loggedInAccount != null && loggedInAccount.getAccountBalance() > 0){
                showBettingScreen(loggedInAccount);
            }
            hand = createHand();
            imageView1.setImage(cardsFile);
            imageView1.setViewport(createViewport(hand.getCard1()));
            sleepy(500);
            imageView2.setImage(cardsFile);
            imageView2.setViewport(createViewport(hand.getCard2()));
        }
    }

    private void showNewGamePopUp(int playerTotal, int dealerTotal) {
        String outcome = null;
        String contentString = "PLAY AGAIN?";

        String playerScore = String.valueOf(playerTotal);
        String dealerScore = String.valueOf(dealerTotal);

        if (playerTotal == -1) {
            playerScore = "BUST";
        }if (dealerTotal == -1) {
            dealerScore = "BUST";
        }

        if (playerTotal == dealerTotal) {
            outcome = "It's a DRAW\n" +
                      "Your Score: " + playerScore + "\n" +
                      "Dealer's Score: " + dealerScore + "\n\n";
        } else if (playerTotal > dealerTotal || dealerBust == true) {
            outcome = "You WIN\n" +
                      "Your Score: " + playerScore + "\n" +
                      "Dealer's Score: " + dealerScore + "\n\n";
            if(loggedInAccount != null){
                int newBalance = (int) (loggedInAccount.getAccountBalance() + (currentBetAmount));
                loggedInAccount.setAccountBalance(newBalance);
                updateBalanceOnFile(newBalance);
            }

        } else if (playerTotal < dealerTotal || bust == true) {
            outcome = "You LOSE\n" +
                      "Your Score: " + playerScore + "\n" +
                      "Dealer's Score: " + dealerScore + "\n\n";
            if(loggedInAccount != null){
                int newBalance = (int) (loggedInAccount.getAccountBalance() - (currentBetAmount));
                loggedInAccount.setAccountBalance(newBalance);
                updateBalanceOnFile(newBalance);
            }
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("PontoonFX");
        dialog.setContentText(contentString);
        dialog.setHeaderText(outcome);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("playAgain.fxml"));

        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            resetGame();
            dealCards();
        }else{
            resetGame();
        }

    }

    private Image createImage(File file) {
//        System.out.println("file exists: " + file.exists());
        Image image = new Image(file.toURI().toString());
        return image;
    }

    private Rectangle2D createViewport(Cards card) {
        Rectangle2D viewportRect = new Rectangle2D(card.getPosX(), card.getPosY(), 79, 123);
        return viewportRect;
    }

    private Hand createHand() {
        Cards card1 = getRandomCard();
        Cards card2 = getRandomCard();
        while (card1 == card2) {
            card2 = getRandomCard();
        }
        Hand hand = new Hand(card1, card2);
        return hand;
    }

    private Cards createCard(Hand hand) {
        boolean cardUnique = false;
        Cards card = getRandomCard();
        Cards[] handCards = hand.getCards();
        while (cardUnique == false) {
            for (Cards singleCard : handCards) {
                if (singleCard == card) {
                    card = getRandomCard();
                }
            }
            cardUnique = true;
        }
        return card;
    }

    private Cards getRandomCard() {
        int i = ((int) Math.floor(Math.random() * 52) + 1);
        Map<Integer, Cards> deckMap = Deck.getDeckMap();
        Cards card = deckMap.get(i);
        return card;
    }

    private void resetGame() {
        hand = null;
        dealersHand = null;
        imageView1.setImage(null);
        imageView2.setImage(null);
        imageView3.setImage(null);
        imageView4.setImage(null);
        imageView5.setImage(null);
        imageView6.setImage(pontoonFile);
        bust = false;
        dealerBust = false;
        stick = false;
        fiveCards = false;
        dealersTurn = false;
        playersScore.setText(null);
        score.setText(null);
    }

    @FXML
    public void showPreparePopUp() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("PontoonFX");
        dialog.setContentText("Prepare for Dealer's turn");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("prepare.fxml"));

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            dealersTurn = true;
        }
    }



    private void runDealersGame() {

        Task<Void> gameTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                dealersHand = createHand();

                imageView1.setImage(cardsFile);
                imageView1.setViewport(createViewport(dealersHand.getCard1()));
                imageView2.setImage(cardsFile);
                imageView2.setViewport(createViewport(dealersHand.getCard2()));

                Thread.sleep(2000);

                while (dealersHand.getHandTotals() < hand.getHandTotals() && !dealerBust) {
                    Cards card = createCard(dealersHand);
                    int cardNumber = dealersHand.addCardToHand(card);

                    if (cardNumber == 3) {
                        imageView3.setImage(cardsFile);
                        imageView3.setViewport(createViewport(dealersHand.getCard3()));

                    } else if (cardNumber == 4) {
                        imageView4.setImage(cardsFile);
                        imageView4.setViewport(createViewport(dealersHand.getCard4()));

                    } else if (cardNumber == 5) {
                        imageView5.setImage(cardsFile);
                        imageView5.setViewport(createViewport(dealersHand.getCard5()));
                        if (dealersHand.getHandTotals() == -1) {
                            dealerBust = true;
                            imageView6.setImage(bustFile);
                        }
                        break;

                    }
                    if (dealersHand.getHandTotals() == -1) {
                        dealerBust = true;
                        imageView6.setImage(bustFile);
                        break;
                    }
                    Thread.sleep(2000);
                }
                dealersTurn = false;
                Thread.sleep(3000);
                thread.interrupt();

                return null;
            }
        };

            Thread gameThread = new Thread(gameTask);
            gameThread.start();
    }

    private void sleepy(int ms){
        try {
            sleep(ms);
        }catch(InterruptedException e){

        }
    }

    @FXML
    public void handleExit(){
        Platform.exit();
    }

    @FXML
    public void aboutPontoonFX() {};

    @FXML
    public void newAccount(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("PontoonFX");
        dialog.setContentText("Create Account");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("createAccount.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            createAccountController controller = fxmlLoader.getController();
            Account newAccount = controller.processResults();
            if(newAccount != null){
                Account account = getAccountDetails(newAccount.getName(), newAccount.getPassword());
                if(account == null) {
                    writeAccountDetails(newAccount);
                    loggedInAccount = newAccount;
                    login.setText("Logged in as " + loggedInAccount.getName());
                }else{
                    showAlreadyRegisteredPopUp();
                }
            }
        }
    }

    private void showAlreadyRegisteredPopUp(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("PontoonFX");
        dialog.setContentText("Username NOT Available");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("alreadyRegistered.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            newAccount();
        }
    }

    private void writeAccountDetails(Account account){

        try(BufferedWriter saveFile = new BufferedWriter(new FileWriter(fileName, true))){
            saveFile.newLine();
            saveFile.write(
                    account.getName() + "," +
                    account.getEmailAddress() + "," +
                    account.getPassword() + "," +
                    account.getAccountBalance());

            saveFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBalanceOnFile(int balance){
        String oldFileName = fileName;
        String tmpFileName = tempFile;

        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(oldFileName));
            bw = new BufferedWriter(new FileWriter(tmpFileName));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(",")){
                    //Seperate input data by delimiter using split method
                    String[] data = line.split(",");
                    double d = Double.parseDouble(data[3]);
                    Integer i = (int) d;
                    String name2 = data[0];
                    int x = name2.compareTo(loggedInAccount.getName().trim());
                    if(x == 0) {
                        line = line.replace(data[3], String.valueOf(loggedInAccount.getAccountBalance()));
                        bw.write(line+"\n");
                    }
                }
            }
        } catch (Exception e) {
            return;
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException e) {
                //
            }
            try {
                if(bw != null)
                    bw.close();
            } catch (IOException e) {
                //
            }
        }
        // Once everything is complete, delete old file..
        File oldFile = new File(oldFileName);
        oldFile.delete();

        // And rename tmp file's name to old file name
        File newFile = new File(tmpFileName);
        newFile.renameTo(oldFile);

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
            scanner.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    public void login(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("PontoonFX");
        dialog.setContentText("Log in to Account");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            loginController controller = fxmlLoader.getController();
            Account newAccount = controller.processResults();
            if(newAccount != null){
                Account account = getAccountDetails(newAccount.getName(), newAccount.getPassword());
                if(account == null) {
                   // No account found
                }else{
                    loggedInAccount = account;
                    login.setText("Logged in as " + loggedInAccount.getName().trim());
                    showAccountDetails(account);


                }
            }
        }
    }

    private void showAccountDetails(Account account){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("PontoonFX");
        dialog.setHeaderText("Account Name: " + account.getName());
        dialog.setContentText("You have " + ((int) account.getAccountBalance()) + " Credits" + "\n\n" +
                              "Email Address: " + account.getEmailAddress());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Optional<ButtonType> result = dialog.showAndWait();
    }

    @FXML
    public void logout(){
        loggedInAccount = null;
        login.setText(null);
    }

    @FXML
    public void showBalance(){
        if(loggedInAccount != null){
            showAccountDetails(loggedInAccount);
        }
    }

    private void showBettingScreen(Account account){

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("PontoonFX");

        dialog.setHeaderText("You have " + ((int) loggedInAccount.getAccountBalance()) + " Credits" + "\n\n" +
                "Place a bet");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("bettingScreen.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            BetController controller = fxmlLoader.getController();
            currentBetAmount = controller.processResults();
            if(currentBetAmount == 0 || currentBetAmount > loggedInAccount.getAccountBalance()){
                showBettingScreen(loggedInAccount);
            }
        }
    }
}


