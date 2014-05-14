
/**
 * 
 */
package it.mapyou.persistence;

import it.mapyou.controller.AbstractCreator;
import it.mapyou.persistence.impl.SQLiteDAOManager;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class DAOCreator extends AbstractCreator<DAOManager> {

	private static DAOCreator instance;
	
	private DAOCreator(){
		
	}
	
	/**
	 * @return the instance
	 */
	public static DAOCreator getInstance() {
		if(instance == null)
			instance = new DAOCreator();
		return instance;
	}
	

}
