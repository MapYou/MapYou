/**
 * 
 */
package it.mapyou.persistence.impl;

import java.util.List;

import it.mapyou.model.AbstractPoint;
import it.mapyou.persistence.Point_DAO;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ServerPointDAO implements Point_DAO {

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOSubjectModelInterface#selectByModelID(int)
	 */
	@Override
	public AbstractPoint selectByModelID(int modelID) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#insert(java.lang.Object)
	 */
	@Override
	public boolean insert(AbstractPoint t) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(AbstractPoint t) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#update(java.lang.Object)
	 */
	@Override
	public boolean update(AbstractPoint t) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#selectAll()
	 */
	@Override
	public List<AbstractPoint> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
