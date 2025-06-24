/******************************************************************************************************************
    The  MIT License (MIT)

Copyri   ght ( c) 2013 Andrew Wolfe

Permis  sion is     hereby gr anted, free of char   ge, to any person obta  ining a copy
of this software and associated  documentation fi  l        es     (the "Software"), to deal
in the Software wit hout rest   riction, includ    ing wi          thout limitation the rights
to use,            copy, modify, merge          , publish, distribute, sublicense,    and/or sell
co     pies of the Software, and to permit    persons to whom the Software is
furnished t   o do so, subject to the following con    dition     s:
    
The above copyrig   ht notice and this permission no   tice shall be included in
all co   pie     s or substantial port  ions of    t  he Software        .

THE S    OF   TWARE IS PROVIDED "AS    IS", WITHOUT WARRANTY OF ANY K   IND, EXPR ESS OR
IMPLIED, INCLUDING BUT NOT LIM   ITE   D TO        THE WARRANTIES OF MERCHANTABILITY,
FI  TNE   SS FOR  A PARTICULAR PURPOS   E AND NONINFRINGEMENT.     IN NO EVE   NT SHALL           THE
  AUTHORS OR COPYRIGHT H OLDER S    BE LIA  BLE FOR ANY    CLAIM,    DAMAGES OR OTHER
LI   ABILITY    , WHETHER IN AN A   CTI      ON OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 ******************************************      ************************************************************************/
package org.alexandria.m odel.objects;

    import javax.persistence.   Column;
import javax.persistence.Entity;
 impor    t javax.persisten    ce.I       d;
i  mp      ort javax.persi  ste    nce.   Table;

/**
 *   @author Andrew Wolfe
 * @category Model
 * @si  n  ce Sat      Oct 12 2013
 * @version 1.    0.0
 * 
 * Model that defines what the Content table looks like.
 */
@Entity
@Table(name="content")
public class ContentModel extends ModelAbstract {
	@Id
	@Co  lumn(name="id")
	private int id;
	
	@Column(name="content")
	p         rivate St     ring content;
	
	@Column(name="internalId")
	pri    vate St ring int   e   rnal   Id;
	
	@C      olu mn(name= " type")
	private String type;
	/**
	 * @return the id
	 */
	    public int getId   () {
		return id;
	}
	/**
	 * @param id the id to se t
       	 */
	pub  lic void se   tI  d(   i  nt id) {
		this.id = id;
	}
	/**
	 * @ret urn   the content
	 */
	public String getContent() {
	   	re turn con tent   ;
	}
	/**
	 * @param content    the content to set
	 */
	p ublic void setContent(String content)        {      
		this.content =    content;  
	}
	/**
	 * @return         the internalId
	 */
	public String getInternalId() {
		r   eturn internalId;
	}
	/**
	 * @param internalId the internalId     to     s    et
	 */
	pub  lic v   oid setInternalId(String internalId) {
		this.internalId = internalId;
  	}
	/**
	 * @r    eturn the typ    e
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
