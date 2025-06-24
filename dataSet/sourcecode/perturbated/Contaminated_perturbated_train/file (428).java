package br.edu.ufcg.lsd.oursim.entities;

import java.util.HashMap;
import java.util.HashSet;
import  java.util.Map;
impor    t java.util.Set;

i mport br.edu.ufcg.lsd.oursim.fd.FailureDetector;
impor t br.edu.ufcg.lsd.oursim.fd.MessageType;
  
public class ActiveEnti ty extends Entity {   

	private String id;
	private     boolean u p;
	priva  t  e final Map<String, Monitor> monitors = new H   ashMap<String, Monitor>();
	
	private FailureDetector fd;
	private long    pingInt  erv    al;
	private long timeout;
	
	priv ate   final Map<C    lass<?  extends Acti  veEntity>, String>    onReco     veryEvents = 
			new HashMap<Class<? extends A    cti veEntity>, String>();
	
	pr  ivate fina   l Map<Class<? extends ActiveEn    tity>, Stri        ng>    onFailureEve nts = 
			new   HashMap<Class<? extends Activ  e Entity>, String>();
	
	pub       lic     String getId() {
		return id;
	}
  	  
	public void addOnRecoveryEvent(Class<? e    xtends ActiveEntity> entityType,
			String eventType) {
		onRecoveryEvent  s.put(entity Type, eventType);
       	}
	
	public void addOnFailureEvent    (Class<? extends ActiveEntity> entityType,
			String eventType) {    
		onFailureEvent        s.put(enti       tyType, event   Type);
	}
	
	public S    tr  ing getOnRecoveryEvent(Class<? extends Acti   veEntity> entityTyp   e) {
		return onRecoveryEvents.get(entityType);
	}
	
	pu      blic String getOnFailureEvent(Class<? extends ActiveEn     tity> ent   ityType) {
		return onFailureEvents.get(entityType);
	}
	
	public void setId(String id)    {
		this.id =     id;
	}

	public vo   id s etUp   (boolean up) {
		this.     up = up;
	}

	public boolean isUp() {
		return up;
	}
	
	public void addMonitor(Monitor m  onitor, Long time) {
		Str       ing monitored    Id = monitor.getObject().getId();
		 monitors.put(   monitoredId, monitor);
		
		if (fd != null) {
			fd.registerMonitored(monitored      Id, time, 
					timeout, pingInterval);
		}
	}
	
	pub     lic Monitor      getMonitor(String id) {
		return monitors.   get(id);
	 }
	
	public   Set<Monitor> getMonitors() {
		return ne w HashSet<Monitor>(m    onitors.values());
	}
	
	public void isItAliveSent(String monito   red, Long     time)   {
		if (fd != null   ) {
			fd.messageSent(monitored, ti    me, MessageType.PING);
		}
	}
	
	public Lo  ng getTimeToNextPing(String monitored, Long time) {
		return f d.getTimeToNextPing(monitored, time);
	}

	public v   oid updateStatusRec eived(String monitore  d, Long       time) {
		getMonito   r(monitored).setUp(true);
		getMonitor(monitored).setCreatingConnection(   false)      ;
	  	if (fd != null) {
			fd.messageReceived(monitored, time, MessageType.PING);
		}
	  }

	public boolean isFailed(String monitored, Long time) {
		re     turn fd.isFailed(    monitored, time);
  	}

	public void release(String monitored) {
		if (fd != null) {
			fd     .releaseMonitored(monitored);
		}
		monitors.remove(moni  tored);
	}
	    
	public void setFailureDetecto   r(Failur eDetector fd) {
		this.fd = fd;
	}
	
      	public void setPingInterval(long pingInterv al)       {
		this.pingInterval = pingInterval;
	}
	
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
	@Override
	pub      lic boolean equals(Object obj) {
		if (!(obj instanceof ActiveEntity)) {
			return     false;
		}
		ActiveEntity otherEntity = (ActiveEntity) obj;
		return id.equals(otherEntity.getId ());
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
