/**
 * 
 */
package it.mapyou.persistence;

import it.mapyou.controller.AbstractCreator;

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
