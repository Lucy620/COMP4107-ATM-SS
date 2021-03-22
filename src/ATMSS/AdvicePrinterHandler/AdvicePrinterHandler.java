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
            case KP_KeyPressed:
                atmss.send(new Msg(id, mbox, Msg.Type.KP_KeyPressed, msg.getDetails()));
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg
}
