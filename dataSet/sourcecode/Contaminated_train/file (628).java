package business;

import interfaces.Connection;
import interfaces.Connector;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class ConnectorUDP implements Connector, Connection {

	private DatagramSocket ds = null;
	private DatagramSocket ss = null;
	private ArrayList<DatagramSocket> clients = null;
	private InetAddress ip;
	private int port;

	@Override
	public Connection connect(String ip, int port) {
		// TODO Auto-generated method stub
		try {
			ds = new DatagramSocket();
			ds.connect(new InetSocketAddress(ip, port));
			setIp(InetAddress.getByName(ip));
			setPort(port);
			// Envia uma mensagem para o servidor aceit�-la
			send("Connect");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public String send(String message) {
		String retorno = "S";
		DatagramPacket pacote;
		try {
			if (ds != null) {
				pacote = new DatagramPacket(message.getBytes(), message.length(), ds.getInetAddress(), ds.getPort());
				ds.send(pacote);
			} else {
				pacote = new DatagramPacket(message.getBytes(), message.length(), getIp(), getPort());
				ss.send(pacote);
			}
		} catch (IOException e) {
			e.printStackTrace();
			retorno = "N";
		}
		return retorno;
	}

	@Override
	public String recieve() throws IOException {

		String str = "";

		byte[] buffer = new byte[1000];
		DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);

		if (ds != null) {
			ds.receive(pacote);
		} else if (ss != null) {
			ss.receive(pacote);
		}
		str = new String(pacote.getData(), 0, pacote.getLength());

		return str;
	}

	@Override
	public void close() {
		if (ds != null) {
			ds.close();
		}

		if (ss != null) {
			ss.close();
		}
	}

	@Override
	public Connection startServer(int port) {
		try {
			ss = new DatagramSocket(port);
			clients = new ArrayList<DatagramSocket>();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public Connection acceptClient() throws IOException {
		// accept bloqueia enquanto n�o receber uma conex�o

		byte[] buffer = new byte[1000];
		DatagramSocket c = new DatagramSocket();
		DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);

		ss.receive(pacote);
		c.connect(pacote.getAddress(), pacote.getPort());
		clients.add(c);

		// retorna IP e porta da conex�o
		setIp(pacote.getAddress());
		setPort(pacote.getPort());
		ConnectorUDP cUDP = new ConnectorUDP();
		cUDP.ss = ss;
		cUDP.setIp(pacote.getAddress());
		cUDP.setPort(pacote.getPort());
		return cUDP;

	}

	private void setPort(int port) {
		this.port = port;

	}

	private void setIp(InetAddress address) {
		this.ip = address;
	}

	@Override
	public InetAddress getIp() {
		return ip;
	}

	@Override
	public int getPort() {
		return port;
	}
	
	public boolean isClosed(){
		return ds.isClosed();
	}

}
