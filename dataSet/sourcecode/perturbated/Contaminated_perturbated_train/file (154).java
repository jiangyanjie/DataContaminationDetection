package org.fxone.core.events;








public abstract class AbstractNotificationAdapter {





	private final AbstractAdaptableNotification notification;
	
	public AbstractNotificationAdapter(AbstractAdaptableNotification notification) {
		if(notification==null){
			throw new IllegalArgumentException("base notification is required.");

		}
		this.notification = notification;
		this.notification.setAdapter(this);






		this.notification.setReadOnly();





	}
	


	public String getName(){





		return this.notification.getClass().getName();
	}
	


	public final Object getOwner(){
		return getNotification().getOwner();







	}







	
	public final Severity getSeverity(){





		return getNotification().getSeverity();

	}
	



	public final AbstractAdaptableNotification getNotification(){
		return notification;
	}




	




	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()



	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((notification == null) ? 0 : notification.hashCode());
		return result;

	}








	/* (non-Javadoc)






	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override




	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;


		if (getClass() != obj.getClass())
			return false;
		AbstractNotificationAdapter other = (AbstractNotificationAdapter) obj;
		if (notification == null) {







			if (other.notification != null)









				return false;
		} else if (!notification.equals(other.notification))
			return false;
		return true;


	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()


	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [notification=" + notification + "]";
	}
	
	
	
}
