/**
 * 
 */
package it.mapyou.controller;

import it.mapyou.execption.LocalDBConnectionNotFoundException;
import it.mapyou.execption.ServerConnectionNotFoundException;
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
	private Server server;
	private AndroidGeoController geoController;
	private NotificationServer notificationServer;
 
	 

	public DeviceController() {
		// TODO Auto-generated constructor stub
	}
	
	public DAOManager getDao() {
		return localDao;
	}
	
	public Server getServer(){
		return server;
	}
	
	public ModelCreator getCreator() {
		return ModelCreator.getInstance();
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

			server.close();
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
			
			server = Server.getServer();
			notificationServer= NotificationServer.getNotificationServer();
			server.open(null, null);
			
			boolean localDaoConnected = localDao.connect();
			boolean serverConnected = server.isOpened();
			
			if(!serverConnected)
				throw new ServerConnectionNotFoundException("Server connection not opened.");
			if(!localDaoConnected)
				throw new LocalDBConnectionNotFoundException("Sqlite not connected.");
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
