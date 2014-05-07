/**
 * 
 */
package it.mapyou.persistence;

import it.mapyou.model.MapMe;
import it.mapyou.model.Segment;
import it.mapyou.model.User;

import java.util.List;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface MapMe_DAO extends DAOSubjectModelInterface<MapMe> {

	public List<Segment> selectSegmentByUser(User user);
	public List<MapMe> selectByUser(User user);
}
