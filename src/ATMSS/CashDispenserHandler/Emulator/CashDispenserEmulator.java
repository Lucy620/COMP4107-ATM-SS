package ATMSS.CashDispenserHandler.Emulator;
import ATMSS.CashDispenserHandler.CashDispenserHandler;
import ATMSS.ATMSSStarter;
import ATMSS.TouchDisplayHandler.Emulator.TouchDisplayEmulator;
import ATMSS.TouchDisplayHandler.Emulator.TouchDisplayEmulatorController;
import AppKickstarter.misc.Msg;
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
        myStage.setScene(new Scene(root, 500, 170));
        myStage.setTitle("Card Dispenser");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            atmssStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    } // CardDispenserEmulator

    //------------------------------------------------------------
    // handleUpdateDisplay
    protected void handleUpdateDisplay(Msg msg) {
        log.info(id + ": update display -- " + msg.getDetails());

        switch (msg.getDetails()) {
            case "Waiting":
                reloadStage("CashDispenserEmulatorWaiting.fxml");
                break;

            case "Dispensing":
                reloadStage("CashDispenserEmulator.fxml");
                break;

            default:
                log.severe(id + ": update display with unknown display type -- " + msg.getDetails());
                break;
        }
    } // handleUpdateDisplay

    //------------------------------------------------------------
    // reloadStage
    private void reloadStage(String fxmlFName) {
        CashDispenserEmulator cashDispenserEmulator = this;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info(id + ": loading fxml: " + fxmlFName);

                    Parent root;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(CashDispenserEmulator.class.getResource(fxmlFName));
                    root = loader.load();
                    cashDispenserEmulatorController = (CashDispenserEmulatorController) loader.getController();
                    cashDispenserEmulatorController.initialize(id, atmssStarter, log, cashDispenserEmulator);
                    myStage.setScene(new Scene(root, 500, 170));
                } catch (Exception e) {
                    log.severe(id + ": failed to load " + fxmlFName);
                    e.printStackTrace();
                }
            }
        });
    }
} // CardDispenserEmulator