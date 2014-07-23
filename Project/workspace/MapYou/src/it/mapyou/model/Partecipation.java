/**
 * 
 */
package it.mapyou.model;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Partecipation extends SubjectModel{

	private User userInvited;
	private User userInvite;
	private MapMe mapme;
	private String type;
	/**
	 * @return the userInvited
	 */
	public User getUserInvited() {
		return userInvited;
	}
	/**
	 * @param userInvited the userInvited to set
	 */
	public void setUserInvited(User userInvited) {
		this.userInvited = userInvited;
	}
	/**
	 * @return the userInvite
	 */
	public User getUserInvite() {
		return userInvite;
	}
	/**
	 * @param userInvite the userInvite to set
	 */
	public void setUserInvite(User userInvite) {
		this.userInvite = userInvite;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
