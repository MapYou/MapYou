/**
 * 
 */
package it.mapyou.persistence;

import java.util.List;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface DAOInterface<T> {

	public boolean insert(T t);
	
	public boolean delete(T t);
	
	public boolean update(T t);
	
	public List<T> selectAll();
	
}
