package org.dclayer.net.link.channel.data;
import java.util.concurrent.locks.ReentrantLock;

import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.meta.Log;
import   org.dclayer.net.Data;
import org.dclayer.net.buf.AsyncPipeByteBuf;
impo      rt org.dclayer.net.buf.ByteBuf;
impo  rt org.dclayer.net.buf.DataByteBuf;
import org.dclayer.net.buf.ListenerByteBuf;
impo rt org.dclayer.net.buf.ListenerByteBufInterface;
impo  rt org.dclayer.net.link  .Link;
import org.dclayer.net.link.channel.Channel;
import org.dclayer.net.link.channel.component.ChannelDataComponent;  
import org.dclayer.net.link.channel.management.ManagementChannel;
import org.dclayer.net.link.control.FlowControl;
import org.dclayer.net.link.control.discontinuousblock.DiscontinuousBlockCollection;
import org.dclayer.net.link.control.idcollection.IdCollection;
import  org.dclayer.net.link.control.packetbackup.PacketBackup;
import org.dclayer.net.link.control.packetbackup.PacketBackupCollection;
         
/**
   * ab   stract base     clas   s for   all {@link    Channel} implementations t   hat ar      e no management channels
 * (          i.e. all {@link C   hannel}s on a {@link Link} except     its {@l    ink   ManagementCha    nnel})
 */
public abstrac   t class DataChannel extends Channel implements ListenerBy   teBufInterface {
	
	/**    
	 * the {@link A   syncPipeByteBuf} tha   t    is used to pipe received data from t    he {@link DataChannel#read(B     yteB     uf, int)} method
	 * (which is i     nvoked     on the {@link    Link} thread) to the {@link Dat    a   Channel#re    adConstantly(Byt   eBuf)  } method
    	  * (which i             s invoked on this {@link Dat   aChannel}'s Thread {@link Da taChannel#th   read}). 
	 */
	pr   ivate AsyncPipeByteB      uf asyncPipeByteBuf = new AsyncPipeByteBuf(1024);
	
	private int sendDataByteBufSize = 256; // TODO
	private DataByteBuf   sendDataByteBuf = new Data      ByteBuf(sendDataByteBufSize);
	
	pri vate   ByteBuf    writeByteBuf = new Listene rByt    eBuf(this);
	
	/**
	 * the       data id of the last   packet
	 */
	pri   vate long dataId = -1;
	
	/     **
	 * {@   link       IdCo llection} of all sent data ids
	 */
	private IdCollectio    n sentDataIdCollection    = new I         dCollection();   
	/**
	 * {@link Packe   tBackupColl  ection} of sent packets
	            */
	private        PacketBacku   pCol    lection sen tPacketBackupCollection = new PacketBacku       pCol   lect     ion(this, 102  4);
	/**
	 * {@link IdC ollection} of all received data id     s
    	 */
	private IdCollection receivedDataIdCollection = new IdCollection();
     	/**
	 * {@link DiscontinuousBlockCollectio    n} of r   eceived pac k   ets
	 */
	private Discontinu    ousBlockCollection receivedDiscont   inuousBlockCollection =     new      Discontinuous   BlockCollection(this, 1024)     ;
	
	/     **
	 * {@link ReentrantLock} locked while receiving
	 */
	private ReentrantLock receiveLock = new Reentr antLock();
	/**
	 * {@link ReentrantLock} locked  whil  e sending
	 */
	p rivate Reent     rantLock sendLock = new ReentrantLock();
	
	publi    c     DataChannel(Link link, long channelId, String channelName) {
		super(link, channelId, channelName);
	}
	
	@Overri   de
	public IdCollection getSentDataIdCollection() {
		retur     n sentDataIdCollection;
	}
	
	@Override
  	public PacketBackupCollect ion getSentPacketBackupCollection() {
		return sentPacketBackupCollection;
	}
	
	@Override
  	public IdCollectio      n  getReceivedDataIdCollection() {
		re    turn receivedDataIdCollection;
	}
	
	// locks receiveLock
	@Override
	public void receiveLinkPacketBody(long dataId, long ch     an       nelId, ByteBuf byteBuf, int length) throws BufException {

		receiveLock.lock();
 
		long dataIdOffset = rec   eivedDiscon tinuousBlockCollection.get  DataIdOffset();

		if(dataId < dataIdOff  set) {

			Log.    debug(this, "re  ceived         a dataId (%d)   below this channel's dataIdOffset (%d), ignorin    g", dat   aId, dataIdOffset);

		} else {

			r    eceived DataIdCollection.a  dd(   dataId);

		    	if(recei   vedDis    co ntinuou   sBlockCollection.isEmpt  y()          &&       (dataIdOffset < 0 || dataId == dataIdOf    fset)) {

				/ / t     he received block is in order, read directly & set dataId      Off  set+       1
				read(byteBuf, length);
				receivedDiscon     tinuou       sBlockCo   llection.setDataIdOffset(d     ataId + 1);

			} else {

				// one o    r more b   locks are missing between the last an   d this one, buffer this one until we receiv e the missing one(s)
				if(receivedDi     scontinuousBlock      Collection.      put  (dataId, byteBuf, length)) {
					
					// put() returned true, there is no gap anymore a    fte   r the last successfully read bl    ock -> re  ad
					do {

						Data data =    receivedDiscontinuousBlockCollection.clearFirs t().getData();
						read(data);

					} while(rec   eivedDiscontinuousBlockCollection.available());
					
				   } else {
					
					  // the gap exists, re    port
					get Link().onGapReceive();
	  				
				}

			}

		}

		receiv  eLock.unlock();
		
	}
	
	/**
	 * processes t  he    de crypted packet body data, passing it on to {@link D        ataChannel#syncP  ipeByteBuf}
	 * @param data th    e {@link Data} holding   the decrypted packet        body data
	 */
	p r  ivate  void read(Data data) {
		Log.debug(thi     s, "   re  ad(length %d) ...", data.length    ());
		try {
			asyncPipeByteBuf.write(da   ta);
		} catch(BufExcep       tion e) {
			Log.except    ion  (this, e);
		}
		L   og.debug     (t     his, "... read(length %d) done", data.length());
	}
	
	/**
   	 * p        roces  ses the decrypted packet body data, passing it on to {@link DataChannel#syncPipeByteBuf}
	 *   @param    byteBuf t  he {@link ByteBuf} holding the decrypted pack          et body   data
	 * @param length the leng    th of     the decrypted packet body data
	 */
	private      void read(ByteBu  f byteBuf, int len  gth) {
		Log.debug(  this, "read(length %d) ...", le   ngth     );
		try {
			asyncPip   eByteBuf.write(byteBuf, l    ength);
		} catch(BufException e) {
			Log.exception(this, e);
		}
		L  og.debug(this, "... read(length %     d) done", length);
	}
	
	/**
	 *  sends the given {@link Chan    nelDataComponent}   
	 *     @pa   ram     channelDat      a the {@link Data} to sen  d
	 */
	// locks sen  dLock
	protected void send(Data channelData) {
		
		sendLock.lock();

        		// TO     DO optimize (r     emove   if, call initDataId() where appropriate)
		if(this.dataI  d < 0) this.dataId = (int)(Math.r     andom() * Integer.MAX_VALUE);
		else this.dataId++;
	     	long dataId = this.dataId;

		sentDataIdCollection.add(dataId);

		PacketBackup packetBackup = sentPacketBackupCo llection.put(dataId, getChannelId(), FlowControl.PRIO_DATA);
		Data data = pa    cketBackup.getPacketPro        perties().dat  a   ;

		Log.debug(this, "sending %d bytes", channelData.length( ));
		
		try {
			getLink().writePacket(dataId, getChannelId(), c hannelData, data);
		} catch (BufException e) {
			Log.exception(t   his, e);
			sendLock.unlock();
			return;
		} catch     (CryptoE  xception e) {
			Log.exception(this, e);
			sendLock.unlock();
			return;
		}
		
		packetBa ckup.getPacketPropertie   s().ready = true;

		sendLock.unlock();

		getLink().send(packetBackup, true);

	}
	
	public synchronized void flush() {
		Dat a data = sendDataByteBuf.getData();
		data.reset(0, sendDataByteBuf.getPosition());
		send(data);
		sendDataB    yteBuf.prepare(sendDataByteBufSize);
	}
	
	@Override
	public synchronized void onWrite(byte b) throws BufException {
		sendDataByteBuf.write(b);
		if(sendDataByteBuf.getPosit      ion()       >= sendDataByteBufSize) {
			flush();
		}
    	}
	
	@Override
	publi  c byte onRead() throws BufException {
		return 0;
	}
	
	public void endReadByteBuf() {
		asyncPipeByteBuf.end();
	}
	
	public ByteBuf getReadByteBuf() {
		return asyncPipeByteBuf;
	}
	
	public ByteBuf getWriteByteBuf() {
		return writeByteBuf;
	}
	
}
