/**
 * 
 */
package it.mapyou.persistence.impl;

import java.util.List;

import it.mapyou.model.User;
import it.mapyou.persistence.User_DAO;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class SQLiteUserDAO implements User_DAO {

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOSubjectModelInterface#selectByModelID(int)
	 */
	@Override
	public User selectByModelID(int modelID) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#insert(java.lang.Object)
	 */
	@Override
	public boolean insert(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#update(java.lang.Object)
	 */
	@Override
	public boolean update(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#selectAll()
	 */
	@Override
	public List<User> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.User_DAO#selectByNickname(java.lang.String)
	 */
	@Override
	public User selectByNickname(String nickname) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.User_DAO#deleteByNickname(it.mapyou.model.User)
	 */
	@Override
	public boolean deleteByNickname(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
