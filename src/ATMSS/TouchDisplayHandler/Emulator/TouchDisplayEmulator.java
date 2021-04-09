package ATMSS.TouchDisplayHandler.Emulator;

import ATMSS.ATMSSStarter;
import ATMSS.TouchDisplayHandler.TouchDisplayHandler;
import AppKickstarter.misc.Msg;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;


//======================================================================
// TouchDisplayEmulator
public class TouchDisplayEmulator extends TouchDisplayHandler {
    private final int WIDTH = 680;
    private final int HEIGHT = 520;
    private ATMSSStarter atmssStarter;
    private String id;
    private String text = "";
    private Stage myStage;
    private TouchDisplayEmulatorController touchDisplayEmulatorController;
    public TextField account1;
	public int AccountCount = 0;
	public Label warning;
	public TextField WithdrawalTextField;
	public String[] AccountList = {"","","",""};
    //------------------------------------------------------------
    // TouchDisplayEmulator
    public TouchDisplayEmulator(String id, ATMSSStarter atmssStarter) throws Exception {
	super(id, atmssStarter);
	this.atmssStarter = atmssStarter;
	this.id = id;
    } // TouchDisplayEmulator


    //------------------------------------------------------------
    // start
    public void start() throws Exception {
	Parent root;
	myStage = new Stage();
	FXMLLoader loader = new FXMLLoader();
	String fxmlName = "TouchDisplayEmulator.fxml";
	loader.setLocation(TouchDisplayEmulator.class.getResource(fxmlName));
	root = loader.load();
	touchDisplayEmulatorController = (TouchDisplayEmulatorController) loader.getController();
	touchDisplayEmulatorController.initialize(id, atmssStarter, log, this);
	myStage.initStyle(StageStyle.DECORATED);
	myStage.setScene(new Scene(root, WIDTH, HEIGHT));
	myStage.setTitle("Touch Display");
	myStage.setResizable(false);
	myStage.setOnCloseRequest((WindowEvent event) -> {
	    atmssStarter.stopApp();
	    Platform.exit();
	});
	myStage.show();
    } // TouchDisplayEmulator


    //------------------------------------------------------------
    // handleUpdateDisplay
    protected void handleUpdateDisplay(Msg msg) {
		log.info(id + ": update display -- " + msg.getDetails());

		if (msg.getDetails().startsWith("account")) {
			String str = msg.getDetails();
			String account = "";
			for (int k = 7; k < str.length(); k++) {
				account += str.charAt(k);
			}

			System.out.println(account);

			if (AccountCount == 0) {
				touchDisplayEmulatorController.TransferField1(account);
				AccountCount++;
			} else if (AccountCount == 1) {
				touchDisplayEmulatorController.TransferField2(account);
			}

			System.out.println(touchDisplayEmulatorController.getAccount1());
		}else if(msg.getDetails().startsWith("balance")){
			String str = msg.getDetails();
			String account = "";
			for (int k = 7; k < str.length(); k++) {
				account += str.charAt(k);
			}
			System.out.println(account);
			AccountList = account.split("/");
			for (int i=0; i<AccountList.length;i++){
				System.out.println(AccountList[i]);
			}
			TouchDisplayEmulator touchDisplayEmulator = this;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						log.info(id + ": loading fxml: " + "SelectAccount.fxml");

						Parent root;
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(TouchDisplayEmulator.class.getResource("SelectAccount.fxml"));
						root = loader.load();

						touchDisplayEmulatorController = (TouchDisplayEmulatorController) loader.getController();
						touchDisplayEmulatorController.initialize(id, atmssStarter, log, touchDisplayEmulator);
						myStage.setScene(new Scene(root, WIDTH, HEIGHT));
						touchDisplayEmulatorController.updateAccountLabel(AccountList);
					} catch (Exception e) {
						log.severe(id + ": failed to load " + "SelectAccount.fxml");
						e.printStackTrace();
					}
				}
			});


		}else if(msg.getDetails().startsWith("transfer")){
			String str = msg.getDetails();
			String account = "";
			for (int k = 8; k < str.length(); k++) {
				account += str.charAt(k);
			}
			System.out.println(account);
			AccountList = account.split("/");
			for (int i=0; i<AccountList.length;i++){
				System.out.println(AccountList[i]);
			}
			TouchDisplayEmulator touchDisplayEmulator = this;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						log.info(id + ": loading fxml: " + "TouchDisplayTransfer.fxml");

						Parent root;
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(TouchDisplayEmulator.class.getResource("TouchDisplayTransfer.fxml"));
						root = loader.load();

						touchDisplayEmulatorController = (TouchDisplayEmulatorController) loader.getController();
						touchDisplayEmulatorController.initialize(id, atmssStarter, log, touchDisplayEmulator);
						myStage.setScene(new Scene(root, WIDTH, HEIGHT));
						touchDisplayEmulatorController.updateTransferLabel(AccountList);
					} catch (Exception e) {
						log.severe(id + ": failed to load " + "TouchDisplayTransfer.fxml");
						e.printStackTrace();
					}
				}
			});


		}else if(msg.getDetails().startsWith("TransferAdvice")){
			String str = msg.getDetails();
			String TransferAdvice = "";
			for(int i = 14; i < str.length(); i++){
				TransferAdvice += str.charAt(i);
			}
			touchDisplayEmulatorController.updateTransferAmount(TransferAdvice);
		} else if(msg.getDetails().startsWith("amount")){
			String str = msg.getDetails();
			String amount = "";
			for (int k = 6; k < str.length(); k++) {
				amount += str.charAt(k);
			}
			String finalAmount = amount;
			TouchDisplayEmulator touchDisplayEmulator = this;

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						log.info(id + ": loading fxml: " + "ViewBalance.fxml");

						Parent root;
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(TouchDisplayEmulator.class.getResource("ViewBalance.fxml"));
						root = loader.load();
						touchDisplayEmulatorController = (TouchDisplayEmulatorController) loader.getController();
						touchDisplayEmulatorController.initialize(id, atmssStarter, log, touchDisplayEmulator);
						myStage.setScene(new Scene(root, WIDTH, HEIGHT));
						touchDisplayEmulatorController.updateAmount(finalAmount);
					} catch (Exception e) {
						log.severe(id + ": failed to load " + "ViewBalance.fxml");
						e.printStackTrace();
					}
				}
			});

		}else {

			switch (msg.getDetails()) {
				case "BlankScreen":
					reloadStage("TouchDisplayEmulator.fxml");
					break;

				case "MainMenu":
					AccountCount = 0;
					reloadStage("TouchDisplayMainMenu.fxml");
					break;

				case "DepositConfirmation":
					reloadStage("TouchDisplayDepositViewBalance.fxml");
					break;

				case "Withdrawal":
					reloadStage("TouchDisplayWithdrawal.fxml");
					break;

				case "WaitWithdrawal":
					reloadStage("TouchDisplayWaitWithdrawal.fxml");
					break;

				case "WithdrawalEnterAmount":
					reloadStage("TouchDisplayWithdrawalEnterAmount.fxml");
					break;

				case "Deposit":
					reloadStage("TouchDisplayDeposit.fxml");
					break;

				case "Transfer":
					reloadStage("TouchDisplayTransfer.fxml");
					break;

				case "WaitTransfer":
					AccountCount = 0;
					reloadStage("TouchDisplayWaitTransfer.fxml");
					break;

				case "PasswordConfirm":
					reloadStage("TouchDisplayConfirmPin.fxml");
					//touchDisplayEmulatorController.ClearText();
					break;

				case "Account 1":
					reloadStage("ViewBalance.fxml");
					break;

				case "EjectBalance":
					reloadStage("TouchDisplayEjectBalance.fxml");
					break;

				case "View Balance":
					reloadStage("SelectAccount.fxml");
					break;

				case "Clear":
					touchDisplayEmulatorController.ClearTextField();
					break;

				case "00":
					touchDisplayEmulatorController.AppendTextField00(text + msg.getDetails());
					break;

				case "Enter":
					touchDisplayEmulatorController.EnterNumber();
					break;

				case "EnterDeposit":
					reloadStage("TouchDisplayWaitDeposit.fxml");
					break;

				case "EnterWithdrawal":
					String amount = WithdrawalTextField.getText();
					reloadStage("TouchDisplayWaitWithdrawal.fxml");
					break;

				case "EnterTransfer":
					touchDisplayEmulatorController.EnterTransfer();
					break;

				case "TransferFailed":
					reloadStage("TransferFailed.fxml");
					break;

				case "TransferAmount":
					AccountCount = 0;
					reloadStage("TransferAmount.fxml");
					break;

				case "TransferComplete":
					reloadStage("TransferComplete.fxml");
					break;

				case "TD_AfterDepWit":
					reloadStage("TouchDisplayAfterDepositWithdrawal.fxml");
					break;

				case "Change Password":
					reloadStage("TouchDisplayChangePassword.fxml");
					break;

				case "Change Complete":
					reloadStage("TouchDisplayChangePasswordComplete.fxml");
					break;

				case "Invalid Pin":
					TouchDisplayEmulator touchDisplayEmulator = this;

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								log.info(id + ": loading fxml: " + "TouchDisplayChangePassword.fxml");

								Parent root;
								FXMLLoader loader = new FXMLLoader();
								loader.setLocation(TouchDisplayEmulator.class.getResource("TouchDisplayChangePassword.fxml"));
								root = loader.load();
								touchDisplayEmulatorController = (TouchDisplayEmulatorController) loader.getController();
								touchDisplayEmulatorController.initialize(id, atmssStarter, log, touchDisplayEmulator);
								myStage.setScene(new Scene(root, WIDTH, HEIGHT));
								touchDisplayEmulatorController.InvalidPin();
							} catch (Exception e) {
								log.severe(id + ": failed to load " + "TouchDisplayChangePassword.fxml");
								e.printStackTrace();
							}
						}
					});
					break;

				default:
					touchDisplayEmulatorController.AppendTextField(text + msg.getDetails());
					//touchDisplayEmulatorController.AppendTextField2(text+msg.getDetails());
					log.severe(id + ": update display with unknown display type -- " + msg.getDetails());
					break;
			}
		} // handleUpdateDisplay
	}


    //------------------------------------------------------------
    // reloadStage
    private void reloadStage(String fxmlFName) {
        TouchDisplayEmulator touchDisplayEmulator = this;

        Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		try {
		    log.info(id + ": loading fxml: " + fxmlFName);

		    Parent root;
		    FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(TouchDisplayEmulator.class.getResource(fxmlFName));
		    root = loader.load();
		    touchDisplayEmulatorController = (TouchDisplayEmulatorController) loader.getController();
		    touchDisplayEmulatorController.initialize(id, atmssStarter, log, touchDisplayEmulator);
		    myStage.setScene(new Scene(root, WIDTH, HEIGHT));
		} catch (Exception e) {
		    log.severe(id + ": failed to load " + fxmlFName);
		    e.printStackTrace();
		}
	    }
	});
    } // reloadStage
} // TouchDisplayEmulator
