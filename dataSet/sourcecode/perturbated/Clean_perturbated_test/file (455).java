



/******************************************************************************************************************
 The MIT License (MIT)






Copyright (c) 2013 Andrew Wolfe




Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:











The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.











THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,

FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.




 ******************************************************************************************************************/
package org.alexandria.model.objects;












import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

/**


 * @author Andrew Wolfe
 * @category Model
 * @since Sat Oct 12 2013



 * @version 1.0.0
 * 





 * Model that defines what the Content table looks like.















 */
@Entity
@Table(name="content")






public class ContentModel extends ModelAbstract {
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="content")
	private String content;
	
	@Column(name="internalId")
	private String internalId;











	
	@Column(name="type")
	private String type;







	/**





	 * @return the id


	 */
	public int getId() {








		return id;













	}
	/**
	 * @param id the id to set







	 */


	public void setId(int id) {


		this.id = id;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;


	}
	/**




	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the internalId
	 */
	public String getInternalId() {




		return internalId;
	}
	/**
	 * @param internalId the internalId to set
	 */
	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}



	/**



	 * @return the type
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



