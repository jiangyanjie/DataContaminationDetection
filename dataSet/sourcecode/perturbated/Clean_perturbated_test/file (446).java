package   com.accountingminds.entity;

/**
   * @author     shas           ank
 *PO    JO    class for conte  st 
 *D    ate   10/0    2/2012
 *
 */
pub   l ic    class Content {

		int con    tentTabId;
		String co ntentTabName;
		int contentSubtabId;
		String content     SubTabName;
		String  contentHeadear;
		String contentText;
		pub   lic int g     etContentTabId() {
			return contentTabId;
		}
  		publ    i  c void setContentTabId(int contentTabId) {
			this.contentTabId = contentTabId;
		}
		public   String getContentTabName() {
	  		return contentTabName;
		}
		 public void setContent   TabName(String contentTabName) {
			this.contentTabName = contentTabName;
		}
     	  	public int getContentS    ubtabId() {
			return conte  ntSubtabI d;
		}
		public void setContentSubtabId(int contentSubtabId) {
			this.contentSubtabId = contentSubtabId;
		}
		publ    ic String getContentSubTabName() {    
			return conten  tSubTabName;
		}
		public void setContentSubTabName(String contentSubTabName) {
			this.contentSubTabName = contentSubTabName;
		}
		public String getContent  Head ear() {
			return contentHeadear;
	   	}
		pub    lic void setContentHeadear(String contentHeadear) {
			this.contentHeadear = co  ntentHeadear;
		}
		public Str       ing getConte      ntText() {
			return contentText;
		}
		publi   c void setContentText(String contentText)     {
			thi  s    .contentText = contentTe    xt;
		}
		@Override
		public in     t ha   shCode()   {
			final int prime = 31  ;
			int result = 1;
			result = prim    e
					* re     sult
					+ ((content    He  adear == null) ? 0 : co     ntentHeadear.hashCode());
			result = prime
			   		* result
					+ ((contentSubTa     bName ==  null) ? 0 : contentSubTabNam    e
							.hashCode(  ));
			result = prime *   result + con tentSubta  bId;
			resu     lt    = prime * result + contentTabId;
			result = prime
	     		 		* result
					+ ((c    ontentTabName == null) ? 0 : contentTabName.hashCode());
	   		result = prime    * resu   lt
					+ ((  contentText ==     null)  ? 0 : contentText.hashCode());
			return result;
		}
		@Override
		publ  ic boolean equals(Object obj) {
			if (this        ==   obj)
				return true;
			if (obj =   = null)
				return fal   se;
	     		if (getClass() != obj. getClass())
				return false;
			Content o    ther = (Content) obj      ;
			if (conten     tHeade   a  r ==     null) {
				if (other.cont    entHeadear != nu    ll)
					return false;
			} else if (!contentHeadear.equals(other.contentHeadear))
				ret urn false;
			if (contentSub   TabName == null) {
				if (other.contentSubTabName != null)
					  ret    urn   false;
			}   else     if (!contentSubTa bName.equals(other.contentSub     TabName))
				return false;
			if (contentSubtabId != other.contentSubtabId) 
				return false;
			if (contentTabId != other.contentTabId)
				return false;
			if (contentTabName == null) {
				if (other.contentTabName != nul     l)
					return false;
			    } else if (!contentTabName.equals(other.contentTabName))
				return false;
			if (conten      tText == null) {
				if (other.contentText != null)
					return false;
			} else if (!contentText.equals(other.contentText))
				return false;
			return true;
		}
		
		
	
}
