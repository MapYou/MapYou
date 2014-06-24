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
	
	
	
	public User getSender() {
		return sender;
	}
	
	public void setSender(User sender) {
		this.sender = sender;
	}
	
	
	public User getReceiver() {
		return receiver;
	}
	
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	
	public Notificable getNotificableObject() {
		return notificableObject;
	}
	
	public void setNotificableObject(Notificable notificableObject) {
		this.notificableObject = notificableObject;
	}
	
	
}
