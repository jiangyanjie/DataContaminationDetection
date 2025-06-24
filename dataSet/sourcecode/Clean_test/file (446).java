package com.accountingminds.entity;

/**
 * @author shasank
 *POJO class for contest 
 *Date 10/02/2012
 *
 */
public class Content {

		int contentTabId;
		String contentTabName;
		int contentSubtabId;
		String contentSubTabName;
		String contentHeadear;
		String contentText;
		public int getContentTabId() {
			return contentTabId;
		}
		public void setContentTabId(int contentTabId) {
			this.contentTabId = contentTabId;
		}
		public String getContentTabName() {
			return contentTabName;
		}
		public void setContentTabName(String contentTabName) {
			this.contentTabName = contentTabName;
		}
		public int getContentSubtabId() {
			return contentSubtabId;
		}
		public void setContentSubtabId(int contentSubtabId) {
			this.contentSubtabId = contentSubtabId;
		}
		public String getContentSubTabName() {
			return contentSubTabName;
		}
		public void setContentSubTabName(String contentSubTabName) {
			this.contentSubTabName = contentSubTabName;
		}
		public String getContentHeadear() {
			return contentHeadear;
		}
		public void setContentHeadear(String contentHeadear) {
			this.contentHeadear = contentHeadear;
		}
		public String getContentText() {
			return contentText;
		}
		public void setContentText(String contentText) {
			this.contentText = contentText;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((contentHeadear == null) ? 0 : contentHeadear.hashCode());
			result = prime
					* result
					+ ((contentSubTabName == null) ? 0 : contentSubTabName
							.hashCode());
			result = prime * result + contentSubtabId;
			result = prime * result + contentTabId;
			result = prime
					* result
					+ ((contentTabName == null) ? 0 : contentTabName.hashCode());
			result = prime * result
					+ ((contentText == null) ? 0 : contentText.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Content other = (Content) obj;
			if (contentHeadear == null) {
				if (other.contentHeadear != null)
					return false;
			} else if (!contentHeadear.equals(other.contentHeadear))
				return false;
			if (contentSubTabName == null) {
				if (other.contentSubTabName != null)
					return false;
			} else if (!contentSubTabName.equals(other.contentSubTabName))
				return false;
			if (contentSubtabId != other.contentSubtabId)
				return false;
			if (contentTabId != other.contentTabId)
				return false;
			if (contentTabName == null) {
				if (other.contentTabName != null)
					return false;
			} else if (!contentTabName.equals(other.contentTabName))
				return false;
			if (contentText == null) {
				if (other.contentText != null)
					return false;
			} else if (!contentText.equals(other.contentText))
				return false;
			return true;
		}
		
		
	
}
