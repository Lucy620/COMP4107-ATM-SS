package ATMSS.CashCollector.Emulator;
import ATMSS.CashCollector.CashCollectorHandler;
import ATMSS.ATMSSStarter;
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
        String fxmlName = "CashCollectorEmulator.fxml";
        loader.setLocation(CashCollectorEmulator.class.getResource(fxmlName));
        root = loader.load();
        cashCollectorEmulatorController = (CashCollectorEmulatorController) loader.getController();
        cashCollectorEmulatorController.initialize(id, atmssStarter, log, this);
        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, 350, 470));
        myStage.setTitle("Cash Collector");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            atmssStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    }// CardReaderEmulator

    //------------------------------------------------------------
    // handle Issues
    // handle Issues
}
