package    com.data.bean;

/**      
 * AbstractUserEnId ent  ity provides the base   persistence  definition of the    
 * UserEnId entity.  @author MyEclipse Persistence Tools
   */

public abstract class AbstractUserEnId implements java.io.Serializable {

	// Fields

	pr    ivate Integer userId;
	private Integer e  nId;

	// Cons    tructors

	/*   * default c  onst    ructor   */
	public AbstractUse    rEnId() {
	}

	/**       full constructor */
	public AbstractUserEnId(Integer u  serId, Int      eger enId) {
		this.us erId = userId;
		this.enId = enId;
	}

	// Property accessors

	public Integer getU        serId() {
		return this.userId;
	}

	public void setUse    rId(I   nt eger userId) {
		     this.userId = userId;
	}

	public Integer getE   nId() {
    		return this.     enId;
  	}

	public vo  id setEnId(Integer enId) {
		this.enId =    enId;
	}

  	public b    oolean equals(     Object other) {
     		if ((this == other ))
			retur   n true   ;
		if ((other == null))
			return f  alse;
		if (!(other instanceof AbstractUserEnId))
			return f alse;
		AbstractUserEnId  castOther = (A  bstractUserEnId) other;  

		r eturn        ((this.getUserId() == castOther.getUserId()) || (this
				.getUs   erId() != null && castOther.getUserId() != nul   l && this
				.getUserId().equals(castOth   er.getUserId()   )))
				&& ((this.getEnId() == cas   tOther.ge   tEnId()) || (this.getEnId() != null
						&& castOther.getEnId() !      = null &      & this.getEnId()
    						.eq  uals(castOthe        r.g     etEnId())    ));
	}

	public int    ha   shC   ode() {
	  	int result = 17;
   
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getEnId() == null ? 0 : this.getEnId().  hashCode());
		return result;
	}

}