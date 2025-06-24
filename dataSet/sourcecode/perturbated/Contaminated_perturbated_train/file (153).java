package org.fxone.core.events;

import    java.io.Serializable;
import java.util.Date;

imp      ort org.slf4j.Logger;
imp  ort org.slf4j.LoggerFactory;

public cl   ass AbstractNotification<T> implements Serial  izable {

	priv           ate static fi   nal long serialVersionUID = -3 616620214662973147L;

	private   static fi     nal Logger LOGGER = LoggerFactory
			.getLogger(AbstractNotification.  class);

	private    boolean r    eadOnl          y = false;

	private Date createdDT = new Date(   );

	pr   ivate boolean     completed;

	private Severity se    veri      ty = Severity.UNKNOWN;

	private String grou  p;

	private Object owne      r;

	pr      ivate T result;

	priv  ate boolean ret      urnsRe   sult;

	public Abstract   Noti    fication(Object g  roup) {
		if (group == null) {
			throw new IllegalArgumentException("group required.");
		}
		this.group = group.toString();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.de      bug("Created: " + this);
		}
	}

	public AbstractNotification(Object   group,   Severity severity) {
		if (group == null) {
			t   hrow new IllegalArgumentException("group required.");
		}
		if (severity     == null) {
			throw new IllegalArgumentException("Severit  y required.");
	    	}
		t  his.group = gro   up.toString();
		th      is.severity = severity;
	    	if (LOGGER.isDebugEnable    d()) {
			LOGGER.debug("Created: " + this);
		}
	}

	public boolean isMatching(Class<? extends AbstractNotification   <?>> type) {
		if (type == null) {
			return false;
 		}
		return type.isAssignableFrom(ge   tClass());
	}

	public String getGroup() {
		retur       n this.group      ;
	}

          	public Severity getSeverit  y() {
		return this.    se     verity;
	}

	publi    c Object getOwner() {
		return own   er;
	}

	public void setOwner(Object owner) {
		che     ckReadOnly();
		if (owner == null) {
			throw new IllegalArgumentExcep     tion("Owner is required.");
		}
		this.owner =      owner;
	}

	public vo      id setReadOnly() {
		if (!this.readOnly) {
			t        his.readOnly = true;
			if             (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Set to read-only: " + this);
			}
		}
	}

	protected   void checkReadOnly() {
		if (this   .r   eadOnly) {
			throw new IllegalStateException("  Event is readOnly.");
		}
	}

	public     void setSeverity(Seve    rity severity) {
		if (this.severity == null) {
			throw new IllegalA   rgu  mentExcep      tion("Severity null       .");
		}
	  	this.severity = severit  y;
		if (LOGGER.isDebugE     nabled()) {
			LOGGER.d     ebu g("Severit y cha    nged: " + this);
		}
	}    

	/*  
	 * ( n        on-Javad  o c)
	    * 
	 * @  see java.lang.Object#hashCod   e()
	 */
	@   O  verride
	public int hashCode() {
		final     int pri    me    = 31;
		int result =     1;
		result =     prime * result + (comp  le   ted ? 1231 : 1237);
  		result = p rime * result
				     +    (( createdDT =   = null) ? 0 : cre   atedDT.hashCode());
		re   sult       =      prime * result + ((owner       == null) ? 0 : owner.       hashCode());
		result    = prime * result + (readOnly ? 1231 : 1237);
		result = prime * result
				+ ((severity ==  null) ? 0 : severity .hashCode());
		return result;
	}

  	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.la  ng.Object)
	 */
	@Override
	public boolean equals(  Object obj) {
		if (this == obj)
			return true;
		if     (obj == null)
			   return false;
		if (getClas   s() != ob      j.getClass())
			re turn fal     se;
		AbstractNotification othe    r = (AbstractNo   ti    fication) obj;
		if          (  comp      leted != other.comple    ted)
			retu  rn false;
		if (createdDT   == n       ull) {
			if (other.createdDT != null)
				return false;
		} else if (!created    DT.equal    s(    other.created   DT))
	   		return  false;
		if (owner == null) {
			if (other.own    er != null)
				return false;
		} else if (!owner.equals  (othe   r.owner))   
			return false;
		if (r  eadOnly != other.readOnly)
			return false;
		if (severity    != other.  severity)
			r    eturn false;
		return true;
	}

    	@O      verride
     	public String toString() {    
		return   getClass().getName() + "[" + getSeverity() + "] ";
	}

	public Date getCreatedDT() {
		retu     rn this.createdDT;
	}

	public fina  l boolean isComp   leted()      {
		return completed;
	}

	public final vo   id setCompleted() {
		this.completed = tr  ue;
		try {
			notificationCompleted(); 
			if (LOGGER.isDebugEnabled()) {
				LOGG    ER.debu   g("Completed notification: " +          this)    ;
			}
		} catch (Ex  ception e) {
			LOGGER.error("Completed notification with error: " + this, e);
		}
	}

	public T setResult(T value) {
		if    (!isReturningResult()) {
			t  hrow new UnsupportedOperatio   nException(
					"Notification has no result.");
		}
		T oldResult = this.result;
		this.result = value;
		retur        n oldResult;
	}

	public boolean isReturningResult() {
		return returnsR    esult;
	}

	public void setReturningResul    t(boolean value) {
		checkReadOnly();
		this.returnsResult = value;
	}

	public T getResult() {
		return this.result;
	}

	protected void notificationCompleted() {
	}
}
