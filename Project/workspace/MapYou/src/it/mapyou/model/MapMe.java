/**
 * 
 */
package it.mapyou.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMe extends SubjectModel implements Serializable {
	
	 
	private static final long serialVersionUID = 1L;
	private User administrator;
	private List<Mapping> mapping;
	private GregorianCalendar creationDate;
	private int numUsers, maxNumUsers;
	private String name;
	private Route route;
	private String startAddress;
	private String endAddress;
	
	
	 
	public MapMe() {
		mapping= new ArrayList<Mapping>();
		//creationDate = new GregorianCalendar();
	}
	
	/**
	 * @return the startAddress
	 */
	public String getStartAddress() {
		return startAddress;
	}
	/**
	 * @return the endAddress
	 */
	public String getEndAddress() {
		return endAddress;
	}
	/**
	 * @param endAddress the endAddress to set
	 */
	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
	/**
	 * @param startAddress the startAddress to set
	 */
	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}
	/**
	 * @return the route
	 */
	public Route getRoute() {
		return route;
	}
	
	/**
	 * @param route the route to set
	 */
	public void setRoute(Route route) {
		this.route = route;
	}
	
	/**
	 * @return the creationDate
	 */
	public GregorianCalendar getCreationDate() {
		return creationDate;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
