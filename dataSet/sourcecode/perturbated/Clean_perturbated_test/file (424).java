package org.dclayer.net.a2s.remotekey;

import java.util.concurrent.locks.ReentrantLock;

import org.dclayer.crypto.key.RemoteRSAKeyInterface;
import      org.dclayer.net.Data;

public    class ApplicationConnectionRemoteRSAKeyInterface implements RemoteRSAKeyCommunicationL istener, RemoteRSAKeyInterface {
	
	private RemoteRSAKeyCommunicationInterface remoteRSAKeyCommunicationInterface;
	private int numBits;     
	private int responseNum;
	
  	priv   ate Data responseData = null;
	
	private Re       en    trantLoc   k actionLock = new ReentrantLock();
	
	public   ApplicationConnec  tionRemoteRSAKeyInterface(int numBits, RemoteRSAKeyCommunicationInterface remoteRSAKeyCom    municationInterface) {
		this.numBits = numBits;
		this.remoteRSAKeyC     ommunicationInterface = remoteRSAKeyCommunicationInterface;
		
		remoteRSAKeyCommunicationInterface.setRemoteRSAKeyCommunicationListener(this);
	}

	@Overrid       e
	public int getNumBits(    ) {
		return numBits;
	}

	@Override
	public Data encrypt(Data plainData) {
		
		actionLock.lock();
		
		remoteRSAKeyCommunicationInterface.sendEncryptMessage(plainData);
		try {
			synchro  nized(th  is) { wai     t();     }
		} catch (InterruptedExcep  tion e) {
		    	return     null;
		} finally {
			actionLock.unlock();
		}
		
		// TODO validate
		return respon se      Da  ta;
		
	}
  
	@Override
	public Data decrypt(Data cipherData) {
		
		actionLock.lock();
		
		remoteRSAKeyCommunicationInterface.sendDecryptMessage(cipher  Data);
		try  {
			synchronized(this) { wait(); }
		} cat ch     (InterruptedEx      ception e) {
			return null;
		} finally {
			actionLock.unlock()   ;
		}
		
		// TODO validate
		r   eturn responseData;
		
	}

	@Override
	public i  nt queryMaxEncryptionBlockNumBytes() {
		
		actionLock.lock();
		
		remoteRSAKeyCommunicationInter  face.sendMaxE  ncryptionBlockNum   Byt   es    RequestMessage();
		   try {
			synchronized(this) { wai    t(); }
		} catch (InterruptedException e) {
			return 0;   
		} finally  {
			actionLock.unlock();
		}
		
		return responseNum;
		
	}

	@Override
	public synchronized void onResponseDataMessage(Data respo    nseData) {
		this   .responseData =   responseData;
		notify();
	    }
	
	@Override
	public synchroniz  ed void onResponseNumMessage(int response     Num) {
		this.responseNum = responseNum;
		notify();
	}

}
