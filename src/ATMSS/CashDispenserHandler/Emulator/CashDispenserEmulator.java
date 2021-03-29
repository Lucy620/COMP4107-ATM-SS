package ATMSS.CashDispenserHandler.Emulator;

import ATMSS.ATMSSStarter;
import ATMSS.CashDispenserHandler.CashDispenserHandler;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


//======================================================================
// CardDispenserEmulator
public class CashDispenserEmulator extends CashDispenserHandler {
    private ATMSSStarter atmssStarter;
    private String id;
    private Stage myStage;
    private CashDispenserEmulatorController cashDispenserEmulatorController;

    //------------------------------------------------------------
    // CardDispenserEmulator
    public CashDispenserEmulator(String id, ATMSSStarter atmssStarter) {
        super(id, atmssStarter);
        this.atmssStarter = atmssStarter;
        this.id = id;
    } // CardDispenserEmulator


    //------------------------------------------------------------
    // start
    public void start() throws Exception {
        Parent root;
        myStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        String fxmlName = "CashDispenserEmulator.fxml";
        loader.setLocation(CashDispenserEmulator.class.getResource(fxmlName));
        root = loader.load();
        cashDispenserEmulatorController = (CashDispenserEmulatorController) loader.getController();
        cashDispenserEmulatorController.initialize(id, atmssStarter, log, this);
        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, 350, 470));
        myStage.setTitle("Card Reader");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            atmssStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    } // CardDispenserEmulator


    //------------------------------------------------------------
    // handleCashDispense
    protected void handleCashDispense() {
        // fixme
        super.handleCashDispense();
        cashDispenserEmulatorController.appendTextArea("Cash Dispensed");
        cashDispenserEmulatorController.updateCardStatus("Cash Dispensed");
    } // handleCardInsert


    //------------------------------------------------------------
    // handleCashRemove
    protected void handleCashRemove() {
        // fixme
        super.handleCashRemove();
        cashDispenserEmulatorController.appendTextArea("Card Ejected");
        cashDispenserEmulatorController.updateCardStatus("Card Ejected");
    } // handleCashRemove
} // CardDispenserEmulator