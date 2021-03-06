package ATMSS.CashCollector.Emulator;
import ATMSS.AdvicePrinterHandler.Emulator.AdvicePrinterEmulator;
import ATMSS.AdvicePrinterHandler.Emulator.AdvicePrinterEmulatorController;
import ATMSS.CashCollector.CashCollectorHandler;
import ATMSS.ATMSSStarter;
import AppKickstarter.misc.Msg;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


//======================================================================
// CashCollectorEmulator
public class CashCollectorEmulator extends CashCollectorHandler {
    private ATMSSStarter atmssStarter;
    private String id;
    private Stage myStage;
    private CashCollectorEmulatorController cashCollectorEmulatorController;

    //------------------------------------------------------------
    // CashCollectorEmulator
    public CashCollectorEmulator(String id, ATMSSStarter atmssStarter) {
        super(id, atmssStarter);
        this.atmssStarter=atmssStarter;
        this.id=id;
    }// CashCollectorEmulator

    //------------------------------------------------------------
    // start
    public void start() throws Exception {
        Parent root;
        myStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        String fxmlName = "CashCollectorEmulatorSlotClose.fxml";
        loader.setLocation(CashCollectorEmulator.class.getResource(fxmlName));
        root = loader.load();
        cashCollectorEmulatorController = (CashCollectorEmulatorController) loader.getController();
        cashCollectorEmulatorController.initialize(id, atmssStarter, log, this);
        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, 470, 370));
        myStage.setTitle("Cash Collector");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            atmssStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    }// CardReaderEmulator


    //------------------------------------------------------------
    // handleUpdateDisplay
    protected void handleUpdateDisplay(Msg msg) {
        switch (msg.getDetails()) {
            case "Waiting":
                reloadStage("CashCollectorEmulatorSlotClose.fxml");
                break;

            case "Open":
                reloadStage("CashCollectorEmulator.fxml");
                break;

            case "CashInsert":
                reloadStage("CashCollectorEmulator.fxml");
                break;
        }
    } // handleUpdateDisplay

    private void reloadStage(String fxmlFName) {
        CashCollectorEmulator cashCollectorEmulator = this;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info(id + ": loading fxml: " + fxmlFName);

                    Parent root;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(CashCollectorEmulator.class.getResource(fxmlFName));
                    root = loader.load();
                    cashCollectorEmulatorController = (CashCollectorEmulatorController) loader.getController();
                    cashCollectorEmulatorController.initialize(id, atmssStarter, log, cashCollectorEmulator);
                    myStage.setScene(new Scene(root, 470, 500));
                } catch (Exception e) {
                    log.severe(id + ": failed to load " + fxmlFName);
                    e.printStackTrace();
                }
            }
        });
    }

    //------------------------------------------------------------
    // handle Issues
    // handle Issues
}
