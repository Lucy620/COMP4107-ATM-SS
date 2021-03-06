package ATMSS.TouchDisplayHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import com.sun.javaws.IconUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;


//======================================================================
// TouchDisplayEmulatorController
public class TouchDisplayEmulatorController {
    private String id;
    private AppKickstarter appKickstarter;
    private Logger log;
    private TouchDisplayEmulator touchDisplayEmulator;
    private MBox touchDisplayMBox;
    int KeyPressedCount = 0;
    String enternumber;
    public String[] AccountList = {"","","",""};
    public TextField WithdrawalTextField;
    public TextField account1;
    public TextField account2;
    public Label a;
    public Label b;
    public Label c;
    public Label d;
    public Label warning;
    public Label Account1; // view
    public Label Account2;
    public Label Account3;
    public Label Account4;
    public Label amountLabel;
    public Label errorPin; //change pin

    public String action;
    public String amount;
    public Label num100;
    public Label num500;
    public Label num1000;
    public Label amount100;
    public Label amount500;
    public Label amount1000;
    public Label totalAmount;
    public Label Account;
    public Label depositAmount;
    public Label depositAccount;
    public Label ejectBalance;
    public int total100;
    public int total500;
    public int total1000;
    public int cash100 = 0;
    private int cash500 = 0;
    private int cash1000 = 0;
    public int total;


    //------------------------------------------------------------
    // initialize
    public void initialize(String id, AppKickstarter appKickstarter, Logger log, TouchDisplayEmulator touchDisplayEmulator) {
        this.id = id;
        this.appKickstarter = appKickstarter;
        this.log = log;
        this.touchDisplayEmulator = touchDisplayEmulator;
        this.touchDisplayMBox = appKickstarter.getThread("TouchDisplayHandler").getMBox();
    } // initialize

    public void AppendTextField(String status){
        WithdrawalTextField.appendText(status);
        KeyPressedCount++;
    }

    public void AppendTextField00(String status){
        WithdrawalTextField.appendText(status);
        KeyPressedCount += 2;
    }

    public void ClearTextField(){
        WithdrawalTextField.deleteText(0,KeyPressedCount);
        KeyPressedCount = 0;
    }

    public void TransferField1(String text){
        account1.appendText(text);
    }

    public String getAccount1(){
        return account1.getText();
    }

    public void TransferField2(String text){
        if(getAccount2().equals("")) {
            account2.appendText(text);
        }else if(account2.getText().equals(account1.getText())) {
            System.out.println("[][][]" + getAccount1());
        }else{
            account2.deleteText(0,getAccount2().length());
            account2.appendText(text);
        }
    }

    public String getAccount2(){
        return account2.getText();
    }

    public void EnterNumber(){
        switch (WithdrawalTextField.getStyle()){
            case "Pin":
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Change Password"));
                break;

            case "EnterDeposit":
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "EnterDeposit"));
                break;

            case "EnterWithdrawal":
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay,"EnterWithdrawal"));
                break;

            case "EnterTransfer":
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay,"EnterTransfer"));

            default:
                log.info(id + "KeyPressed: Enter");
        }
    }

    //------------------------------------------------------------
    // td_mouseClick
    public void td_mouseClick(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        if (x >= 340 && y >= 270 && y <= 340) {
            action="Withdrawal";
            touchDisplayMBox.send(new Msg(id,touchDisplayMBox, Msg.Type.TD_MouseClicked,"action"+action));
            action="";
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Withdrawal"));
        }else if(x <= 300 && y >= 270 && y <= 340){
            action="Deposit";
            touchDisplayMBox.send(new Msg(id,touchDisplayMBox, Msg.Type.TD_MouseClicked,"action"+action));
            action = "";
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Deposit"));
        }else if(x <= 300 && y >= 340 && y <= 410){
            action="Transfer";
            touchDisplayMBox.send(new Msg(id,touchDisplayMBox, Msg.Type.TD_MouseClicked,"action"+action));
            action="";
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Transfer"));
        }else if(x >= 340 && y >= 340 && y <= 410){
            action="View Balance";
            touchDisplayMBox.send(new Msg(id,touchDisplayMBox, Msg.Type.TD_MouseClicked,"action"+action));
            action="";
            touchDisplayMBox.send(new Msg(id,touchDisplayMBox, Msg.Type.TD_MouseClicked,"View Balance"));
        }else if(x >= 340 && y>=410 && y<=480){
            touchDisplayMBox.send(new Msg(id,touchDisplayMBox, Msg.Type.TD_MouseClicked,"Eject Card"));
        }else if(x <= 300 && y >= 410){
            touchDisplayMBox.send(new Msg(id,touchDisplayMBox, Msg.Type.TD_UpdateDisplay,"Change Password"));
        }
    }// td_mouseClick

    public void td_mouseClick2(MouseEvent mouseEvent){

        Label selectedLabel = ((Label) mouseEvent.getSource());
        String selectedText = selectedLabel.getText();
        action = selectedText;
        touchDisplayMBox.send(new Msg(id,touchDisplayMBox, Msg.Type.TD_MouseClicked,"action"+action));
        action="";
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, selectedText));
       // touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, selectedText));

    }

    public void TransferClick(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        if (x <= 300 && y >= 270 && y <= 340) {
            if(SameTransferAccount(a)){
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "account"+a.getText()));
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "account"+a.getText()));
            }
        }else if(x >= 340 && y >= 270 && y <= 340){
            if(SameTransferAccount(b)){
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "account"+b.getText()));
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "account"+b.getText()));
            }
        }else if(x <= 300 && y >= 340 && y <= 410){
            if(SameTransferAccount(c)){
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "account"+c.getText()));
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "account"+c.getText()));
            }
        }else if(x >= 340 && y >= 340 && y <= 410){
            if(SameTransferAccount(d)){
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "account"+d.getText()));
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "account"+d.getText()));
            }
        }else if(x <= 300 && y >= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
        }else{
            if(TransferWarning()){
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "TransferAmount"));
            }else{

            }
        }
    }

    public boolean SameTransferAccount(Label l){
        if(account1.getText().equals(l.getText())){
            return false;
        }
        return true;
    }

    public boolean TransferWarning(){
        if(account1.getText().equals("") || account2.getText().equals("")){
            warning.setText("Please Choose Two Accounts!");
            return false;
        }
        return true;
    }

    public void TransferClickLabel(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        Label selectedLabel = ((Label) mouseEvent.getSource());
        String selectedText = selectedLabel.getText();

        if(selectedText.equals("Submit")){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "TransferAmount"));
        }else if(selectedText.equals("Back")){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
        }else{
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "account"+selectedText));
        }
    }

    public void updateTransferLabel(String[] AccountList){
        try{
            a.setText(AccountList[0]);
            b.setText(AccountList[1]);
            c.setText(AccountList[2]);
            d.setText(AccountList[3]);
        }catch (Exception e){

        }

    }

    public void finishTransfer(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        if(x <= 300 && y >= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
        }else if(x >= 340 && y >= 410){
            EjectCard(mouseEvent);
        }else if(x <= 300 && y >= 340 && y <= 410){
            TransferAdviceOnly(mouseEvent);
        }else if( x >= 340 && y >= 340 && y <= 410){
            TransferEjectCardandAdvice(mouseEvent);
        }
    }

    public void finishChangePin(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        if(x <= 300 && y >= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
        }
    }

    public void AdviceOnly(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "PrintAdviceOnly"));
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
    }

    public void WDAdviceOnly(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "PrintAdviceOnly"));
    }

    public void TransferAdviceOnly(MouseEvent mouseEvent){
        AdviceOnly(mouseEvent);
        //touchDisplayMBox.send(new Msg(id,touchDisplayMBox, Msg.Type.TD_MouseClicked,"amount"+amount));

        //amount="";
        //touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay,"TransferAdvice"));
    }

    public void TransferEjectCardandAdvice(MouseEvent mouseEvent){
        //touchDisplayMBox.send(new Msg(id,touchDisplayMBox, Msg.Type.TD_MouseClicked,"amount"+amount));
        EjectCardandAdvice(mouseEvent);
        amount="";
    }

    public void InvalidPin(){
        System.out.println(errorPin.getText());
        errorPin.setText("Invalid New Password!");
    }

    public void EnterTransfer(){
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "EnterTransfer"));
    }

    public void EnterWithdrawal(){
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "EnterWithdrawal"));
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "WaitWithdrawal"));
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Dispensing"));
    }

    public void Withdrawal(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        Label selectedLabel = ((Label) mouseEvent.getSource());
        String selectedText = selectedLabel.getText();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "DTWithdrawal"+selectedText));

    }

    /*public void WaitWithdrawal(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        if(x <= 300 && y >= 410 ){
            BackToMenu(mouseEvent);
        }else if(x >= 340 && y >= 410){
            EnterAmount(mouseEvent);
        }else{
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "WaitWithdrawal"));
        }

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Dispensing"));
    }*/

    public void CashCollectorOpen(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        if (x <= 300 && y >= 410) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "CollectorOpen"));
        } else if (x >= 340 && y >= 410) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "totalAmount" + String.valueOf(total)));
        }
    }

    public void CashCollectorOpenLabel(MouseEvent mouseEvent) {
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "CollectorOpen"));
    }

    public void DepositConfirmationLabel(MouseEvent mouseEvent) {
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "totalAmount" + String.valueOf(total)));
    }

    public void DepositViewBalance(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        if (x <= 300 && y >= 410) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "EjectBalance"));
        } else if (x >= 340 && y >= 410) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Eject Card"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "PrintAdvice"));
        }

    }

    public void selectDeposit_mouseClick(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        if (x <= 300 && y >= 270 && y <= 340) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "DepositAccount" + Account1.getText()));
        } else if (x >= 340 && y >= 270 && y <= 340) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "DepositAccount" + Account2.getText()));
        } else if (x <= 300 && y >= 340 && y <= 410) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "DepositAccount" + Account3.getText()));
        } else if (x >= 340 && y >= 340 && y <= 410) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "DepositAccount" + Account4.getText()));
        }
    }

    public void selectDeposit_mouseClickLabel(MouseEvent mouseEvent) {
        Label selectedLabel = ((Label) mouseEvent.getSource());
        String selectedText = selectedLabel.getText();
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "DepositAccount" + selectedText));
    }

    public void BackToMenu(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
    }

    public void EnterAmount(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "WithdrawalEnterAmount"));
    }

    public void WithdrawalClickAmount(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        Label selectedLabel = ((Label) mouseEvent.getSource());

        String str = selectedLabel.getText();
        if (str.compareToIgnoreCase("back") == 0) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "MainMenu"));
        } else {
            String selectedText = "";
            for (int k = 2; k < str.length(); k++) {
                selectedText += str.charAt(k);
            }
            System.out.println(selectedText);

            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "WDAmount" + selectedText));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "WaitWithdrawal"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Dispensing"));

            amount = "";
        }
    }

    public void WDSelection(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        if (x <= 500 && y >= 175 && y <= 235) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Eject Card"));
        } else if (x <= 500 && y >= 235 && y <= 305) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "EjectBalanceWD"));
        } else if (x <= 500 && y >= 305 && y <= 375) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Eject Card"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "PrintAdvice"));
        } else if (x <= 500 && y >= 375 && y <= 450) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "EjectBalance"));
        }
    }

    public void WithdrawalRecAmount(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        if (x <= 300 && y >= 270 && y <= 340) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "WDAmount400"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "WaitWithdrawal"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Dispensing"));
        } else if (x >= 340 && y >= 270 && y <= 340) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "WDAmount800"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "WaitWithdrawal"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Dispensing"));
        } else if (x <= 300 && y >= 340 && y <= 410) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "WDAmount1000"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "WaitWithdrawal"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Dispensing"));
        } else if (x >= 340 && y >= 340 && y <= 410) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "WDAmount1200"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "WaitWithdrawal"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Dispensing"));
        } else if (x <= 300 && y >= 410) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "MainMenu"));
        } else if (x >= 340 && y >= 410) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "WithdrawalEnterAmount"));
        }
    }

    public void PickAction(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        if (x <= 300 && y >= 270 && y <= 340) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Eject Card"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "PrintAdvice"));
        }else if(x >= 340 && y >= 270 && y <= 340){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Eject Card"));
        }else if(x <= 300 && y >= 340 && y <= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
        }
    }// SelectAccount_rectangle

    public void EjectCard(MouseEvent mouseEvent){
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Eject Card"));
    }

    public void EjectCardandBalance(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "EjectBalance"));

    }

    public void EjectCardandAdvice(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Eject Card"));
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "PrintAdvice"));
    }


    public void EjectCardandBalanceWD(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "EjectBalanceWD"));

    }

    public void EjectCardandBalanceandAdvice(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        WDAdviceOnly(mouseEvent);
        EjectCardandBalanceWD(mouseEvent);
    }

    public void select_mouseClick(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();


        if (x <= 300 && y >= 270 && y <= 340) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "detail"+AccountList[0]));
        }else if(x >= 340 && y >= 270 && y <= 340){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "detail"+AccountList[1]));
        }else if(x <= 300 && y >= 340 && y <= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "detail"+AccountList[2]));
        }else if(x >= 340 && y >= 340 && y <= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "detail"+AccountList[3]));
        }else if(x <= 300 && y >= 410 ){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
        }
    }// SelectAccount_rectangle

    public void select_mouseClick_withdrawal(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();


        if (x <= 300 && y >= 270 && y <= 340) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "DTWithdrawal"+AccountList[0]));
        }else if(x >= 340 && y >= 270 && y <= 340){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "DTWithdrawal"+AccountList[1]));
        }else if(x <= 300 && y >= 340 && y <= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "DTWithdrawal"+AccountList[2]));
        }else if(x >= 340 && y >= 340 && y <= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "DTWithdrawal"+AccountList[3]));
        }else if(x <= 300 && y >= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
        }
    }// SelectAccount_rectangle

    public void ViewBalance(MouseEvent mouseEvent){

        Label selectedLabel = ((Label) mouseEvent.getSource());
        String selectedText = selectedLabel.getText();

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "detail"+selectedText));

    }

    public void updateAccountLabel(String[] Accountlist_back) {
        try{
            AccountList=Accountlist_back;
            Account1.setText(AccountList[0]);
            Account2.setText(AccountList[1]);
            Account3.setText(AccountList[2]);
            Account4.setText(AccountList[3]);
        }catch (Exception e){

        }


    }
    public void updateAmount(String amount_back) {
        amountLabel.setText("Account Balance: "+amount_back);
        amount=amount_back;
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "amount" + amount));
        amount = "";
    }

    public void updateTransferAmount(String amount_back) {
        amount = amount_back;
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "amount" + amount));
        amount = "";
    }

    public void updateDepositAccount(String[] AccountList) {
        try{
            Account1.setText(AccountList[0]);
            Account2.setText(AccountList[1]);
            Account3.setText(AccountList[2]);
            Account4.setText(AccountList[3]);
        }catch (Exception e){

        }

    }


    public void handleUpdateDisplay_ShowCashDetail(String str) {
        String cashNum = "";
        for (int k = 14; k < str.length(); k++) {
            cashNum += str.charAt(k);
        }
        String[] CashDetail = {"", "", ""};
        CashDetail = cashNum.split("/");

        cash100 += Integer.parseInt(CashDetail[0]);
        cash500 += Integer.parseInt(CashDetail[1]);
        cash1000 += Integer.parseInt(CashDetail[2]);

        total100 = cash100 * 100;
        total500 = cash500 * 500;
        total1000 = cash1000 * 1000;
        total = total100 + total500 + total1000;

        num100.setText(String.valueOf(cash100));
        num500.setText(String.valueOf(cash500));
        num1000.setText(String.valueOf(cash1000));

        amount100.setText(String.valueOf(total100));
        amount500.setText(String.valueOf(total500));
        amount1000.setText(String.valueOf(total1000));
        totalAmount.setText(String.valueOf(total));

    }

    public void UpdateDepositViewBalance(String accno, String amount_back) {
        depositAccount.setText(accno);
        depositAmount.setText(amount_back);
        amount = amount_back;
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "amount" + amount));
        amount = "";
    }

    public void DepositEjectBalance(String balance) {
        ejectBalance.setText(balance);
    }

    public void WDEjectBalance(String balance) {
        ejectBalance.setText(balance);
    }


} // TouchDisplayEmulatorController
