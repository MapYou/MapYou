 
package it.mapyou.controller;

import it.mapyou.controller.network.NotificationServer;
import it.mapyou.controller.network.Server;
 

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
	public void init(Object... parameters) throws Exception {
		
		
		try {
			server = Server.getServer();
			notificationServer= NotificationServer.getNotificationServer();
			server.open(null, null);
			ParsingController.getParser().init(parameters);
			parsingController= ParsingController.getParser();
 
			boolean serverConnected = server.isOpened();
			
			if(!serverConnected)
				throw new Exception("Server connection not opened.");
 
		} catch (Exception e) {
			 
			throw new Exception(e.getMessage());
		}
		
	}



	/* (non-Javadoc)
	 * @see it.mapyou.controller.Controller#isInitialized()
	 */
	@Override
	public boolean isInitialized() throws Exception {
		// TODO Auto-generated method stub
		return server!=null && server.isOpened() && parsingController.isInitialized();
	}


}
