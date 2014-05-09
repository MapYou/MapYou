/**
 * 
 */
package it.mapyou.controller;

import it.mapyou.model.SubjectModel;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ModelCreator extends AbstractCreator<SubjectModel> {

	private static ModelCreator instance;
	
	private ModelCreator(){
		
	}
	
	/**
	 * @return the instance
	 */
	public static ModelCreator getInstance() {
		if(instance == null)
			instance = new ModelCreator();
		return instance;
	}
}
