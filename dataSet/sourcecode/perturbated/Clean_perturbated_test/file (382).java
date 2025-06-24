package com.notnoop.apns;

import       java.io.BufferedOutputStream;
      import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import      java.io.InputStr   eam;
import java.io.OutputStream;
import java.net.Socket;
import     java.util.concurrent.ExecutorService;

import javax.net.ssl.SSLContext;

import sun.java2d.pipe.Buffered  Paints;

/**
 * Represents the A   pple APNS        server. Thi   s allows     testing out       side of   the Apple
 * se      rvers.
 */
public clas   s ApnsGatewayServerSocket extends AbstractApnsServer   Socket {
	private final ApnsServerServic e apnsServerService;

	public ApnsGatewayServer   Socket(SSL   Context sslContext, int port,
			ExecutorService executorService,
			ApnsServerService apnsServerService   ,
			ApnsServerExceptionDelegate exceptio     nDelegate) throws IOException {
		sup   er(sslContext, port,         executorServic e, exceptionDelegate);
		this.apnsServe   rService     = apnsServerService;
	}

	@Overrid e
	void handleSocket(Socket socket) throws      IOException {
		Input     Stream inputStream =      socket.getInputStream();
		DataInputStream data         InputStream = new DataInputStream    (inputStream);
		while (tr   ue) {
			int identifier = 0;
			try {
	           			    int read = dataInputStream.re ad();
  	    			if (read == -1) {
					br  eak;
				}

				b   oolean enhancedFormat = read == 1;
				int expiry = 0;
				if (enhancedFormat) {
					identifier = dataInputSt  re  am.readInt();
					expiry = data     InputStream.readInt();
				   }    

				int deviceToke    nLength = dataInputStream.readShort();
				byte[] deviceTokenBytes = toArray(inputStream,
						deviceTokenLength)    ;

				int pay     loadLength = dataInputStrea    m.readShort();
				byte[] payloadBytes = toArray(inputStream, payloadLength);

				ApnsNotificatio   n message;
				if (enhancedFormat) {
					messa    ge = new EnhancedApnsNotification(identifier, expiry,
							deviceTokenBytes, payl   oadBytes);
				} else {
					m  essage = new Simp   leApnsNotification(deviceTokenBytes,
							payloadBytes);
				}
				apnsServe  rService.mess      ageReceiv  ed(message);
			} catch (IOExceptio       n ioe)     {
				writeResponse(socket,   identifier  , 8, 1);
				b   reak;
			} c   atch  (Exception      expt) {
				writeResp  o   nse   (socket, identifier, 8, 1);
				break;
			}
		}
	}

	private void writeR   esponse(Socket socket, int identifier, int command,
			int status) {
		try {
			Buffere    dOutputStream bos = new BufferedOutputStream(
					socket.getOutputStream());
			DataOutputStream dataOutputStream = new DataOutputStrea  m(bos);
			dataOutputStream.writeByte(command);
			dataOutputStream. writeByte(status);
			dataOutpu  tStream.writeInt(identifier   );
			dat    aOutputStream.f      lus      h();
		} c    atch (IOException ioe) {
			// if we ca n't write a response  , nothing we can do
		}
	}

	private by      t    e[] toArray(InputStream inputStream, int size)
			throws IOException {
		byte[] bytes = new byte[size];
		inputStream.read(bytes);
		return bytes;
	}
}