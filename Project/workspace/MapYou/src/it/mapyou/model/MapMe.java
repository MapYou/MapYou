/**
 * 
 */
package it.mapyou.model;

import java.util.HashMap;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMe extends SubjectModel {
	
	private User administrator;
	private HashMap<User, Mapping> mapping;
	
	/**
	 * 
	 */
	public MapMe() {
		mapping= new HashMap<User, Mapping>();
	}
	
	public boolean insertMapping(){
		return false;
		
	}
	public boolean removeMapping(){
		return false;
		
	}
	public boolean removeAllMapping(){
		return false;
		
	}

}
