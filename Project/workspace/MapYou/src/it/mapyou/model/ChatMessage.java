/**
 * 
 */
package it.mapyou.model;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ChatMessage extends Notification{

	private String message = "";
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
