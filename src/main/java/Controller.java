import berger.BergerCode;
import berger.BitContainerInterface;
import io.ChangeType;
import io.DataInput;
import io.DataReader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import report.Record;
import report.Report;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import static presets.Preset.*;


public class Controller implements Initializable {

    private static int flag = 0;
    private List<DataInput> dataInputs = new ArrayList<>(); //original loaded data
    private static BergerCode currentExampleBerger;
    private final String ButtonCodeWordName = "buttonCodeWord";
    private static int indexOfExamples;
    private final String ButtonCheckBitsName = "buttonCheckBits";
    private List<BergerCode> bergerCodes = new ArrayList<>();
    private BergerCode currentExampleOriginal; //backup for restart

    private Random generator = new Random(); //temporary - will be updated with random seed
    @FXML
    private Button reset, preset1, preset2, preset3, preset4, preset5, PreviousExample, NextExample, GenerateRaport;

    public Controller() {
        indexOfExamples = 1;
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> handleBergerCodeError()));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    @FXML
    private Label Output, ExamplesCount;

    @FXML
    private Circle BergerCodeStatus;

    @FXML
    private HBox hBox1, hBox2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Output.setText("Symulator działania kodu Bergera. \nProszę wybrać plik zawierający przykłady.");
    }

    //TODO Generating report to choosen file
    @FXML
    private void handleButtonActionGenerateReport() {
        final int kMin = 0;
        final int kMax = 5;
        Report report = new Report();

        for (DataInput dataInput : dataInputs) {
            BergerCode bergerCode = GenerateBergerCode(dataInput);
            for (int i = kMin; i <= kMax; ++i) {
                BergerCode modifiedInstance = createModifiedInstance(bergerCode, i);
                ChangeType changeType = ChangeType.findByKey(i);
                Record record = new Record(modifiedInstance, changeType);
                report.addRecord(bergerCode, record);
            }
        }
        report.exportToFile("SOB_Report");
    }

    private BergerCode createModifiedInstance(BergerCode bergerCode, int preset) {
        BergerCode modifiedInstance = new BergerCode(bergerCode);

        switch (preset) {
            case 1:
                PresetSingleZero(modifiedInstance);
                break;
            case 2:
                PresetSingleOne(modifiedInstance);
                break;
            case 3:
                PresetMultipleZeros(modifiedInstance);
                break;
            case 4:
                PresetMultipleOnes(modifiedInstance);
            case 5:
                PresetMultipleMixed(modifiedInstance);
                break;
            default:
                break;
        }

        return modifiedInstance;
    }

    @FXML
    public void handleButtonActionLoadFromFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Wybierz plik");
        File file = chooser.showOpenDialog(new Stage());

        try {
            dataInputs = DataReader.readJsonData(file.getCanonicalPath());
            bergerCodes.clear();
            for (DataInput dataInput : dataInputs) {
                BergerCode bergerCode = GenerateBergerCode(dataInput);
                bergerCodes.add(bergerCode);
            }

            currentExampleBerger = bergerCodes.get(0);
            setCurrentAsOriginal();
            refreshExamplesCount();
            setButtonsVisible();

            sethBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setButtonsVisible() {
        if (!PreviousExample.isVisible())
            PreviousExample.setVisible(true);
        if (!NextExample.isVisible())
            NextExample.setVisible(true);
        if (!ExamplesCount.isVisible())
            ExamplesCount.setVisible(true);
        if (!preset1.isVisible())
            preset1.setVisible(true);
        if (!preset2.isVisible())
            preset2.setVisible(true);
        if (!preset3.isVisible())
            preset3.setVisible(true);
        if (!preset4.isVisible())
            preset4.setVisible(true);
        if (!preset5.isVisible())
            preset5.setVisible(true);
        if (!reset.isVisible())
            reset.setVisible(true);
        if (!GenerateRaport.isVisible())
            GenerateRaport.setVisible(true);
    }

    private boolean isBitChanged(int i, String Name) {
        if (Name.equals(ButtonCodeWordName))
            return !currentExampleBerger.getCodeWord().toList().get(i).toString().equals
                    (currentExampleOriginal.getCodeWord().toList().get(i).toString());
        else
            return !currentExampleBerger.getCheckBits().toList().get(i).toString().equals
                    (currentExampleOriginal.getCheckBits().toList().get(i).toString());
    }

    private Button[] BitContainerToButtons(List<Boolean> bitContainer, String Name) {
        int numberOfButtons = bitContainer.size();
        Button[] buttons = new Button[numberOfButtons];

        for (int i = 0; i < numberOfButtons; i++) {
            buttons[i] = new Button();
            buttons[i].setId(Name + i);
            buttons[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
            buttons[i].setText("" + (bitContainer.get(i).toString().equals("false") ? "0" : "1"));
            if (isBitChanged(i, Name))
                buttons[i].setTextFill(Color.RED);
        }
        return buttons;
    }

    private void sethBox() {
        hBox1.getChildren().clear();
        hBox2.getChildren().clear();

        clearPrompt();
        addToPropmt("Aktualny przyklad : " + dataInputs.get(indexOfExamples - 1).getData());

        hBox1.getChildren().addAll(BitContainerToButtons(currentExampleBerger.getCodeWord().toList(), ButtonCodeWordName));
        hBox2.getChildren().addAll(BitContainerToButtons(currentExampleBerger.getCheckBits().toList(), ButtonCheckBitsName));
    }

    @FXML
    private void HandleButtonReset() {
        currentExampleBerger = new BergerCode(currentExampleOriginal);
        sethBox();
    }

    private void handleBergerCodeError() {
        if (currentExampleBerger != null) {
            if (currentExampleBerger.isErrorDetected()) {
                BergerCodeStatus.setFill(Color.RED);
                if (flag != 1) {
                    addToPropmt("Wykryto blad.");
                    flag = 1;
                }
            } else {
                BergerCodeStatus.setFill(Color.GREEN);

                if (!(currentExampleBerger.getCodeWord().toList().equals(currentExampleOriginal.getCodeWord().toList()))
                        || !(currentExampleBerger.getCheckBits().toList().equals(currentExampleOriginal.getCheckBits().toList()))) {
                    BergerCodeStatus.setFill(Color.ORANGE);
                    if (flag > -1) {
                        addToPropmt("Kod Bergera nie wykryl bledu.");
                        flag = -1;
                    }
                } else if (flag != 0) flag = 0;
            }
        }
    }

    @FXML
    public void handleButtonActionPreviousExample() {
        if (indexOfExamples > 1) {
            indexOfExamples--;
            refreshExamplesCount();
            currentExampleBerger = bergerCodes.get(indexOfExamples - 1);
            setCurrentAsOriginal();
            sethBox();
        }
    }

    @FXML
    public void handleButtonActionNextExample() {
        if (indexOfExamples < bergerCodes.size()) {
            indexOfExamples++;
            refreshExamplesCount();
            currentExampleBerger = bergerCodes.get(indexOfExamples - 1);
            setCurrentAsOriginal();
            sethBox();
        }

    }

    private void refreshExamplesCount() {
        ExamplesCount.setText(indexOfExamples + "/" + bergerCodes.size());
    }

    private void setCurrentAsOriginal() {
        currentExampleOriginal = new BergerCode(currentExampleBerger);
    }

    @FXML
    public void HandleButtonPreset1() {
        //changes single value from 1 to 0
        currentExampleBerger = createModifiedInstance(currentExampleBerger, 1);
        addToPropmt("Preset pierwszy, wskrzykniecie bledu z wartosci 1 na 0.");
        sethBox();
    }

    private BergerCode GenerateBergerCode(DataInput dataInput) {
        BergerCode bergerCode;
        switch (dataInput.getType()) {
            case INT:
                bergerCode = new BergerCode(Integer.parseInt(dataInput.getData()));
                break;
            case LONG:
                bergerCode = new BergerCode(Long.parseLong(dataInput.getData()));
                break;
            case STRING:
                bergerCode = new BergerCode(dataInput.getData());
                break;
            case BYTES:
                List<Boolean> booleans = new ArrayList<>();
                dataInput.getData().chars().forEachOrdered(ch -> booleans.add(ch == '1'));
                bergerCode = new BergerCode(booleans);
                break;
            default:
                return null;
        }
        return bergerCode;
    }

    @FXML
    public void HandleButtonPreset2() {
        //changes single value from 1 to 0
        currentExampleBerger = createModifiedInstance(currentExampleBerger, 2);
        addToPropmt("Preset drugi, wskrzykniecie bledu z wartosci 1 na 0.");
        sethBox();
    }

    @FXML
    public void HandleButtonPreset3() {
        //changes multiple values from 0 to 1
        currentExampleBerger = createModifiedInstance(currentExampleBerger, 3);
        addToPropmt("Preset trzeci, wskrzykniecie wielokrotnych bledow z wartosci 0 na 1.");
        sethBox();
    }

    @FXML
    public void HandleButtonPreset4() {
        //changes multiple values from 1 to 0
        currentExampleBerger = createModifiedInstance(currentExampleBerger, 4);
        sethBox();
        addToPropmt("Preset czwarty, wskrzykniecie wielokrotnych bledow z wartosci 1 na 0.");
    }

    @FXML
    public void HandleButtonPreset5() {
        //changes multiple values, bidirectional
        currentExampleBerger = createModifiedInstance(currentExampleBerger, 5);
        sethBox();
        addToPropmt("Preset piaty, wskrzykniecie wielokrotnych bledow dwukierunkowych.");
    }

    private void clearPrompt() {
        Output.setText("");
    }

    private void addToPropmt(String str) {
        Output.setText(Output.getText() + "\n" + str);
    }

    private class MyEventHandler implements EventHandler<Event> {
        @Override
        public void handle(Event evt) {
            Control control = (Control) evt.getSource();
            String objectId = control.getId();
            BitContainerInterface bitContainer;

            //Change of button text
            Button lookup = (Button) control.getScene().lookup("#" + objectId);
            lookup.setText((lookup.getText().equals("1")) ? "0" : "1");

            int indexToFlip;
            if (objectId.startsWith(ButtonCodeWordName)) {
                indexToFlip = Integer.parseInt(control.getId().replace(ButtonCodeWordName, ""));
                bitContainer = currentExampleBerger.getCodeWord();
                if (!isBitChanged(indexToFlip, ButtonCodeWordName)) lookup.setTextFill(Color.RED);
                else lookup.setTextFill(Color.BLACK);
            } else {
                indexToFlip = Integer.parseInt(control.getId().replace(ButtonCheckBitsName, ""));
                bitContainer = currentExampleBerger.getCheckBits();
                if (!isBitChanged(indexToFlip, ButtonCheckBitsName)) lookup.setTextFill(Color.RED);
                else lookup.setTextFill(Color.BLACK);
            }

            //change of bit in Berger code
            if (!bitContainer.flipBit(indexToFlip))
                System.err.println("error while flipping");
        }
    }
}
