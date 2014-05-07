/**
 * 
 */
package it.mapyou.persistence;

import it.mapyou.model.User;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface User_DAO extends DAOSubjectModelInterface<User> {

	public User selectByNickname(String nickname);
	
	public boolean deleteByNickname(User user);
}
