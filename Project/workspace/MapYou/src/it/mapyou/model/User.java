/**
 * 
 */
package it.mapyou.model;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class User extends SubjectModel{
	
	private String nickname;
	private String firstname;
	private String lastname;
	private String password;
	private String email;
	
	public User() {
		this.nickname = "";
		this.firstname = "";
		this.lastname = "";
		this.password = "";
		this.email = "";
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
