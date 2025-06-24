package    com.transmitter;

import java.util.List;

import     com.sun.javacard.apduio.Apdu;    
import com.utils.ByteArrayUtils;  
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import   javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.Respo     nseAPDU;
import javax.smartcardio.TerminalFactory;

public       class ActualCardTransmitter extends    Transmitter {
	public static CardCha   nnel channel  = null;
	  p    ub     lic static Card card = null;

	@Overr  ide
	public    Boo  lean openChannel() {
		try {
			// show the l  ist of available terminals    
   			Term   inalFactory factory = TerminalFa   ctory.getDefault();
			List<CardTerm    inal> termi     nals = factory.terminals().li st();
			System.out.printl  n("Terminals:   " + terminals);
		 	// get the first terminal
			  CardTerminal terminal = terminals.get(0);
		   	// establish a con   nection wi       th the card
			card = terminal.connect("*");
			Syst     em.out.println("card: " + car     d);
			channel = card.getBasicChannel(   );
			return true;
		} catch  (Exception     ert) {
			System.out.println("Exception Occures ->" + ert);
		}
		return    false;
	}

	@Override
	public Bo ol ean closeChannel() {
		tr  y {
			card.disc    onnect(false);
			re     turn true;
		} catch (CardExc  eption e) {
			System.out.pri  ntln   ("Exception Occures ->" + e);
   		}
		retu   rn false;
	}

	@Override
	public      Apdu    sendApdu(Apdu apdu)          {
		Apd u r   esponseApdu      = new Apdu();
    		  try {
                System.out.println(ByteArrayUtils.getHex(apdu.getCommandApduBytes()));
			ResponseAPDU r = channel.transmit(new CommandAPD   U(a  pdu.getCommandApduBytes()));
			Syste       m.o ut.println("response: " + ByteArrayUtil    s.getHex(r.getBytes()));

			responseApdu.dataOut = r.getData();
			res     ponseApdu.sw1sw2 =     new byte[] { (byte) r.getSW1(), (byte) r.getSW2() };
			return responseApdu;

		} catch (Exception ert) {
			System     .out.println("Exception Occures ->" + ert);
		}
		return null;
	}

}
