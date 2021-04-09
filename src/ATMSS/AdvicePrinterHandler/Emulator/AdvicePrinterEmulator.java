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
    private String amount="";

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
                AdvicePrinterEmulator advicePrinterEmulator = this;

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            log.info(id + ": loading fxml: " + "AdvicePrinterEmulator.fxml");

                            Parent root;
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(AdvicePrinterEmulator.class.getResource("AdvicePrinterEmulator.fxml"));
                            root = loader.load();
                            advicePrinterEmulatorController = (AdvicePrinterEmulatorController) loader.getController();
                            advicePrinterEmulatorController.initialize(id, atmssStarter, log, advicePrinterEmulator);
                            myStage.setScene(new Scene(root, WIDTH, HEIGHT));
                            advicePrinterEmulatorController.updateActionLabel(action);
                            advicePrinterEmulatorController.updateAmountLabel(amount);
                            action = "";
                            amount = "";
                        } catch (Exception e) {
                            log.severe(id + ": failed to load " + "AdvicePrinterEmulator.fxml");
                            e.printStackTrace();
                        }
                    }
                });

                break;

            case "Waiting":
                reloadStage("AdvicePrinterDefault.fxml");
                break;

            case "CardRetain":
                AdvicePrinterEmulator advicePrinterEmulator2 = this;

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            log.info(id + ": loading fxml: " + "AdvicePrinterEmulator.fxml");

                            Parent root;
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(AdvicePrinterEmulator.class.getResource("AdvicePrinterEmulator.fxml"));
                            root = loader.load();
                            advicePrinterEmulatorController = (AdvicePrinterEmulatorController) loader.getController();
                            advicePrinterEmulatorController.initialize(id, atmssStarter, log, advicePrinterEmulator2);
                            myStage.setScene(new Scene(root, WIDTH, HEIGHT));
                            advicePrinterEmulatorController.updateCardRetain();
                            action = "";
                        } catch (Exception e) {
                            log.severe(id + ": failed to load " + "AdvicePrinterEmulator.fxml");
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }

    protected void cardRetain(Msg msg){
        handleUpdateLabel(msg);
        handleUpdateDisplay(new Msg(id,mbox,Msg.Type.TD_UpdateDisplay,"Printing"));
        advicePrinterEmulatorController.updateCardRetain();
    }

    protected void handleUpdateLabel(Msg msg) {
        if(msg.getDetails().startsWith("action")){
            String str = msg.getDetails();
            for (int k = 6; k < str.length(); k++) {
                action += str.charAt(k);
            }
            //advicePrinterEmulatorController.updateActionLabel(action);
        }else if(msg.getDetails().startsWith("amount")){
            String str = msg.getDetails();

            for (int k = 6; k < str.length(); k++) {
                amount += str.charAt(k);
            }

            //advicePrinterEmulatorController.updateAmountLabel(amount);
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
