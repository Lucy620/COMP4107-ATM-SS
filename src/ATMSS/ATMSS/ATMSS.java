package ATMSS.ATMSS;

import ATMSS.BAMSHandler.BAMSHandler;
import ATMSS.BAMSHandler.BAMSInvalidReplyException;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.*;

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

    private boolean login = false;
    private boolean CardReaderEmpty = true;
    private String cardNo = "";
    private String accNo="";
    private String textField = "";
    private MBox cashCollectorMBox;

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
                    //status=""
                    cardNo=msg.getDetails();
                    cardReaderInsertPressed(msg);
                    break;

                case TimesUp:
                    Timer.setTimer(id, mbox, pollingTime);
                    log.info("Poll: " + msg.getDetails());
                    cardReaderMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
                    keypadMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
                    touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.Poll, ""));
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
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "00"));
        } else if (msg.getDetails().compareToIgnoreCase(".") == 0) {
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "."));
        } else if (msg.getDetails().startsWith("Enter")) {
            if(!login){
                cardValidation(bams);
            }
        } else if (msg.getDetails().compareToIgnoreCase("Erase") == 0) {
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Clear"));
        } else if (msg.getDetails().compareToIgnoreCase("???") == 0) {

        }

    } // processKeyPressed


    //------------------------------------------------------------
    // processMouseClicked
    private void processMouseClicked(Msg msg) throws IOException, BAMSInvalidReplyException {
        if (msg.getDetails().compareToIgnoreCase("EjectCard") == 0) {
            cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
        } else if (msg.getDetails().compareToIgnoreCase("PrintAdvice") == 0) {
            cardReaderMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, ""));
            advicePrinterMBox.send(new Msg(id, mbox, Msg.Type.CR_EjectCard, "Printing"));
        } else if (msg.getDetails().compareToIgnoreCase("Dispensing") == 0) {
            cashDispenserMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Dispensing"));
        } else if (msg.getDetails().compareToIgnoreCase("TD_AfterDepWit") == 0) {
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "TD_AfterDepWit"));
        } else if(msg.getDetails().compareToIgnoreCase("CollectorOpen") == 0) {
			cashCollectorMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "Open"));
		}else if(msg.getDetails().compareToIgnoreCase("View Balance") == 0) {
            GetAcc(bams);
        }else if(msg.getDetails().startsWith("detail")){
            String str = msg.getDetails();
            for (int k = 6; k < str.length(); k++) {
                accNo += str.charAt(k);
            }
            Enquiry(bams);
        } else if (msg.getDetails().compareToIgnoreCase("BlankScreen") == 0) {
            cardNo="";
            login=false;
            touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "BlankScreen"));

        }else if(msg.getDetails().compareToIgnoreCase("CardRemoved") == 0){
            CardReaderEmpty=true;
        }else if(msg.getDetails().compareToIgnoreCase("CardInserted") == 0){
            CardReaderEmpty=false;
        }

	} // processMouseClicked

    private void cardValidation(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
        System.out.println("Login:");
        try {
            String cred = bams.login(cardNo, textField);
            textField="";
            System.out.println("cred: " + cred);
            if(cred.equals("Success Login")){
                login=true;
                System.out.println("123");
                touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "MainMenu"));
            }
        } catch (Exception e) {
            System.out.println("TestBAMSHandler: " + e.getMessage());
        }
    }

    private void GetAcc(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
        System.out.println("GetAcc:");
        String accounts = bams.getAccounts(cardNo, "cred-1");
        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "balance"+accounts));
        System.out.println("accounts: " + accounts);

    }

    private void Enquiry(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
        System.out.println("Enquiry:");
        double amount = bams.enquiry(cardNo, accNo,"cred-1");
        accNo="";
        System.out.println("amount: " + amount);
        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "amount"+amount));
    } // testEnquiry

    private void cardReaderInsertPressed(Msg msg) {
        touchDisplayMBox.send(new Msg(id, mbox, Msg.Type.TD_UpdateDisplay, "PasswordConfirm"));
    }
} // CardReaderHandler
