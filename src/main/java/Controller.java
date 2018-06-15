import berger.BergerCode;
import berger.BitContainerInterface;
import io.ChangeType;
import io.DataInput;
import io.DataReader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import presets.Preset;
import report.Record;
import report.Report;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static presets.Preset.*;


public class Controller {

    private List<BergerCode> bergerCodes=new ArrayList<>();
    private List<DataInput> dataInputs = new ArrayList<>(); //original loaded data
    private static BergerCode currentExampleBerger;
    BergerCode currentExampleOriginal; //backup for restart
    private static int indexOfExamples;

    public Controller(){
        indexOfExamples = 1;
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }
    private Random generator = new Random(); //temporary - will be updated with random seed

    @FXML
    private static Button[] buttons, buttons2;

    @FXML
    private Button LoadFile, reset, preset1,preset2,preset3,preset4, preset5, PreviousExample, NextExample, GenerateRaport;

    @FXML
    private Label Output, ExamplesCount;

    @FXML
    private Circle BergerCodeStatus;

    @FXML
    private HBox hBox1, hBox2;

    @FXML
    private void handleButtonActionGenerateReport(ActionEvent event){
        final int kMin=1;
        final int kMax=5;
        Report report=new Report();

        for (DataInput dataInput : dataInputs)
        {
            BergerCode bergerCode = GenerateBergerCode(dataInput);
            int randomNumber = generator.nextInt(kMax)+kMin;
            System.out.println("randomNumber for preset: " + randomNumber);
            BergerCode modifiedInstance=createModifiedInstance(bergerCode,randomNumber);
            ChangeType changeType=ChangeType.findByKey(randomNumber);
            Record record=new Record(modifiedInstance,changeType);
            report.addRecord(bergerCode,record);

        }
        report.exportToFile("SOB_Report");

    }

    private BergerCode createModifiedInstance(BergerCode bergerCode, int preset){
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
    public void handleButtonActionLoadFromFile(ActionEvent actionEvent) throws IOException {
        //Chosing File
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Wybierz plik");
        File file = chooser.showOpenDialog(new Stage());

        try {
            dataInputs = DataReader.readJsonData(file.getCanonicalPath());

            //Initializing List of BergerCodes
            bergerCodes.clear();
            for (DataInput dataInput : dataInputs) {
                System.out.println("dataInput: " + dataInput.getType() + " " + dataInput.getData());
                BergerCode bergerCode = GenerateBergerCode(dataInput);
                bergerCodes.add(bergerCode);
            }
            //Initialize displayed example
            currentExampleBerger = bergerCodes.get(0);
            currentExampleOriginal = new BergerCode(currentExampleBerger);


            //Exposing switch-example Menu
            ExamplesCount.setText(indexOfExamples + "/" + bergerCodes.size());
            if(!PreviousExample.isVisible())
                PreviousExample.setVisible(true);
            if(!NextExample.isVisible())
                NextExample.setVisible(true);
            if(!ExamplesCount.isVisible())
                ExamplesCount.setVisible(true);
            System.out.println("" + currentExampleBerger.getCodeWord().toList().toString());
            //Displaying current example
            sethBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    final String kKuttonCodeWordName = "buttonCodeWord";
    final String kKuttonCheckBitsName = "buttonCheckBits";

    public void sethBox(){
        hBox1.getChildren().clear();
        hBox2.getChildren().clear();

        int numberOfButtons = currentExampleBerger.getCodeWord().length();
        buttons = new Button[numberOfButtons];

        for(int i = 0; i<numberOfButtons;i++){

            buttons[i] = new Button();

            buttons[i].setUserData(i);

            buttons[i].setId("buttonCodeWord" + i);

            buttons[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new MyEventHandler());

            buttons[i].setText("" + (currentExampleBerger.getCodeWord().toList().get(i).toString().equals("false")? "0": "1"));

        }
        hBox1.getChildren().addAll(buttons);

        int numberOfButtons2 = currentExampleBerger.getCheckBits().toList().size();
        buttons2 = new Button[numberOfButtons2];

        for(int i = 0; i<numberOfButtons2;i++){

            buttons2[i] = new Button();

            buttons2[i].setUserData(i);

            buttons2[i].setId("buttonCheckBits" + i);

            buttons2[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new MyEventHandler());

            buttons2[i].setText("" + (currentExampleBerger.getCheckBits().toList().get(i).toString().equals("false")? "0": "1"));

        }
        hBox2.getChildren().addAll(buttons2);
    }


    private class MyEventHandler implements EventHandler<Event> {
        @Override
        public void handle(Event evt) {
            Control control = (Control) evt.getSource();
            String objectId = control.getId();

            BitContainerInterface bitContainer;

            //  Button button = (Button) scene.lookup("#" + id);
            System.out.println(control.getId());

            int indexToFlip;
            if (objectId.startsWith(kKuttonCodeWordName)) {
                indexToFlip = Integer.parseInt(control.getId().replace(kKuttonCodeWordName, ""));
                bitContainer = currentExampleBerger.getCodeWord();
            } else {
                indexToFlip = Integer.parseInt(control.getId().replace(kKuttonCheckBitsName, ""));
                bitContainer = currentExampleBerger.getCheckBits();
            }

            //Change of button text
            Button lookup = (Button) control.getScene().lookup("#" + objectId);
            lookup.setText((lookup.getText().equals("1")) ? "0" : "1");

            //change of bit in Berger code
            if (!bitContainer.flipBit(indexToFlip))
                System.out.println("error while flipping");

            System.out.println(bitContainer.toList());
        }
    }

    @FXML
    private void HandleButtonReset(ActionEvent actionEvent){
        currentExampleBerger = new BergerCode(currentExampleOriginal);
        sethBox();
    }

    Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            handleBergerCodeError();
        }
    }));

    private void handleBergerCodeError() {
        if(currentExampleBerger != null) {
            if (currentExampleBerger.isErrorDetected())
                BergerCodeStatus.setFill(Color.RED);
            else
                BergerCodeStatus.setFill(Color.GREEN);
        }
    }

    @FXML
    public void handleButtonActionPreviousExample(ActionEvent actionEvent) {
        if(indexOfExamples > 1) {
            indexOfExamples--;
            ExamplesCount.setText(indexOfExamples + "/" + bergerCodes.size());
            currentExampleBerger = bergerCodes.get(indexOfExamples-1);
            System.out.println(currentExampleBerger.getCodeWord().toList().toString());
            sethBox();
        }
    }

    @FXML
    public void handleButtonActionNextExample(ActionEvent actionEvent) {
        if(indexOfExamples < bergerCodes.size()) {
            indexOfExamples++;
            ExamplesCount.setText(indexOfExamples + "/" + bergerCodes.size());
            currentExampleBerger = bergerCodes.get(indexOfExamples-1);
            System.out.println(currentExampleBerger.getCodeWord().toList().toString());
            sethBox();
        }

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

    private Preset preset = new Preset();

    @FXML
    public void HandleButtonPreset1(ActionEvent actionEvent){
        //changes single value from 1 to 0
        currentExampleBerger = createModifiedInstance(currentExampleBerger,1);
        sethBox();
    }

    @FXML
    public void HandleButtonPreset2(ActionEvent actionEvent){
        //changes single value from 1 to 0
        currentExampleBerger = createModifiedInstance(currentExampleBerger,2);
        sethBox();
    }

    @FXML
    public void HandleButtonPreset3(ActionEvent actionEvent){
        //changes multiple values from 0 to 1
        currentExampleBerger = createModifiedInstance(currentExampleBerger,3);
        sethBox();
    }

    @FXML
    public void HandleButtonPreset4(ActionEvent actionEvent){
        //changes multiple values from 1 to 0
        currentExampleBerger = createModifiedInstance(currentExampleBerger,4);
        sethBox();
    }

    @FXML
    public void HandleButtonPreset5(ActionEvent actionEvent){
        //changes multiple values, bidirectional
        currentExampleBerger = createModifiedInstance(currentExampleBerger,5);
        sethBox();
    }
}
