/**
 * 
 */
package it.mapyou.persistence;

import java.util.Set;

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
