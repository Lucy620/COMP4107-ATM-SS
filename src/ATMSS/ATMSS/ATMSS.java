package ATMSS.ATMSS;

import ATMSS.BAMSHandler.BAMSHandler;
import ATMSS.BAMSHandler.BAMSInvalidReplyException;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.*;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.scene.control.TextField;

import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;
import AppKickstarter.timer.Timer;


//======================================================================
// ATMSS
public class ATMSS extends AppThread {

    private int pollingTime;
    private MBox cardReaderMBox;
    private MBox keypadMBox;
    private MBox touchDisplayMBox;
    private MBox advicePrinterMBox;
    private MBox cashDispenserMBox;
    private BAMSHandler bams;
    public TextField WithdrawalTextField;

    private boolean login = false;
    private boolean CardReaderEmpty = true;
    private String cardNo = "";
    private String accNo = "";
    private String textField = "";
    private String fromAcc = ""; //transfer
    private String toAcc = "";
    private String amount = "";
    private int transferCount = 0;

    private String depositAmount = "";

    private MBox cashCollectorMBox;
    private int pinCount = 0;

    private String oldPin = "";
    private String newPin = "";
    //------------------------------------------------------------
    // ATMSS
    public ATMSS(String id, AppKickstarter appKickstarter) throws Exception {
        super(id, appKickstarter);
        pollingTime = Integer.parseInt(appKickstarter.getProperty("ATMSS.PollingTime"));
        String urlPrefix = "http://cslinux0.comp.hkbu.edu.hk/comp4107_20-21_grp06/";
        bams = new BAMSHandler(urlPrefix);
    } // ATMSS


    //------------------------------------------------------------
    // run
    public void run() {

        Timer.setTimer(id, mbox, pollingTime);
        log.info(id + ": starting...");

        cardReaderMBox = appKickstarter.getThread("CardReaderHandler").getMBox();
        keypadMBox = appKickstarter.getThread("KeypadHandler").getMBox();
        touchDisplayMBox = appKickstarter.getThread("TouchDisplayHandler").getMBox();
        advicePrinterMBox = appKickstarter.getThread("AdvicePrinterHandler").getMBox();
        cashDispenserMBox = appKickstarter.getThread("CashDispenserHandler").getMBox();
		cashCollectorMBox = appKickstarter.getThread("CashCollectorHandler").getMBox();

        for (boolean quit = false; !quit; ) {
            Msg msg = mbox.receive();

            log.fine(id + ": message received: [" + msg + "].");

            switch (msg.getType()) {

                case TD_MouseClicked:
                    log.info("MouseCLicked: " + msg.getDetails());
                    try {
                        processMouseClicked(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (BAMSInvalidReplyException e) {
                        e.printStackTrace();
                    }
                    break;

                case KP_KeyPressed:
                    log.info("KeyPressed: " + msg.getDetails());
                    try {
                        processKeyPressed(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (BAMSInvalidReplyException e) {
                        e.printStackTrace();
                    }

                    break;

                case CR_CardInserted:
                    log.info("MouseCLicked: " + msg.getDetails());
                    cardNo=msg.getDetails();
                    cardReaderInsertPressed(msg);
                    break;

                case CR_CardRemoved:
                    log.info("Card: " + msg.getDetails() + "removed");
                    try{
                        CardRemove(bams,msg);
                    }catch (IOException e){
                        e.printStackTrace();
                    }catch (BAMSInvalidReplyException e){
                        e.printStackTrace();
                    }
                    break;


                case TimesUp:
                    Timer.setTimer(id, mbox, pollingTime);
                    log.info("Poll: " + msg.getDetails());
                    CardRetain();
                    //cardReaderMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
                    //keypadMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
                    //touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
                    //cashCollectorMBox.send(new Msg(id, mbox, Msg.Type.Poll,""));
                    break;

                case PollAck:
                    log.info("PollAck: " + msg.getDetails());
                    break;

                case Terminate:
                    quit = true;
                    break;

                default:
                    log.warning(id + ": unknown message type: [" + msg + "]");
            }
        }

        // declaring our departure
        appKickstarter.unregThread(this);
        log.info(id + ": terminating...");
    } // run


    //------------------------------------------------------------
    // processKeyPressed
    private void processKeyPressed(Msg msg) throws IOException, BAMSInvalidReplyException {

        // *** The following is an example only!! ***
        if (msg.getDetails().compareToIgnoreCase("Cancel") == 0) {
            cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
        } else if (msg.getDetails().compareToIgnoreCase("1") == 0) {
            //if status.equals("")
            textField += msg.getDetails();
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "1"));
        } else if (msg.getDetails().compareToIgnoreCase("2") == 0) {
            textField += msg.getDetails();
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "2"));
        } else if (msg.getDetails().compareToIgnoreCase("3") == 0) {
            textField += msg.getDetails();
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "3"));
        } else if (msg.getDetails().compareToIgnoreCase("4") == 0) {
            textField += msg.getDetails();
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "4"));
        } else if (msg.getDetails().compareToIgnoreCase("5") == 0) {
            textField += msg.getDetails();
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "5"));
        } else if (msg.getDetails().compareToIgnoreCase("6") == 0) {
            textField += msg.getDetails();
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "6"));
        } else if (msg.getDetails().compareToIgnoreCase("7") == 0) {
            textField += msg.getDetails();
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "7"));
        } else if (msg.getDetails().compareToIgnoreCase("8") == 0) {
            textField += msg.getDetails();
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "8"));
        } else if (msg.getDetails().compareToIgnoreCase("9") == 0) {
            textField += msg.getDetails();
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "9"));
        } else if (msg.getDetails().compareToIgnoreCase("0") == 0) {
            textField += msg.getDetails();
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "0"));
        } else if (msg.getDetails().compareToIgnoreCase("00") == 0) {
            textField += msg.getDetails();
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "00"));
        } else if (msg.getDetails().compareToIgnoreCase(".") == 0) {
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "."));
        } else if (msg.getDetails().startsWith("Enter")) {
            if(!login){
                try{
                    cardValidation(bams);
                    clearTextFiled();
                }catch (IOException e){
                    e.printStackTrace();
                }catch (BAMSInvalidReplyException e) {
                    e.printStackTrace();
                }
            }else{
                touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, msg.getDetails()));
            }

        } else if (msg.getDetails().compareToIgnoreCase("Erase") == 0) {
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Clear"));
            clearTextFiled();
        } else if (msg.getDetails().compareToIgnoreCase("???") == 0) {

        }

    } // processKeyPressed


    //------------------------------------------------------------
    // processMouseClicked
    private void processMouseClicked(Msg msg) throws IOException, BAMSInvalidReplyException {
        if (msg.getDetails().compareToIgnoreCase("Eject Card") == 0) {
            cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
        } else if (msg.getDetails().compareToIgnoreCase("PrintAdvice") == 0) {
            cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
            advicePrinterMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, "Printing"));
        }  else if (msg.getDetails().compareToIgnoreCase("PrintAdviceOnly") == 0) {
            advicePrinterMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, "Printing"));
        } else if (msg.getDetails().compareToIgnoreCase("Dispensing") == 0) {
            cashDispenserMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Dispensing"));
        } else if (msg.getDetails().compareToIgnoreCase("TD_AfterDepWit") == 0) {
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "TD_AfterDepWit"));
        } else if(msg.getDetails().compareToIgnoreCase("CollectorOpen") == 0) {
			cashCollectorMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Open"));
		}else if(msg.getDetails().compareToIgnoreCase("View Balance") == 0) {
            GetAcc(bams);
        }else if (msg.getDetails().compareToIgnoreCase("EnterTransfer") == 0) {
            TransferAccount(bams);
            clearTextFiled();
        } else if (msg.getDetails().startsWith("account")) {
            getTransferAcc(msg);
        } else if (msg.getDetails().startsWith("detail")) {
            String str = msg.getDetails();
            for (int k = 6; k < str.length(); k++) {
                accNo += str.charAt(k);
            }
            Enquiry(bams);
        } else if (msg.getDetails().compareToIgnoreCase("BlankScreen") == 0) {
            cardNo = "";
            login = false;
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "BlankScreen"));
        }else if(msg.getDetails().compareToIgnoreCase("Change Password") == 0){
            ChangePassword(bams);
        }else if (msg.getDetails().compareToIgnoreCase("Deposit") == 0) {
            GetDepositAcc(bams);

        } else if (msg.getDetails().startsWith("DepositAccount")) {
            String str = msg.getDetails();
            for (int k = 14; k < str.length(); k++) {
                accNo += str.charAt(k);
            }
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "DisplayCashDetail"));
        } else if (msg.getDetails().startsWith("DisplayCashDetail")) {
            log.info("DisplayCashDetail: " + msg.getDetails());
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, msg.getDetails()));
        } else if (msg.getDetails().startsWith("totalAmount")) {
            String str = msg.getDetails();
            for (int k = 11; k < str.length(); k++) {
                depositAmount += str.charAt(k);
            }
            Deposit(bams);
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "DepositConfirmation" + accNo + "/" + depositAmount));
        }else if(msg.getDetails().compareToIgnoreCase("Transfer") == 0) {
            GetTransferAcc(bams);
        }else if(msg.getDetails().compareToIgnoreCase("Change Password") == 0){
            ChangePassword(bams);
        } else if (msg.getDetails().startsWith("action")) {
            advicePrinterMBox.send(new Msg(id, mbox, Msg.Type.TD_MouseClicked, msg.getDetails()));
        }else if (msg.getDetails().startsWith("amount")) {
            System.out.println("????" + msg.getDetails());
            advicePrinterMBox.send(new Msg(id, mbox, Msg.Type.TD_MouseClicked, msg.getDetails()));
        } else if (msg.getDetails().compareToIgnoreCase("EjectBalance") == 0) {
            DepositBalanceEnquiry(bams);
        }

	} // processMouseClicked

    private void clearTextFiled(){
        textField = "";
    }

    private void cardValidation(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {

        if(pinCount ==  2){
            CardRetain();
            pinCount = 0;
        }else{
            try {
                System.out.println("Login:");
                String cred = bams.login(cardNo, textField);
                System.out.println("cred: " + cred);
                if(cred.equals("Success Login")){
                    login=true;
                    oldPin = textField;
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
                }else{
                    pinCount++;
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "PasswordConfirm"));
                    clearTextFiled();
                }
                textField="";
            } catch (Exception e) {
                System.out.println("TestBAMSHandler: " + e.getMessage());
            }
        }

    }

    private void ChangePassword(BAMSHandler bams) throws BAMSInvalidReplyException, IOException{
        System.out.println("ChangePin:");
        newPin = textField;
        if(textField.length() != 6){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Invalid Pin"));
            newPin = "";
        }else{
            String accounts = bams.chgPinReq(cardNo,oldPin,newPin,"cred-1");
            if(accounts.equals("succ")){
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Change Complete"));
                oldPin = newPin;
                newPin = "";
            }else{
                touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "Invalid Pin"));
                newPin = "";
            }
            textField = "";
            System.out.println("accounts: " + accounts);
        }

    }

    private void TransferAccount(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
        System.out.println("Transfer:");
        System.out.println("---------------"+textField);
        System.out.println("---------------"+fromAcc);
        System.out.println("---------------"+toAcc);
        double transAmount = bams.transfer(cardNo, "cred-1",fromAcc, toAcc, textField);
        System.out.println("transAmount: " + transAmount);
        if(transAmount == -1.0){
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay,"TransferFailed"));
            fromAcc="";
            toAcc="";
        }else{
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay,"TransferAdvice" + textField));
            touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay,"TransferComplete"));
            fromAcc="";
            toAcc="";
        }

    }

    private void GetAcc(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
        System.out.println("GetAcc:");
        String accounts = bams.getAccounts(cardNo, "cred-1");
        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "balance" + accounts));
        System.out.println("accounts: " + accounts);

    }

    private void GetDepositAcc(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
        System.out.println("GetAcc:");
        String accounts = bams.getAccounts(cardNo, "cred-1");
        System.out.println("------------------" + accounts);
        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "deposit" + accounts));
        System.out.println("accounts: " + accounts);
    }

    public void GetTransferAcc(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
        System.out.println("GetAcc:");
        String accounts = bams.getAccounts(cardNo, "cred-1");
        System.out.println("accounts: " + accounts);
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox, Msg.Type.TD_UpdateDisplay, "transfer" + accounts));
        System.out.println();
    }

    private void getTransferAcc(Msg msg) {
        String str = msg.getDetails();
        String fromto = "";
        for (int k = 7; k < str.length(); k++) {
            fromto += str.charAt(k);
        }

        if (fromAcc.equals("")) {
            fromAcc = fromto;
            transferCount++;
        } else {
            toAcc = fromto;
        }
        //System.out.println(fromAcc + "------" + toAcc);
    }

    private void Enquiry(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
        System.out.println("Enquiry:");
        double amount = bams.enquiry(cardNo, accNo, "cred-1");
        accNo = "";
        System.out.println("amount: " + amount);
        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "amount" + amount));
    } // testEnquiry

    private void cardReaderInsertPressed(Msg msg) {
        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "PasswordConfirm"));
    }

    public void CardRetain(){
        touchDisplayMBox.send(new Msg(id, touchDisplayMBox,Msg.Type.TD_UpdateDisplay,"BlankScreen"));
        //advicePrinterMBox.send(new Msg(id, advicePrinterMBox,Msg.Type.TD_MouseClicked,"actionRetain card"));
        advicePrinterMBox.send(new Msg(id, advicePrinterMBox, Msg.Type.CR_EjectCard, "CardRetain"));
        cardReaderMBox.send(new Msg(id, cardReaderMBox, Msg.Type.TimesUp, "Retain"));
        cashCollectorMBox.send(new Msg(id, cardReaderMBox, Msg.Type.TD_UpdateDisplay, "Waiting"));
        cashDispenserMBox.send(new Msg(id, cashDispenserMBox, Msg.Type.TD_UpdateDisplay, "Waiting"));
    }

    public void CardRemove(BAMSHandler bams, Msg msg) throws IOException, BAMSInvalidReplyException {
        login = false;
        String cred = bams.logout(msg.getDetails(),"cred-1");
        System.out.println("cred: " + cred);

    }

    private void Deposit(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
        System.out.println("Deposit:");
        double depAmount = bams.deposit(cardNo, accNo, "cred-1", depositAmount);
        System.out.println("depAmount: " + depAmount);
        System.out.println();
    }

    private void DepositBalanceEnquiry(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
        System.out.println("Enquiry:");
        double amount = bams.enquiry(cardNo, accNo, "cred-1");
        accNo = "";
        System.out.println("amount: " + amount);
        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "DepositAmount" + amount));
    }
} // CardReaderHandler
