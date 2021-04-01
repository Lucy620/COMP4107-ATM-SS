package ATMSS;
import ATMSS.BAMSHandler.BAMSHandler;
import ATMSS.BAMSHandler.BAMSInvalidReplyException;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.*;

import ATMSS.AdvicePrinterHandler.AdvicePrinterHandler;
import ATMSS.CashDispenserHandler.CashDispenserHandler;
import ATMSS.KeypadHandler.Emulator.KeypadEmulator;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.Msg;
import AppKickstarter.timer.Timer;

import ATMSS.ATMSS.ATMSS;
import ATMSS.CardReaderHandler.CardReaderHandler;
import ATMSS.KeypadHandler.KeypadHandler;
import ATMSS.TouchDisplayHandler.TouchDisplayHandler;
import ATMSS.AdvicePrinterHandler.AdvicePrinterHandler;
import ATMSS.CashCollector.CashCollectorHandler;

import com.sun.nio.sctp.PeerAddressChangeNotification;
import javafx.application.Platform;


//======================================================================
// ATMSSStarter
public class ATMSSStarter extends AppKickstarter {
    protected Timer timer;
    protected ATMSS atmss;
    protected CardReaderHandler cardReaderHandler;
    protected KeypadHandler keypadHandler;
    protected TouchDisplayHandler touchDisplayHandler;
    protected AdvicePrinterHandler advicePrinterHandler;
    protected CashCollectorHandler cashCollectorHandler;
	protected CashDispenserHandler cashDispenserHandler;


	//------------------------------------------------------------
    // main
    public static void main(String [] args) {
        new ATMSSStarter().startApp();
		String urlPrefix = "http://cslinux0.comp.hkbu.edu.hk/comp4107_20-21_grp06/";
		BAMSHandler bams = new BAMSHandler(urlPrefix,initLogger());

		try{
			testLogin(bams);
			testGetAcc(bams);
			//testWithdraw(bams);
			//testDeposit(bams);
			testEnquiry(bams);
			testTransfer(bams);
		}catch (Exception e){
			System.out.println("TestBAMSHandler: Exception caught: " + e.getMessage());
			e.printStackTrace();
		}
		return;
    } // main
	static Logger initLogger() {
		// init our logger
		ConsoleHandler logConHdr = new ConsoleHandler();
		logConHdr.setFormatter(new LogFormatter());
		Logger log = Logger.getLogger("TestBAMSHandler");
		log.setUseParentHandlers(false);
		log.setLevel(Level.ALL);
		log.addHandler(logConHdr);
		logConHdr.setLevel(Level.ALL);
		return log;
	} // initLogger


	static class LogFormatter extends Formatter {
		//------------------------------------------------------------
		// format
		public String format(LogRecord rec) {
			Calendar cal = Calendar.getInstance();
			String str = "";

			// get date
			cal.setTimeInMillis(rec.getMillis());
			str += String.format("%02d%02d%02d-%02d:%02d:%02d ",
					cal.get(Calendar.YEAR) - 2000,
					cal.get(Calendar.MONTH) + 1,
					cal.get(Calendar.DAY_OF_MONTH),
					cal.get(Calendar.HOUR_OF_DAY),
					cal.get(Calendar.MINUTE),
					cal.get(Calendar.SECOND));

			// level of the log
			str += "[" + rec.getLevel() + "] -- ";

			// message of the log
			str += rec.getMessage();
			return str + "\n";
		} // format
	} // LogFormatter

	static void testLogin(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
		System.out.println("Login:");
		try{
		    String cred = bams.login("123-456-789", "123456");
		    System.out.println("cred: " + cred);
		}catch(Exception e){
			System.out.println("TestBAMSHandler: " + e.getMessage());
		}

		System.out.println();
	} // testLogin

	static void testGetAcc(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
		System.out.println("GetAcc:");
		String accounts = bams.getAccounts("123-456-789", "cred-1");
		System.out.println("accounts: " + accounts);
		System.out.println();
	}

	static void testWithdraw(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
		System.out.println("Withdraw:");
		int outAmount = bams.withdraw("123-456-789", "123","cred-1", "7000");
		System.out.println("outAmount: " + outAmount);
		System.out.println();
	}

	static void testDeposit(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
		System.out.println("Deposit:");
		double depAmount = bams.deposit("123-456-789", "123","cred-1", "1703");
		System.out.println("depAmount: " + depAmount);
		System.out.println();
	}

	static void testEnquiry(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
		System.out.println("Enquiry:");
		double amount = bams.enquiry("123-456-789", "123","cred-1");
		System.out.println("amount: " + amount);
		System.out.println();
	} // testEnquiry

	static void testTransfer(BAMSHandler bams) throws BAMSInvalidReplyException, IOException {
		System.out.println("Transfer:");
		double transAmount = bams.transfer("123-456-789", "cred-1","123", "456", "2000");
		System.out.println("transAmount: " + transAmount);
		System.out.println();
	} //testTransfer
	//------------------------------------------------------------
    // ATMStart
    public ATMSSStarter() {
	super("ATMSSStarter", "etc/ATM.cfg");
    } // ATMStart


    //------------------------------------------------------------
    // startApp
    protected void startApp() {
	// start our application
	log.info("");
	log.info("");
	log.info("============================================================");
	log.info(id + ": Application Starting...");

	startHandlers();
    } // startApp


    //------------------------------------------------------------
    // startHandlers
    protected void startHandlers() {
	// create handlers
	try {
	    timer = new Timer("timer", this);
	    atmss = new ATMSS("ATMSS", this);
	    cardReaderHandler = new CardReaderHandler("CardReaderHandler", this);
	    keypadHandler = new KeypadHandler("KeypadHandler", this);
	    touchDisplayHandler = new TouchDisplayHandler("TouchDisplayHandler", this);
	    advicePrinterHandler = new AdvicePrinterHandler("AdvicePrinterHandler",this);
	    cashCollectorHandler = new CashCollectorHandler("CashCollectorHandler",this);
		cashDispenserHandler = new CashDispenserHandler("TouchDisplayHandler", this);
	} catch (Exception e) {
	    System.out.println("AppKickstarter: startApp failed");
	    e.printStackTrace();
	    Platform.exit();
	}

	// start threads
	new Thread(timer).start();
	new Thread(atmss).start();
	new Thread(cardReaderHandler).start();
	new Thread(keypadHandler).start();
	new Thread(touchDisplayHandler).start();
	new Thread(advicePrinterHandler).start();
	new Thread(cashCollectorHandler).start();
	new Thread(cashDispenserHandler).start();
	} // startHandlers


    //------------------------------------------------------------
    // stopApp
    public void stopApp() {
	log.info("");
	log.info("");
	log.info("============================================================");
	log.info(id + ": Application Stopping...");
	atmss.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
	cardReaderHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
	keypadHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
	touchDisplayHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
	advicePrinterHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
	cashCollectorHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
	cashDispenserHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
	timer.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
    } // stopApp
} // ATM.ATMSSStarter
