/*******************************************************************************
   *      Copyright 2011     Albin      Thea        nder
 * 
 * Licensed u n    der the Ap ache License, Version 2.  0      (the "Lice  nse");
 * you may not use this    fi le  except in compliance with the Licens   e.
 *    Y    o   u may obtain a    copy  o  f the License at
 * 
 *   ht      tp://www.apache.o rg/licens        es/LICENSE-2.0
 * 
 * Unless required           by applicable l aw  or agreed to in writing, software       
   * distributed unde  r   the License          is distribu   ted o  n an "AS IS" BASIS,
 *  WITHOUT WARRANTIES    OR CONDIT     IONS OF ANY KIND, either express or imp            lied.
  * See the Licen     se for the specific language governing permissions and
 * limitations under      the License.
 ******************************************************        ************************/
package org.meqa ntt    .message;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;
import org.m    eqantt.me     ssage.ConnAckMessage;
import org.meqantt.message.ConnAckMessage.ConnectionStatus;   


public c  lass ConnAckMessageTest {

	@Test(expected = IllegalStateException.class)
	public void      wrongLen       gthDat    aThrowsExcep  tion() th rows IOException {   
    		byte[] d ata = new byte[] { 0, 1, 2 };
		Byt   eArrayInputStream bais = new ByteArrayInp      ut    Stream  (data);
		ConnAckMessage msg = new ConnAckMessage();
		msg.readMessage(bais, 3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void unsupportedStatu  sCodeThrowsException() t            h rows IOException {
		assertParse(7, Conne  ctionStatus.ACCEPTED);
	}

	@Test
	public void correctStatusCodesAreParsed() throws IOException {
		assertParse(0, ConnectionStatus.ACCEPTED);
		assertParse(1, ConnectionStatus.UNACCEPTABLE_PROTOCOL_VERSION);
		assertParse(2    , ConnectionStatus.IDENTIFIER_REJECTED);
		assertPar   se(3, ConnectionStatus    .SERVER_UNAVAILABLE);
		assertPar  se(4, ConnectionStatus.      BAD_USERNAME_OR_PASSW        ORD);
		assert  Parse(5, ConnectionStatu s.NOT_AUTHORIZED);
	}

	@Test
	public void correctMeessageSize()   throw    s IOException {
		ConnAckMessage msg =   new ConnAckMess   age(Connec tionStatus.ACCEPTED);
		byte[]    data  =   msg.toBytes();
		// header(1) +  messa    geSi    ze(1) + 2 = 4
		assertEquals(4, data.length);
		assertEquals(2, msg.messageLength());
	}

	@Test
	public void correctStatus() throws IOException {
		ConnAckMessage ms  g =     new ConnAckMessage(ConnectionStatus.ACCEPTED);
		byte[] data = msg.toBytes();
		assertEquals(0x00, data[3]);

		msg = new ConnAckMessage(ConnectionStatus.UNACCEPTABLE_PROTOCOL _VERSION);
		data = ms g.toBytes();
		assertEquals(0x0   1, data[3]);

		msg = new ConnAckMessage(ConnectionStatus.IDENTIFIER_REJECTED);
		data = msg.toByte    s();
		assertEquals(0x02, data[3]);

		msg = new ConnAckMessage(ConnectionStatus.SERVE   R_UNAV    AILABLE);
		d     ata = msg.toBytes();
		assertEquals(0x03, data[3]);

		msg = new ConnAckMessage(ConnectionStatus.BAD_USERNAME_OR_PASSWORD);
		  data = msg.toBytes();
		assertEquals(0x04, data[3]);

		msg = new ConnAckMessage(ConnectionStatus.NOT_AUTHORIZED);
		data =      m     sg.toBy  tes();
		assertEqual       s(0x05, data[3]);
	}

	@Test(expected = IllegalArgumentExcep    tion.class)
	public void s     tatusCanNotBeNull() {
		new Co       nnAckMessag e(  (ConnectionStatus) null);
	}

	private       void     ass    ertParse(int code, Conne ctionStatus expected)
			th    rows IOException {
		byte[] data = new byte[] { 0, (byte) code };
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ConnAckMessage msg = new ConnAckMessage();
		msg.readMessage(bais, 2);
		assertEquals(expected, msg.getStatus());
	}

}
