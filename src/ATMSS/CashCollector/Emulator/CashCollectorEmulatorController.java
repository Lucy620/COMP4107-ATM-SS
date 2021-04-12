package ATMSS.CashCollector.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.logging.Logger;


//======================================================================
// CardReaderEmulatorController
public class CashCollectorEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private CashCollectorEmulator cashCollectorEmulator;
    private MBox cashDepositCollectorMBox;

    public Button Banknote100;
    public Button Banknote500;
    public Button Banknote1000;

    public Label banknote100num;
    public Label banknote500num;
    public Label banknote1000num;

    public int num100 = 0;
    public int num500 = 0;
    public int num1000 = 0;

    public int total100 = 0;
    public int total500 = 0;
    public int total1000 = 0;

    public int totalBanknote;

    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, CashCollectorEmulator cashCollectorEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.cashCollectorEmulator = cashCollectorEmulator;
        this.cashDepositCollectorMBox = appKickstarter.getThread("CashCollectorHandler").getMBox();
    }// initialize

    public void buttonPressed(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        String btntext = btn.getText();

        System.out.println("====num100 " + num100 + "====num500 " + num500 + "====num1000 " + num1000);

        totalBanknote = num100 + num500 + num1000;

        total100 = 100 * num100;
        total500 = 500 * num500;
        total1000 = 1000 * num1000;

        cashDepositCollectorMBox.send(new Msg(id, cashDepositCollectorMBox, Msg.Type.TD_MouseClicked, "ShowCashDetail" + String.valueOf(num100) + "/" + String.valueOf(num500) + "/" + String.valueOf(num1000)));

        cashDepositCollectorMBox.send(new Msg(id, cashDepositCollectorMBox, Msg.Type.TD_UpdateDisplay, "Waiting"));
    } // buttonPressed

    //------------------------------------------------------------
    // functions

    public void depAmount(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        String btntext = btn.getText();

        if (btntext.compareToIgnoreCase("100") == 0) {
            num100 += 1;
            banknote100num.setText(String.valueOf(num100));
        } else if (btntext.compareToIgnoreCase("500") == 0) {
            num500 += 1;
            banknote500num.setText(String.valueOf(num500));
        } else if (btntext.compareToIgnoreCase("1000") == 0) {
            num1000 += 1;
            banknote1000num.setText(String.valueOf(num1000));
        }

        System.out.println("=====num100 " + num100);
        System.out.println("=====num500 " + num500);
        System.out.println("=====num1000 " + num1000);
    }


}
