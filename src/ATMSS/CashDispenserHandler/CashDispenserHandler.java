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
            case TD_MouseClicked: // need to be updated
                atmss.send(new Msg(id, mbox, Msg.Type.TD_MouseClicked, msg.getDetails()));
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg


    //------------------------------------------------------------
    // handleCashDispense
    protected void handleCashDispense() {
        log.info(id + ": cash dispensed");
    } // handleCashDispense


    //------------------------------------------------------------
    // handleCashRemove
    protected void handleCashRemove() {
        log.info(id + ": cash removed");
    } // handleCashRemove
} // CashDispenserHandler
