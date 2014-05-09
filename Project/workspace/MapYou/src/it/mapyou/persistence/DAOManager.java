/**
 * 
 */
package it.mapyou.persistence;



/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class DAOManager {
	
	public abstract Source getSource();
	
	public abstract User_DAO getUserDAO();

	public abstract MapMe_DAO getMapMeDAO();

	public abstract Notification_DAO getNotificationDAO();
	
	public abstract Point_DAO getPointDAO();
}
