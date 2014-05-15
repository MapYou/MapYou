/**
 * 
 */
package it.mapyou.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMe extends SubjectModel implements Notificable{
	
	private User administrator;
	private List<Mapping> mapping;
	private GregorianCalendar creationDate;
	private int numUsers, maxNumUsers;
	
	/**
	 * 
	 */
	public MapMe() {
		mapping= new ArrayList<Mapping>();
		creationDate = new GregorianCalendar();
	}
	
	/**
	 * @return the creationDate
	 */
	public GregorianCalendar getCreationDate() {
		return creationDate;
	}
	
	/**
	 * @return the maxNumUsers
	 */
	public int getMaxNumUsers() {
		return maxNumUsers;
	}
	
	/**
	 * @return the numUsers
	 */
	public int getNumUsers() {
		return numUsers;
	}
	
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(GregorianCalendar creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * @param maxNumUsers the maxNumUsers to set
	 */
	public void setMaxNumUsers(int maxNumUsers) {
		this.maxNumUsers = maxNumUsers;
	}
	
	/**
	 * @param numUsers the numUsers to set
	 */
	public void setNumUsers(int numUsers) {
		this.numUsers = numUsers;
	}
	
	public boolean insertMapping(Mapping...m){
		try {
			for(int i=0; i<m.length; i++)
				mapping.add(m[i]);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public boolean removeMapping(Mapping...m){
		try {
			for(int i=0; i<m.length; i++)
				mapping.remove(m[i]);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public boolean removeAllMapping(){
		return mapping.removeAll(mapping);
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
