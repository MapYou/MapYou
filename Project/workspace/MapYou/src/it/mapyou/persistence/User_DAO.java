/**
 * 
 */
package it.mapyou.persistence;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface User_DAO<User> extends DAOSubjectModelInterface<User> {

	public User selectByNickname(String nickname);
	
	public boolean deleteByNickname(User user);
}
