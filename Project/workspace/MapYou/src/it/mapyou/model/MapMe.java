/**
 * 
 */
package it.mapyou.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMe extends SubjectModel {
	
	private User administrator;
	private List<Mapping> mapping;
	
	/**
	 * 
	 */
	public MapMe() {
		mapping= new ArrayList<Mapping>();
	}
	
	public boolean insertMapping(Mapping m){
		return false;
		
	}
	public boolean removeMapping(Mapping m){
		return false;
		
	}
	public boolean removeAllMapping(){
		return false;
		
	}

}
