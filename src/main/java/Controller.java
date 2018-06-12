import berger.CheckBits;
import berger.CodeWord;

import java.net.URL;
import java.util.*;

import berger.BergerCode;
import io.ChangeType;

import java.io.File;
import java.io.IOException;

import io.DataInput;
import io.DataReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import report.Record;
import report.Report;
public class Controller implements Initializable{


    private List<BergerCode> bergerCodes=new ArrayList<>();
    private static char[] currentExample;
    private static List<DataInput> dataInputs;
    private static int indexOfExamples;
    public Controller(){
        dataInputs = null;
        indexOfExamples = 0;
        currentExample = null;
    }

    @FXML
    private ArrayList<Button> buttons;
    @FXML
    private Button LoadFile, preset1,preset2,preset3,preset4, PreviousExample, NextExample, GenerateRaport;

    @FXML
    private Label Output, ExamplesCount;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Output.setText("Button Action\n");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PreviousExample.setVisible(false);
        NextExample.setVisible(false);
        ExamplesCount.setVisible(false);
    }

    @FXML
    private void handleButtonActionGenerateReport(ActionEvent event){
        final int kMin=1;
        final int kMax=5;
        Report report=new Report();

        Random generator = new Random();
        for(BergerCode bergerCode: bergerCodes)
        {
            int randomNumber = generator.nextInt(kMax)+kMin;

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
                //preset 3;
                break;
            case 4:
                //preset 4;
            case 5:
                PresetMultipleMixed(modifiedInstance);
                break;
            default:
                break;
        }

        return modifiedInstance;
    }



    private BergerCode PresetMultipleMixed(BergerCode bergerCode) {
        CodeWord codeWord = bergerCode.getCodeWord();
        CheckBits checkBits = bergerCode.getCheckBits();

        final int totalLength = codeWord.length() + checkBits.length();
        final int numberOfBitsToFlip = totalLength / 2;

        Set<Integer> uniqueIndexes = GenerateUniqueIndexes(numberOfBitsToFlip,totalLength);

        final int codeWordMaxIndex = codeWord.length() - 1;
        final int offset=codeWord.length();
        for (Integer index : uniqueIndexes) {
            if (index > codeWordMaxIndex) {
                checkBits.flipBit(index - offset);
            } else
                codeWord.flipBit(index);

        }
        return bergerCode;

    }

    private Set<Integer> GenerateUniqueIndexes(int n, int maxIndex){
        Random generator = new Random();
        Set<Integer> uniqueIndexes =new TreeSet<>();
        while (uniqueIndexes.size() < n) {
            int index = generator.nextInt(maxIndex);
            uniqueIndexes.add(index);
        }
        return uniqueIndexes;
    }


    public void handleButtonActionLoadFromFile(ActionEvent actionEvent) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Wybierz plik");
        File file = chooser.showOpenDialog(new Stage());
        try {
            dataInputs = DataReader.readJsonData(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExamplesCount.setText(indexOfExamples + "/" + (dataInputs.size()-1));
        PreviousExample.setVisible(true);
        NextExample.setVisible(true);
        ExamplesCount.setVisible(true);
        currentExample = dataInputs.get(indexOfExamples).getData().toCharArray();
        System.out.println(currentExample);
    }

    public void handleButtonActionPreviousExample(ActionEvent actionEvent) {
        if(indexOfExamples > 0) {
        indexOfExamples--;
        ExamplesCount.setText(indexOfExamples + "/" + (dataInputs.size()-1));
        currentExample = dataInputs.get(indexOfExamples).getData().toCharArray();
        System.out.println(currentExample);
        }
    }

    public void handleButtonActionNextExample(ActionEvent actionEvent) {
        if(indexOfExamples < (dataInputs.size()-1)) {
            indexOfExamples++;
            ExamplesCount.setText(indexOfExamples + "/" + (dataInputs.size()-1));
            currentExample = dataInputs.get(indexOfExamples).getData().toCharArray();
            System.out.println(currentExample);
        }
    }
}