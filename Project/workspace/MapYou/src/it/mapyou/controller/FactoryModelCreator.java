/**
 * 
 */
package it.mapyou.controller;

import it.mapyou.model.SubjectModel;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class FactoryModelCreator implements ModelCreator {

	private static FactoryModelCreator instance;
	
	private FactoryModelCreator(){
	}
	
	public static FactoryModelCreator getInstance() {
		if(instance == null)
			instance = new FactoryModelCreator();
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see it.mapyou.model.ModelCreator#create(java.lang.Class)
	 */
	@Override
	public SubjectModel create(Class<? extends SubjectModel> clazz) {
		// TODO Auto-generated method stub
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SubjectModel create(String type) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			Class<? extends SubjectModel> clazz = (Class<? extends SubjectModel>) Class.forName("it.mapyou.model."+type);
			return create(clazz);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
