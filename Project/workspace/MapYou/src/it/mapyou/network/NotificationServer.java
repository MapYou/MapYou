/**
 * 
 */
package it.mapyou.network;

import java.util.List;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class NotificationServer implements NotificationServerInterface {

	/* (non-Javadoc)
	 * @see it.mapyou.network.NotificationServerInterface#pushNotification(it.mapyou.network.Notification[])
	 */
	@Override
	public void pushNotification(Notification... notifications) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see it.mapyou.network.NotificationServerInterface#pullNotification()
	 */
	@Override
	public List<Notification> pullNotification() {
		// TODO Auto-generated method stub
		return null;
	}

}
