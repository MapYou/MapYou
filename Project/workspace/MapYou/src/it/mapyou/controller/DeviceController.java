/**
 * 
 */
package it.mapyou.controller;

import it.mapyou.execption.LocalDBConnectionNotFoundException;
import it.mapyou.execption.ServerConnectionNotFoundException;
import it.mapyou.model.User;
import it.mapyou.network.NotificationServer;
import it.mapyou.network.Server;
import it.mapyou.persistence.DAOManager;
import it.mapyou.persistence.impl.SQLiteDAOManager;
import android.content.Context;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class DeviceController implements Controller{

	private DAOManager localDao;
	private Server serverDao;
	private AndroidGeoController geoController;
	private NotificationServer notificationServer;
	private StringSecurityController security;
	 

	public DeviceController() {
		// TODO Auto-generated constructor stub
	}
	
	public DAOManager getDao() {
		return localDao;
	}
	
	public Server getServer(){
		return serverDao;
	}
	

	/**
	 * @return the security
	 */
	public StringSecurityController getSecurity() {
		return security;
	}

	public ModelCreator getCreator() {
		return ModelCreator.getInstance();
	}

	@Override
	public boolean login(User user) {
		return false;

//		try {
//			return localDao.getUserDAO().selectByNickname(user.getNickname()).equals(user)
//	//				|| serverDao.getUserDAO().selectByNickname(user.getNickname()).equals(user);
//		} catch (Exception e) {
//			return false;
//		}
	}

	@Override
	public boolean registration(User user) {
		return false;
//		try {
//			return serverDao.getUserDAO().insert(user);
//		} catch (Exception e) {
//			return false;
//		}
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.Controller#forgotPassword(it.mapyou.model.User)
	 */
	@Override
	public String forgotPassword(User user) {
		return null;
		// TODO Auto-generated method stub
//		try {
//			if(user.getNickname()!=null){
//				User u = localDao.getUserDAO().selectByNickname(user.getNickname());
//				if(u!=null)
//					return null;
//				else{
//					u = serverDao.getUserDAO().selectByNickname(user.getNickname());
//					return (u!=null && u.getEmail()!=null
//							&& user.getEmail()!=null && u.getEmail().equals(user.getEmail()))?
//									u.getPassword():null;
//				}
//			}else
//				return null;
//		} catch (Exception e) {
//			return null;
//		}
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.Controller#disconnet(boolean)
	 */
	@Override
	public boolean disconnet(boolean applyCommit) {
		// TODO Auto-generated method stub
		try {
			if(applyCommit){
				localDao.commit();
			}

			serverDao.close();
			localDao.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}


	@Override
	public void partecipate() {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void init(Object... parameters) throws Exception {
		
		
		try {
			for(int i=0; i<parameters.length; i++){
				Object pi = parameters[i];
				if(pi instanceof Context)
					localDao = SQLiteDAOManager.getInstance((Context) pi);
			}
			
			serverDao = Server.getServer();
			serverDao.open(null, null);
			
			boolean localDaoConnected = localDao.connect();
			boolean serverDaoConnected = serverDao.isOpened();
			
			if(!serverDaoConnected)
				throw new ServerConnectionNotFoundException("server connection not opened.");
			if(!localDaoConnected)
				throw new LocalDBConnectionNotFoundException("sqlite not connected.");
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
		
	}

	/**
	 * @return the geoController
	 */
	public AndroidGeoController getGeoController() {
		return geoController;
	}

	/**
	 * @return the notificationServer
	 */
	public NotificationServer getNotificationServer() {
		return notificationServer;
	}


}
