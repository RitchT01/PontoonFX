package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by andyr on 29/07/2016.
 */
public class ClickOptionData {

    private static ClickOptionData instance = new ClickOptionData();
    private ObservableList<ClickOption> clickOptionList;

    public static ClickOptionData getClickOptionData(){
        return instance;
    }

    public void loadClickOptionData(){

        clickOptionList = FXCollections.observableArrayList();

        ClickOption clickOption = new ClickOption("Deal Cards");
        clickOptionList.add(clickOption);

        clickOption = new ClickOption("Twist");
        clickOptionList.add(clickOption);

        clickOption = new ClickOption("Stick");
        clickOptionList.add(clickOption);

        clickOption = new ClickOption("Play again");
        clickOptionList.add(clickOption);

    }

    public static ClickOptionData getInstance() {
        return instance;
    }

    public ObservableList<ClickOption> getClickOptionList() {
        return clickOptionList;
    }
}
