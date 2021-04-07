package ATMSS.AdvicePrinterHandler;

import ATMSS.HWHandler.HWHandler;
import ATMSS.ATMSSStarter;
import AppKickstarter.misc.*;

public class AdvicePrinterHandler extends HWHandler{
    //------------------------------------------------------------
    // AdvicePrinterHandler
    public AdvicePrinterHandler(String id, ATMSSStarter atmssStarter) {
        super(id, atmssStarter);
    } // AdvicePrinterHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case CR_EjectCard:
                handleUpdateDisplay(msg);
                break;

            case TD_MouseClicked:
                handleUpdateLabel(msg);
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg

    protected void handleUpdateDisplay(Msg msg) {
        log.info(id + ": update display -- " + msg.getDetails());
    } // handleUpdateDisplay

    protected void handleUpdateLabel(Msg msg) {
        log.info(id + ": update display -- " + msg.getDetails());
    } // handleUpdateLabel


}
