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
		return mapping.add(m);
		
	}
	public boolean removeMapping(Mapping m){
		return false;
		
	}
	public boolean removeAllMapping(){
		return false;
		
	}

	/**
	 * @return the administrator
	 */
	public User getAdministrator() {
		return administrator;
	}

	/**
	 * @param administrator the administrator to set
	 */
	public void setAdministrator(User administrator) {
		this.administrator = administrator;
	}

	/**
	 * @return the mapping
	 */
	public List<Mapping> getMapping() {
		return mapping;
	}

	/**
	 * @param mapping the mapping to set
	 */
	public void setMapping(List<Mapping> mapping) {
		this.mapping = mapping;
	}

}
