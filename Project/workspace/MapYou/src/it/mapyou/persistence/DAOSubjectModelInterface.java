/**
 * 
 */
package it.mapyou.persistence;

import it.mapyou.model.SubjectModel;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface DAOSubjectModelInterface<K extends SubjectModel> extends DAOInterface<K> {
	
	public K selectByModelID(int modelID);

}
