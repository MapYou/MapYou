/**
 * 
 */
package it.mapyou.persistence;

import it.mapyou.model.MapMe;
import it.mapyou.model.Notification;
import it.mapyou.model.User;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class DAOManager {

	public abstract Source getSource();
	
	public abstract User_DAO<User> getUserDAO();

	public abstract MapMe_DAO<MapMe> getMapMeDAO();

	public abstract Notification_DAO<Notification> getNotificationDAO();
}
