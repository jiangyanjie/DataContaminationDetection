package business;

import interfaces.Connection;
import interfaces.Connector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectorTCP implements Connector, Connection {
	private Socket s = null;
	private BufferedWriter os = null;
	private BufferedReader br = null;
	private ServerSocket ss = null;
	private Socket client = null;
	private InetAddress ip;
	private int port;

	public InetAddress getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public boolean isClosed(){
		return s.isClosed();
	}

	@Override
	public Connection connect(String ip, int port) {
		// TODO Auto-generated method stub
		try {
			s = new Socket(ip, port);
			setIp(InetAddress.getByName(ip));
			setPort(port);
			s.setKeepAlive(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public String send(String message) {
		String retorno = "N";
		try {
			if (ss == null) {
				os = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			} else {
				os = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			}
			os.write(message + System.getProperty("line.separator"));
			retorno = "S";
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public String recieve() throws IOException {
		String str = "";
		if (ss == null) {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		}
		str = br.readLine();
		System.out.println("depois do readLine: "+ str);
		return str;
	}

	@Override
	public void close() {
		if (os != null) {
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public Connection startServer(int port) throws IOException {
		ss = new ServerSocket(port);

		return this;
	}

	@Override
	public Connection acceptClient() throws IOException {
		ConnectorTCP c = new ConnectorTCP();
		client = ss.accept();
		client.setKeepAlive(true);
		c.client = client;
		c.setIp(client.getInetAddress());
		c.setPort(client.getPort());
		c.s = s;
		c.ss = ss;
		setIp(client.getInetAddress());
		setPort(client.getPort());
		return c;
	}

}
