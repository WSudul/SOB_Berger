import berger.BergerCode;
import berger.BitContainerInterface;
import berger.CheckBits;
import berger.CodeWord;

import java.util.*;

import berger.BergerCode;
import io.ChangeType;

import java.io.File;
import java.io.IOException;

import io.DataInput;
import io.DataReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import report.Record;
import report.Report;

import java.util.*;
import java.util.function.Function;


public class Controller {


    private List<BergerCode> bergerCodes=new ArrayList<>();

    @FXML
    private Button Bit1, LoadFile, preset1,preset2,preset3,preset4;

    @FXML
    private Label Output;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Output.setText("Button Action\n");
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


    private BergerCode PresetMultipleZeros(BergerCode bergerCode) {

        CodeWord codeWord = bergerCode.getCodeWord();
        CheckBits checkBits = bergerCode.getCheckBits();

        SetMultipleOnesToZero(codeWord, 0.50);
        SetMultipleOnesToZero(checkBits, 0.50);

        return bergerCode;
    }


    private BergerCode PresetMultipleOnes(BergerCode bergerCode) {

        CodeWord codeWord = bergerCode.getCodeWord();
        CheckBits checkBits = bergerCode.getCheckBits();

        SetMultipleZerosToOnes(codeWord, 0.50);
        SetMultipleZerosToOnes(checkBits, 0.50);

        return bergerCode;
    }

    private void SetMultipleOnesToZero(BitContainerInterface bitContainer, double fraction) {
        Set<Integer> IndexesOfOnes = GetSpecificIndexes(bitContainer, bit -> (bit));
        Set<Integer> IndexesToBeCleared = SelectIndexes(IndexesOfOnes, (int) (IndexesOfOnes.size() * fraction));
        for (Integer index : IndexesToBeCleared) {
            bitContainer.clearBit(index);
        }
    }

    private void SetMultipleZerosToOnes(BitContainerInterface bitContainer, double fraction) {
        Set<Integer> IndexesOfZeros = GetSpecificIndexes(bitContainer, bit -> (!bit));
        Set<Integer> IndexesToBeSet = SelectIndexes(IndexesOfZeros, (int) (IndexesOfZeros.size() * fraction));
        for (Integer index : IndexesToBeSet) {
            bitContainer.setBit(index);
        }
    }

    private Set<Integer> GetSpecificIndexes(BitContainerInterface bitContainer, Function<Boolean, Boolean> function) {
        Set<Integer> specificIndexes = new TreeSet<>();
        for (int i = 0; i < bitContainer.length(); ++i) {
            if (function.apply(bitContainer.getBit(i)))
                specificIndexes.add(i);
        }
        return specificIndexes;
    }

    private Set<Integer> SelectIndexes(Set<Integer> indexes, int selectSize) {
        if (selectSize > indexes.size())
            return null;
        Random generator = new Random();
        List<Integer> indexesList = new ArrayList<>(indexes);
        Set<Integer> selection = new TreeSet<>();

        while (selectSize > 0) {
            int index = generator.nextInt(indexes.size());
            selection.add(indexesList.get(index));
            --selectSize;
        }

        return selection;
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


    public void handleButtonActionLoadFromFile(ActionEvent actionEvent) {
        List<DataInput> dataInputs = null;
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Wybierz plik");
        File file = chooser.showOpenDialog(new Stage());
        try {
            dataInputs = DataReader.readJsonData(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[] arr = dataInputs.get(3).getData().toCharArray();
        System.out.println(arr);
    }
}