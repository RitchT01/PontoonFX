package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;

/**
 * Created by andyr on 23/09/2016.
 */
public class BetController {

        @FXML
        private Spinner betAmount;

        public int processResults() {

            return (Integer) betAmount.getValue();
        }
}
