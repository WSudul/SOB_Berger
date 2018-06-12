import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Button Bit1;

    @FXML
    private Label Output;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Output.setText("Button Action\n");
    }

    }