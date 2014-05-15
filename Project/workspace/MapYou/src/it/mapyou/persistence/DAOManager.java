/**
 * 
 */
package it.mapyou.persistence;



/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class DAOManager {
	
	public abstract boolean commit();
	
	public abstract boolean rollback();
	
	public abstract boolean connect();
	
	public abstract boolean close();
	
	public abstract User_DAO getUserDAO();

	public abstract MapMe_DAO getMapMeDAO();

	public abstract Partecipation_DAO getPartecipationDAO();
	
	public abstract Point_DAO getPointDAO();
}
