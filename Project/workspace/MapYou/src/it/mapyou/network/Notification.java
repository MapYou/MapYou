/**
 * 
 */
package it.mapyou.network;

import it.mapyou.model.Notificable;
import it.mapyou.model.User;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Notification {

	private User sender, receiver;
	private Notificable notificableObject;
	/**
	 * @return the sender
	 */
	public User getSender() {
		return sender;
	}
	/**
	 * @param sender the sender to set
	 */
	public void setSender(User sender) {
		this.sender = sender;
	}
	/**
	 * @return the receiver
	 */
	public User getReceiver() {
		return receiver;
	}
	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	/**
	 * @return the notificableObject
	 */
	public Notificable getNotificableObject() {
		return notificableObject;
	}
	/**
	 * @param notificableObject the notificableObject to set
	 */
	public void setNotificableObject(Notificable notificableObject) {
		this.notificableObject = notificableObject;
	}
	
	
}
