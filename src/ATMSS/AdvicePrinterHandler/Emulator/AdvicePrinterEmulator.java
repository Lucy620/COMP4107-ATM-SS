package ATMSS.AdvicePrinterHandler.Emulator;

import ATMSS.ATMSSStarter;
import ATMSS.AdvicePrinterHandler.AdvicePrinterHandler;

import AppKickstarter.misc.Msg;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

//======================================================================
// AdvicePrinterEmulator

public class AdvicePrinterEmulator extends AdvicePrinterHandler {
    private final int WIDTH = 350;
    private final int HEIGHT = 470;
    private ATMSSStarter atmssStarter;
    private String id;
    private Stage myStage;
    private AdvicePrinterEmulatorController advicePrinterEmulatorController;
    private String action ="";

    public AdvicePrinterEmulator(String id, ATMSSStarter atmssStarter) {
        super(id, atmssStarter);
        this.atmssStarter = atmssStarter;
        this.id = id;
    }// AdvicePrinterEmulator


    public void start() throws Exception {
        Parent root;
        myStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        String fxmlName = "AdvicePrinterDefault.fxml";
        loader.setLocation(AdvicePrinterEmulator.class.getResource(fxmlName));
        root = loader.load();
        advicePrinterEmulatorController = (AdvicePrinterEmulatorController) loader.getController();
        advicePrinterEmulatorController.initialize(id, atmssStarter, log, this);
        myStage.initStyle(StageStyle.DECORATED);
        myStage.setScene(new Scene(root, WIDTH, HEIGHT));
        myStage.setTitle("AdvicePrinterHandler");
        myStage.setResizable(false);
        myStage.setOnCloseRequest((WindowEvent event) -> {
            atmssStarter.stopApp();
            Platform.exit();
        });
        myStage.show();
    }

    protected void handleUpdateDisplay(Msg msg) {

        switch (msg.getDetails()) {
            case "Printing":
                reloadStage("AdvicePrinterEmulator.fxml");
                break;

            case "Waiting":
                reloadStage("AdvicePrinterDefault.fxml");
                break;


        }
    }

    private void reloadStage(String fxmlFName) {
        AdvicePrinterEmulator advicePrinterEmulator = this;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info(id + ": loading fxml: " + fxmlFName);

                    Parent root;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(AdvicePrinterEmulator.class.getResource(fxmlFName));
                    root = loader.load();
                    advicePrinterEmulatorController = (AdvicePrinterEmulatorController) loader.getController();
                    advicePrinterEmulatorController.initialize(id, atmssStarter, log, advicePrinterEmulator);
                    myStage.setScene(new Scene(root, WIDTH, HEIGHT));
                } catch (Exception e) {
                    log.severe(id + ": failed to load " + fxmlFName);
                    e.printStackTrace();
                }
            }
        });
    }
}
