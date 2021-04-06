package ATMSS.CashCollector.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.logging.Logger;


//======================================================================
// CardReaderEmulatorController
public class CashCollectorEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private CashCollectorEmulator cashCollectorEmulator;
    private MBox cashDepositCollectorMBox;



    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, CashCollectorEmulator cashCollectorEmulator){
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.cashCollectorEmulator = cashCollectorEmulator;
        this.cashDepositCollectorMBox = appKickstarter.getThread("CashCollectorHandler").getMBox();
    }// initialize

    public void buttonPressed(ActionEvent actionEvent) {
        cashDepositCollectorMBox.send(new Msg(id, cashDepositCollectorMBox, Msg.Type.TD_UpdateDisplay, "Waiting"));
    } // buttonPressed
    //------------------------------------------------------------
    // functions
}
