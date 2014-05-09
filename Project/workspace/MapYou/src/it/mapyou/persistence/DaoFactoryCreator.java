/**
 * 
 */
package it.mapyou.persistence;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class DaoFactoryCreator implements DAOFactoryCreatorInterface{

	private static DaoFactoryCreator factory=null; 
	 
	public DaoFactoryCreator() {
		if(factory==null)
			factory=new DaoFactoryCreator();
		 
	}
	
	 
	@Override
	public DAOManager createDAOManager(String type) {

		if(factory!=null){
		
			if(type.equalsIgnoreCase("SqlLyte"))
				return new SqlLyteDaoManager();
		 
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		
		DaoFactoryCreator d= new DaoFactoryCreator();
		Sqlyte sql= d.createDAOManager(SqlLyte);
		
	}

}


 







 
