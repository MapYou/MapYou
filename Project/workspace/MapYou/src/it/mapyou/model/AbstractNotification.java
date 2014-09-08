/**
 * 
 */
package it.mapyou.model;

import java.util.GregorianCalendar;

import android.os.Parcelable;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class AbstractNotification<X, Y, Z> extends SubjectModel implements Parcelable{

	private X notifier;
	private Y notified;
	private Z notificationObject;
	private String notificationState;
	private String notificationType;
	private GregorianCalendar date;
	
	/**
	 * @return the date
	 */
	public GregorianCalendar getDate() {
		return date;
	}
	
	/**
	 * @param date the date to set
	 */
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	
	/**
	 * @return the notificationState
	 */
	public String getNotificationState() {
		return notificationState;
	}
	/**
	 * @param notificationState the notificationState to set
	 */
	public void setNotificationState(String notificationState) {
		this.notificationState = notificationState;
	}
	/**
	 * @return the notificationType
	 */
	public String getNotificationType() {
		return notificationType;
	}
	/**
	 * @param notificationType the notificationType to set
	 */
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	/**
	 * @return the notifier
	 */
	public X getNotifier() {
		return notifier;
	}
	/**
	 * @param notifier the notifier to set
	 */
	public void setNotifier(X notifier) {
		this.notifier = notifier;
	}
	/**
	 * @return the notified
	 */
	public Y getNotified() {
		return notified;
	}
	/**
	 * @param notified the notified to set
	 */
	public void setNotified(Y notified) {
		this.notified = notified;
	}
	/**
	 * @return the notificationObject
	 */
	public Z getNotificationObject() {
		return notificationObject;
	}
	/**
	 * @param notificationObject the notificationObject to set
	 */
	public void setNotificationObject(Z notificationObject) {
		this.notificationObject = notificationObject;
	}
	
	
}
