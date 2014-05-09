/**
 * 
 */
package it.mapyou.controller;

import it.mapyou.model.SubjectModel;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface ModelCreator {

	public SubjectModel create(Class<? extends SubjectModel> clazz);
	
	public SubjectModel create(String type);
}
