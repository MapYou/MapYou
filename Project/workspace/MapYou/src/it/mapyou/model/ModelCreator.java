/**
 * 
 */
package it.mapyou.model;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface ModelCreator {

	public SubjectModel create(Class<? extends SubjectModel> clazz);
}
