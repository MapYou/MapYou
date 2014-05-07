/**
 * 
 */
package it.mapyou.persistence;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface Source {

	public boolean commit();
	
	public boolean rollback();
	
	public boolean openConnection();
	
	public boolean closeConnection();
}
