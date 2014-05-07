/**
 * 
 */
package it.mapyou.persistence;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface DAOSubjectModelInterface<K extends SubjectModel> extends DAOInterface<K> {
	
	public K selectByModelID(int modelID);

}
