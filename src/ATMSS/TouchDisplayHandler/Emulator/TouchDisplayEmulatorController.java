package ATMSS.TouchDisplayHandler.Emulator;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.MBox;
import AppKickstarter.misc.Msg;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    public TextField WithdrawalTextField;
    public TextField textField;


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

    public void EnterNumber(String text){
        switch (WithdrawalTextField.getStyle()){
            case "Pin":
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "EnterPin"));
                break;

            case "EnterDeposit":
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "EnterDeposit"));
                break;

            case "EnterWithdrawal":
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay,"EnterWithdrawal"));
        }
        //System.out.println(WithdrawalTextField.getText());
    }

    //------------------------------------------------------------
    // td_mouseClick
    public void td_mouseClick(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        if (x >= 340 && y >= 270 && y <= 340) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Withdrawal"));
        }else if(x <= 300 && y >= 270 && y <= 340){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Deposit"));
        }else if(x >= 340 && y>=340 && y<=410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "View Balance"));
        }
    }// td_mouseClick

    public void td_mouseClick2(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        Label selectedLabel = ((Label) mouseEvent.getSource());
        String selectedText = selectedLabel.getText();
        //System.out.println(selectedText);
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, selectedText));
    }

    public void Withdrawal(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Withdrawal"));
    }

    public void WaitWithdrawal(MouseEvent mouseEvent){
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
        System.out.println("123");
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "Dispensing"));
    }

    public void CashCollectorOpen(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        if(x <= 300 && y >= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "CollectorOpen"));
        }
    }

    public void CashCollectorOpenLabel(MouseEvent mouseEvent){
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "CollectorOpen"));
    }

    public void DepositConfirmation(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        if(x >= 340 && y>=340 && y<=410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "DepositConfirmation"));
        }
    }

    public void DepositConfirmationLabel(MouseEvent mouseEvent){
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "DepositConfirmation"));
    }

    public void BackToMenu(MouseEvent mouseEvent){
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
    public void PickAction(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        if (x <= 300 && y >= 270 && y <= 340) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "EjectCard"));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "PrintAdvice"));
        }else if(x >= 340 && y >= 270 && y <= 340){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "EjectCard"));
        }else if(x <= 300 && y >= 340 && y <= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
        }
    }// SelectAccount_rectangle


    public void EjectCard(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "EjectCard"));
    }

    public void EjectCardandAdvice(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "EjectCard"));
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, "PrintAdvice"));
    }
    public void select_mouseClick(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        if (x <= 300 && y >= 270 && y <= 340) {
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Account 1"));
        }else if(x >= 340 && y >= 270 && y <= 340){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Account 1"));
        }else if(x <= 300 && y >= 340 && y <= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Account 1"));
        }else if(x >= 340 && y >= 340 && y <= 410){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Account 1"));
        }
    }// SelectAccount_rectangle

    public void ViewBalance(MouseEvent mouseEvent){
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        log.fine(id + ": mouse clicked: -- (" + x + ", " + y + ")");


        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_MouseClicked, x + " " + y));

        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Account 1"));

    }



} // TouchDisplayEmulatorController
