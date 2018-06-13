import berger.BergerCode;
import io.ChangeType;
import io.DataInput;
import io.DataReader;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import report.Record;
import report.Report;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static presets.Preset.*;


public class Controller {

    private List<BergerCode> bergerCodes=new ArrayList<>();
    private static BergerCode currentExampleBerger;
    private static int indexOfExamples;

    public Controller(){
        indexOfExamples = 1;
    }
    private Random generator = new Random(); //temporary - will be updated with random seed

    @FXML
    private static ArrayList<Button> buttons;

    @FXML
    private Button LoadFile, preset1,preset2,preset3,preset4, PreviousExample, NextExample, GenerateRaport;

    @FXML
    private Label Output, ExamplesCount;

    @FXML
    private HBox hBox1, hBox2;

    @FXML
    private void handleButtonActionGenerateReport(ActionEvent event){
        final int kMin=1;
        final int kMax=5;
        Report report=new Report();

        for(BergerCode bergerCode: bergerCodes)
        {
            int randomNumber = generator.nextInt(kMax)+kMin;
            System.out.println("randomNumber for type: " + randomNumber);
            BergerCode modifiedInstance=createModifiedInstance(bergerCode,randomNumber);
            ChangeType changeType=ChangeType.findByKey(randomNumber);
            Record record=new Record(modifiedInstance,changeType);
            report.addRecord(bergerCode,record);

        }
        report.exportToFile("SOB_Report");

    }

    private BergerCode createModifiedInstance(BergerCode bergerCode, int preset){
        BergerCode modifiedInstance = new BergerCode(bergerCode);

        //todo select preset
        switch (preset) {
            case 1:
                //preset1
                break;
            case 2:
                //preset 2;
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




    public void handleButtonActionLoadFromFile(ActionEvent actionEvent) throws IOException {
        //Chosing File
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Wybierz plik");
        File file = chooser.showOpenDialog(new Stage());
        List<DataInput> dataInputs;

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

    private class MyEventHandler implements EventHandler<Event>{
        @Override
        public void handle(Event evt) {
            String id = ((Control)evt.getSource()).getId();
          //  Button button = (Button) scene.lookup("#" + id);
            System.out.println(((Control)evt.getSource()).getId());
        }
    }

    public void sethBox(){
        hBox1.getChildren().clear();
        hBox2.getChildren().clear();

        int numberOfButtons = currentExampleBerger.getCodeWord().toList().size();
        Button[] buttons = new Button[numberOfButtons];

        for(int i = 0; i<numberOfButtons;i++){

            buttons[i] = new Button();

            buttons[i].setUserData(i);

            buttons[i].setId("buttonCodeWord" + i);

            buttons[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new MyEventHandler());

            buttons[i].setText("" + (currentExampleBerger.getCodeWord().toList().get(i).toString().equals("false")? "0": "1"));

        }
        hBox1.getChildren().addAll(buttons);

        int numberOfButtons2 = currentExampleBerger.getCheckBits().toList().size();
        Button[] buttons2 = new Button[numberOfButtons2];

        for(int i = 0; i<numberOfButtons2;i++){

            buttons2[i] = new Button();

            buttons2[i].setUserData(i);

            buttons2[i].setId("buttonCheckBits" + i);

            buttons2[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new MyEventHandler());

            buttons2[i].setText("" + (currentExampleBerger.getCheckBits().toList().get(i).toString().equals("false")? "0": "1"));

        }
        hBox2.getChildren().addAll(buttons2);
    }

    public void handleButtonActionPreviousExample(ActionEvent actionEvent) {
        if(indexOfExamples > 1) {
            indexOfExamples--;
            ExamplesCount.setText(indexOfExamples + "/" + bergerCodes.size());
            currentExampleBerger = bergerCodes.get(indexOfExamples-1);
            System.out.println(currentExampleBerger.getCodeWord().toList().toString());
            sethBox();
        }
    }

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
                BigInteger bigInteger = new BigInteger(dataInput.getData());
                bergerCode = new BergerCode(bigInteger.toByteArray());
                break;
            default:
                return null;
        }
        return bergerCode;
    }
}