package ATMSS.CardReaderHandler;

import ATMSS.HWHandler.HWHandler;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.*;
import AppKickstarter.misc.MBox;

import ATMSS.ATMSSEmulatorStarter;

//======================================================================
// CardReaderHandler
public class CardReaderHandler extends HWHandler {
    private MBox cardReaderMBox;

    //------------------------------------------------------------
    // CardReaderHandler
    public CardReaderHandler(String id, AppKickstarter appKickstarter) {
	super(id, appKickstarter);
    } // CardReaderHandler


    //------------------------------------------------------------
    // processMsg
    protected void processMsg(Msg msg) {
        switch (msg.getType()) {
            case CR_CardInserted:
                atmss.send(new Msg(id, mbox, Msg.Type.CR_CardInserted, msg.getDetails()));
                break;

            case CR_EjectCard:
                handleCardEject();
                break;

            case CR_CardRemoved:
                atmss.send(new Msg(id, mbox, Msg.Type.CR_CardRemoved,msg.getDetails()));
                handleCardRemove();
                break;

            case TimesUp:
                handleCardRetain();
                break;

            case TD_MouseClicked:
                clear();
                break;

            default:
                log.warning(id + ": unknown message type: [" + msg + "]");
        }
    } // processMsg


    //------------------------------------------------------------
    // handleCardInsert
    protected void handleCardInsert() {
        atmss.send(new Msg(id, cardReaderMBox, Msg.Type.TD_MouseClicked, "CardInserted"));
        log.info(id + ": card inserted");
    } // handleCardInsert


    //------------------------------------------------------------
    // handleCardEject
    protected void handleCardEject() {
        atmss.send(new Msg(id, cardReaderMBox, Msg.Type.TD_MouseClicked, "BlankScreen"));
        log.info(id + ": card ejected");

    } // handleCardEject

    protected void handleCardRetain(){
        log.info(id + ": card retained");
    }


    //------------------------------------------------------------
    // handleCardRemove
    protected void handleCardRemove() {
        //atmss.send(new Msg(id, cardReaderMBox, Msg.Type.TD_MouseClicked, "CardRemoved"));
        log.info(id + ": card removed");
    } // handleCardRemove

    protected void clear(){
        log.info("");
    }
} // CardReaderHandler
