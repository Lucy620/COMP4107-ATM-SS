package ATMSS.CashCollector;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;

//======================================================================
// CashCollectorHandler
public class CashCollectorHandler extends HWHandler{
    //------------------------------------------------------------
    // CashCollectorHandler
    public CashCollectorHandler(String id, AppKickstarter appKickstarter){
        super(id, appKickstarter);
    } // CashCollectorHandler



    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case TD_MouseClicked: // need to be updated
                atmss.send(new Msg(id, mbox, Msg.Type.TD_MouseClicked, msg.getDetails()));
                break;

            case TD_UpdateDisplay: // need to be updated
                handleUpdateDisplay(msg);
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    }// processMsg


    //------------------------------------------------------------
    // handleUpdateDisplay
    protected void handleUpdateDisplay(Msg msg) {
        log.info(id + ": update display -- " + msg.getDetails());
    } // handleUpdateDisplay
}
