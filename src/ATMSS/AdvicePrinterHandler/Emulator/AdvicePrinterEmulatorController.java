package ATMSS.AdvicePrinterHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;


//======================================================================
// KeypadEmulatorController
public class AdvicePrinterEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private AdvicePrinterEmulator advicePrinterEmulator;
    private MBox advicePrinterMBox;
    public Label actionLabel;
    public Label amountLabel;

    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, AdvicePrinterEmulator advicePrinterEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.advicePrinterEmulator = advicePrinterEmulator;
        this.advicePrinterMBox = appKickstarter.getThread("AdvicePrinterHandler").getMBox();
    } // initialize

    //------------------------------------------------------------
    // buttonPressed
    public void buttonPressed(ActionEvent actionEvent) {
        advicePrinterMBox.send(new Msg(id, advicePrinterMBox, Msg.Type.CR_EjectCard, "Waiting"));
    } // buttonPressed

    public void updateActionLabel(String action) {
        actionLabel.setText("Action: "+action);
    } // buttonPressed

    public void updateAmountLabel(String amount) {
        amountLabel.setText("Amount: "+amount);
    } // buttonPressed


}
