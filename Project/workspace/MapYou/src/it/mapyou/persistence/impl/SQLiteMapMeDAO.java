/**
 * 
 */
package it.mapyou.persistence.impl;

import java.util.List;

import it.mapyou.model.MapMe;
import it.mapyou.model.Segment;
import it.mapyou.model.User;
import it.mapyou.persistence.MapMe_DAO;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class SQLiteMapMeDAO implements MapMe_DAO {

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOSubjectModelInterface#selectByModelID(int)
	 */
	@Override
	public MapMe selectByModelID(int modelID) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#insert(java.lang.Object)
	 */
	@Override
	public boolean insert(MapMe t) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(MapMe t) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#update(java.lang.Object)
	 */
	@Override
	public boolean update(MapMe t) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.DAOInterface#selectAll()
	 */
	@Override
	public List<MapMe> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.MapMe_DAO#selectSegmentByUser(it.mapyou.model.User)
	 */
	@Override
	public List<Segment> selectSegmentByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.persistence.MapMe_DAO#selectByUser(it.mapyou.model.User)
	 */
	@Override
	public List<MapMe> selectByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
