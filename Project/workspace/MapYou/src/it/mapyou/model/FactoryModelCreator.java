/**
 * 
 */
package it.mapyou.model;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class FactoryModelCreator implements ModelCreator {

	/* (non-Javadoc)
	 * @see it.mapyou.model.ModelCreator#create(java.lang.Class)
	 */
	@Override
	public SubjectModel create(Class<? extends SubjectModel> clazz) {
		// TODO Auto-generated method stub
		try {
			return clazz.newInstance().getClass().cast(clazz);
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

	public static void main(String[] args) {
		System.out.println(new FactoryModelCreator().create(User.class).getClass().getSimpleName());
	}

}
