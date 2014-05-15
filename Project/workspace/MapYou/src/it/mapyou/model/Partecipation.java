/**
 * 
 */
package it.mapyou.model;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Partecipation extends SubjectModel{

	private User user;
	private MapMe mapme;
	private boolean acceptance, isRequest;
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the mapme
	 */
	public MapMe getMapme() {
		return mapme;
	}
	/**
	 * @param mapme the mapme to set
	 */
	public void setMapme(MapMe mapme) {
		this.mapme = mapme;
	}
	/**
	 * @return the acceptance
	 */
	public boolean isAcceptance() {
		return acceptance;
	}
	/**
	 * @param acceptance the acceptance to set
	 */
	public void setAcceptance(boolean acceptance) {
		this.acceptance = acceptance;
	}
	/**
	 * @return the isRequest
	 */
	public boolean isRequest() {
		return isRequest;
	}
	/**
	 * @param isRequest the isRequest to set
	 */
	public void setRequest(boolean isRequest) {
		this.isRequest = isRequest;
	}
	
	
}
