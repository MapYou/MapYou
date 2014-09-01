///**
// * 
// */
//package it.mapyou.persistence.impl;
//
//import it.mapyou.persistence.DAOManager;
//import it.mapyou.persistence.MapMe_DAO;
//import it.mapyou.persistence.Partecipation_DAO;
//import it.mapyou.persistence.Point_DAO;
//import it.mapyou.persistence.User_DAO;
//
///**
// * @author mapyou (mapyouu@gmail.com)
// *
// */
//public class ServerDAOManager extends DAOManager {
//
//	private static ServerDAOManager instance;
//	private ServerMapMeDAO mapmedao;
//	private ServerPointDAO pointdao;
//	private ServerUserDAO userdao;
//	private ServerPartecipationDAO partecipationdao;
//	private boolean isOpened;
//	
//	private ServerDAOManager(){
//		mapmedao = new ServerMapMeDAO();
//		userdao = new ServerUserDAO();
//		partecipationdao = new ServerPartecipationDAO();
//		pointdao = new ServerPointDAO();
//	}
//	
//	/**
//	 * @return the instance
//	 */
//	public static ServerDAOManager getInstance() {
//		if(instance == null)
//			instance = new ServerDAOManager();
//		return instance;
//	}
//	
//	/* (non-Javadoc)
//	 * @see it.mapyou.persistence.DAOManager#commit()
//	 */
//	@Override
//	public boolean commit() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	/* (non-Javadoc)
//	 * @see it.mapyou.persistence.DAOManager#rollback()
//	 */
//	@Override
//	public boolean rollback() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	/* (non-Javadoc)
//	 * @see it.mapyou.persistence.DAOManager#connect()
//	 */
//	@Override
//	public boolean connect() {
//		// TODO Auto-generated method stub
//		if(isOpened)
//			return false;
//		else{
//			try {
//				isOpened = true;
//				return true;
//			} catch (Exception e) {
//				// TODO: handle exception
//				return false;
//			}
//		}
//	}
//
//	/* (non-Javadoc)
//	 * @see it.mapyou.persistence.DAOManager#close()
//	 */
//	@Override
//	public boolean close() {
//		// TODO Auto-generated method stub
//		if(isOpened){
//			try {
//				isOpened = false;
//				return true;
//			} catch (Exception e) {
//				// TODO: handle exception
//				return false;
//			}
//		}else
//			return false;
//	}
//
//	/* (non-Javadoc)
//	 * @see it.mapyou.persistence.DAOManager#delete()
//	 */
//	@Override
//	public boolean delete() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	/* (non-Javadoc)
//	 * @see it.mapyou.persistence.DAOManager#getUserDAO()
//	 */
//	@Override
//	public User_DAO getUserDAO() {
//		// TODO Auto-generated method stub
//		return userdao;
//	}
//
//	/* (non-Javadoc)
//	 * @see it.mapyou.persistence.DAOManager#getMapMeDAO()
//	 */
//	@Override
//	public MapMe_DAO getMapMeDAO() {
//		// TODO Auto-generated method stub
//		return mapmedao;
//	}
//
//	/* (non-Javadoc)
//	 * @see it.mapyou.persistence.DAOManager#getPartecipationDAO()
//	 */
//	@Override
//	public Partecipation_DAO getPartecipationDAO() {
//		// TODO Auto-generated method stub
//		return partecipationdao;
//	}
//
//	/* (non-Javadoc)
//	 * @see it.mapyou.persistence.DAOManager#getPointDAO()
//	 */
//	@Override
//	public Point_DAO getPointDAO() {
//		// TODO Auto-generated method stub
//		return pointdao;
//	}
//
//	/* (non-Javadoc)
//	 * @see it.mapyou.persistence.DAOManager#updateDB()
//	 */
//	@Override
//	public void updateDB() {
//		// TODO Auto-generated method stub
//
//	}
//
//}
