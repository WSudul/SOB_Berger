import berger.BergerCode;
import io.ChangeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import report.Record;
import report.Report;


public class Controller {


    private List<BergerCode> bergerCodes=new ArrayList<>();

    @FXML
    private Button Bit1;

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
                //preset 3;
                break;
            case 4:
                //preset 4;
            case 5:
                //preset 5?;
                break;
            default:
                break;
        }

        return modifiedInstance;
    }

}