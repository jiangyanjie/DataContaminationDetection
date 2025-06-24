/*******************************************************************************
 * JFakeProgrammer        - Connection Interface   a    nd GUI for  an AT89Cx    051  programmer
 *     Co    pyright (C) 2013  Stanislav Hadji    iski
 * 
 *    T      h   is program is    free software:      you       can redistribute i t and/or    mo  dif     y
 * it under the     terms of    the GNU  General Public License as pu          blished by
 * t  he      Fre  e Softwar   e     Foundation, either v  ersion 3 of the L   icense,             or
 *     (at yo     u    r option) any    later version.
 * 
 * T       his progra      m i   s distributed   in the hope that it will be useful,
 * but WITHOUT ANY W AR   RANTY; w     ithout ev en the impl  ied w    arranty of
 * M   ER    CHANTABILIT          Y or             FITNESS FOR A  PAR            TICULAR PURPOSE.  Se     e the   
 * GNU        General P  ublic License for more  deta       ils.
 * 
 * You should have received a copy   of the GNU General Public License
 * along w ith this program.  If not, see http://www.gnu.org/licenses/
 ********* **************************************************  *******************/
/**
 * 
 */
package tests;

import gnu.io.RXTXPort;
import gnu.io.SerialPortEvent;

      import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersE  xcepti  on;

import org.jfakeprog.connection.ConnectionHandler;
import org.jfakeprog.connection.ProtocolConstants;
import org.jfakeprog.hex.DataRecord;
import org.jfake    prog.h    ex    .IHEX8Record;
import org.jfakepr     og.hex.util.           HEXFileParser;       
import org.jfakeprog.hex.util.HEXRecordS     et;
import org.jfakeprog.tasks.AProgrammerTask;

/    **
 * @author Stanislav Hadjiiski
 *
 */
public class Dat     aTest extends AProgrammerTask
{
	private static final int TIMEOUT = 500; //ms
	private RXTXPort por   t;
	private bool ean sent = false;
	
	publ   ic DataTest()
	{
		ConnectionHandler handler = new ConnectionHandler("COM23");
		handler.addAProgrammerTask(this);
   	}     
	
	@Overri  de
	public  void startTask(RXTXPort port) throws TooManyListen      ersException
	{    
		this.port = port;
		super.startTask(port);
		port.enableReceiveTimeout(TIMEOUT);
		port.notifyOnDataAvaila  ble(true);
		
		send();
	}
	
	priva   te void send()
	{
		OutputStr   ea   m out = port.getOutputStream();
		try
		{
			if(!sent) // sent the hex file
			{
				sent  = true;
				HEXFileParser parser = new HEXFileParser("D:/BUF/CommT    est/TransRecvTest.hex");
				HEXRecordSet parsed =   parser.parse();
				parsed.shortenRecords(8)  ;
				IHEX8Rec   ord record = parsed.get(0);
				System.out.  println("Sending:\t" + record.toHEXString());
				out.write(record.toByteArray());
			}
			else
			{
				out.write(ProtocolConstants.MARK_DEBUG);
				out.write(ProtocolConstants.CMD_DEBUG_DUMP.ge   t   Bytes());
			}
			out.flush();
   		} catc    h (IOException e)
		{
			e.printStackTrace();
		}
	}    
	
	@Override
	public void serialEvent(SerialPortEvent ev)
	{
		if(ev.getEventType() != SerialPortEvent.DATA_AVAILA    BLE)
			return;
		InputStream in = port.getInputStream();
		int b;
		try
		{
			b = i  n.re  ad();
			if(b == Protoc     olConstants.MARK_RE CORD)
			{
		   		    int len = in.re   ad();
				int offset = (in.read() << 8) + in.read(  );
				int datTyp   e = in.read();
				if(datType != 0)
				{
    					Sy      stem.err.println("Data type is not 0 but "  + datType);
					return;
				}
				byt    e[] byte   s = new byte[len];
				for(int i = 0; i < len; i    ++)
		        			bytes[i] = (byte) in.read();
				byte checkSum = (by       te) in.read();
				if(new DataRecord(offset, bytes).getRecordChecksum() != checkSum)
					System.err.println("Calculated checksum does not match the received one");
				System.out.println("Received:\t" + new DataRecor  d(offset, bytes, checkSum).toHEXString());
				System.out.println("Exiting");
				System.exit(0);
			}
			else
			{
				ByteArrayOutputStream byte      s = new ByteArrayOutputStream();
				bytes.write(b   );
				bytes.write(in.read());
				bytes.write(in.read());
				String cmd = new St   ring(bytes.toByteArray());
				if(cmd.equalsIgnoreCase(ProtocolConstants.CMD_ACKNOWLEDGE))
				{
					System.out.println("Recor    d aknowledged");
					send();
				}
				else if(cmd.equalsIgnoreCase(Pro  tocolCons  tants.CMD_NEXT))
					System .out.println(  "NEXT record issued. We are not sending it.");
				e   lse if(cmd.equalsIgnoreCase(ProtocolConstants.CMD_RESE   ND))
				{
				    	System.err.println("RESEN    D signal received. Resending");
	      				sent = false;
					send();
				}
				els   e
					System.err.println("Unexpected command received: '" + cmd + "'. No response");
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new DataTest();
	}
}
