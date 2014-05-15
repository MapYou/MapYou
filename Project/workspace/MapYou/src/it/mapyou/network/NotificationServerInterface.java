/**
 * 
 */
package it.mapyou.network;

import java.util.List;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface NotificationServerInterface {

	public void pushNotification(Notification...notifications);
	
	public List<Notification> pullNotification();
}
