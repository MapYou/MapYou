/**
 * 
 */
package it.mapyou.persistence;

import java.util.List;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface MapMe_DAO<MapMe> extends DAOSubjectModelInterface<MapMe> {

	public List<Segment> selectSegmentByUser(User user);
	
	public List<MapMe> selectByUser(User user);
}
