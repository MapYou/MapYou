<<<<<<< HEAD
/**
 * 
 */
package it.mapyou.controller;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class AbstractCreator<T> implements Creator<T> {
	
	/* (non-Javadoc)
	 * @see it.mapyou.controller.Creator#create(java.lang.Class)
	 */
	@Override
	public T create(Class<? extends T> clazz) {
		// TODO Auto-generated method stub
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.Creator#create(java.lang.String)
	 */
	@Override
	public T create(String type) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			Class<? extends T> clazz = (Class<? extends T>) Class.forName(type);
			return create(clazz);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
=======
/**
 * 
 */
package it.mapyou.controller;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class AbstractCreator<T> implements Creator<T> {
	
	/* (non-Javadoc)
	 * @see it.mapyou.controller.Creator#create(java.lang.Class)
	 */
	@Override
	public T create(Class<? extends T> clazz) {
		// TODO Auto-generated method stub
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.Creator#create(java.lang.String)
	 */
	@Override
	public T create(String type) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			Class<? extends T> clazz = (Class<? extends T>) Class.forName(type);
			return create(clazz);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
>>>>>>> d33f0a58dc6e5fbd532102d91e80e59a1b94405c
