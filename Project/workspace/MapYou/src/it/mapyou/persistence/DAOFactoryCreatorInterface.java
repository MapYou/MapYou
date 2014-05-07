/**
 * 
 */
package it.mapyou.persistence;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface DAOFactoryCreatorInterface {

	public DAOManager createDAOManager(String type);
}
