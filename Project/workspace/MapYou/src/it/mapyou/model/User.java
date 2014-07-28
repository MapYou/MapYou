/**
 * 
 */
package it.mapyou.model;

import java.io.Serializable;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class User extends SubjectModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4972158577677211216L;
	private String nickname;
	private String password;
	private String email;
	private String idNotification;
	
	public User() {
		this.nickname = "";
		this.password = "";
		this.email = "";
		this.idNotification="";
	}
	
	/**
	 * @return the idNotification
	 */
	public String getIdNotification() {
		return idNotification;
	}
	/**
	 * @param idNotification the idNotification to set
	 */
	public void setIdNotification(String idNotification) {
		this.idNotification = idNotification;
	}
	
	/**
	 * @param nickname
	 * @param email
	 */
	public User(String nickname, String email) {
		super();
		this.nickname = nickname;
		this.email = email;
	}



	/**
	 * @param nickname
	 */
	public User(String nickname) {
		super();
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return nickname;
	}

	 
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	 
	public void setPassword(String password) {
		this.password = password;
	}

	 
	public String getEmail() {
		return email;
	}

	 
	public void setEmail(String email) {
		this.email = email;
	}

	

}
