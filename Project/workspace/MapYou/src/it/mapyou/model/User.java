/**
 * 
 */
package it.mapyou.model;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class User{
	
	private String nickname;
	private String firstname;
	private String lastname;
	private String password;
	private String email;
	

	/**
	 * @param nickname
	 * @param firstname
	 * @param lastname
	 * @param password
	 * @param email
	 */
	public User(String nickname, String firstname, String lastname,
			String password, String email) {
		super();
		this.nickname = nickname;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.email = email;
	}
	
	public String getNickname() {
		return nickname;
	}

	 
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	 
	public String getFirstname() {
		return firstname;
	}

	 
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	 
	public String getLastname() {
		return lastname;
	}

	 
	public void setLastname(String lastname) {
		this.lastname = lastname;
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
