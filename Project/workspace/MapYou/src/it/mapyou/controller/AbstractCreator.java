
/**
 * 
 */
package it.mapyou.controller;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class AbstractCreator<T> implements Creator<T> {
	
	 
	@Override
	public T create(Class<? extends T> clazz) {
		 
		try {
			return clazz.newInstance();
		} catch (Exception e) {
		 
			return null;
		}
	}

 
	@Override
	public T create(String type) {
		 
		try {
			@SuppressWarnings("unchecked")
			Class<? extends T> clazz = (Class<? extends T>) Class.forName(type);
			return create(clazz);
		} catch (ClassNotFoundException e) {
			 
			e.printStackTrace();
			return null;
		}
	}

}