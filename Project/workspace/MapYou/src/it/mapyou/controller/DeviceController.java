 
package it.mapyou.controller;

import it.mapyou.execption.ServerConnectionNotFoundException;
import it.mapyou.network.NotificationServer;
import it.mapyou.network.Server;
import it.mapyou.parsing.ParsingController;
 

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class DeviceController implements Controller{

 
	private Server server;
	private NotificationServer notificationServer;
	private static DeviceController instance;
	private ParsingController parsingController;
	
	/**
	 * @return the instance
	 */
	public static DeviceController getInstance() {
		if(instance==null)
			instance = new DeviceController();
		return instance;
	}
 
	 

	private DeviceController() {
	}
	
	public Server getServer(){
		return server;
	}
	

	public ParsingController getParsingController() {
		return parsingController;
	}
	public NotificationServer getNotificationServer() {
		return notificationServer;
	}

	
	public ModelCreator getCreator() {
		return ModelCreator.getInstance();
	}

	 

	 
	@Override
	public boolean disconnet(boolean applyCommit) {
		return applyCommit;
	 
	}


	@Override
	public void partecipate() {

	}

	
	@Override
	public void init(Object... parameters) throws Exception {
		
		
		try {
			server = Server.getServer();
			notificationServer= NotificationServer.getNotificationServer();
			server.open(null, null);
			parsingController= ParsingController.getParser();
 
			boolean serverConnected = server.isOpened();
			
			if(!serverConnected)
				throw new ServerConnectionNotFoundException("Server connection not opened.");
 
		} catch (Exception e) {
			 
			throw new Exception(e.getMessage());
		}
		
	}


}
