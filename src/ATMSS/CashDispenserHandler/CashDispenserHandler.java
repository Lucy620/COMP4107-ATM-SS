package ATMSS.CashDispenserHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;


//======================================================================
// CashDispenser
public class CashDispenserHandler extends HWHandler {
    //------------------------------------------------------------
    public CashDispenserHandler(String id, AppKickstarter appKickstarter) {
        super(id, appKickstarter);
    }


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {

            case TD_UpdateDisplay:
                handleUpdateDisplay(msg);
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg

    //------------------------------------------------------------
    // handleUpdateDisplay
    protected void handleUpdateDisplay(Msg msg) {
        log.info(id + ": update display -- " + msg.getDetails());
    } // handleUpdateDisplay

} // CashDispenserHandler
