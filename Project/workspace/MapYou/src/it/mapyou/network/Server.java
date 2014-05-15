/**
 * 
 */
package it.mapyou.network;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Server implements ServerInterface<JSON_Resource> {

	private static Server server;
	
	private Server(){
		
	}
	
	/**
	 * @return the server
	 */
	public static Server getServer() {
		if(server==null)
			server = new Server();
		return server;
	}
	
	/* (non-Javadoc)
	 * @see it.mapyou.network.ServerInterface#close()
	 */
	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.network.ServerInterface#open()
	 */
	@Override
	public boolean open() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.network.ServerInterface#request(it.mapyou.network.Request)
	 */
	@Override
	public Response<JSON_Resource> request(Request<JSON_Resource> req) {
		// TODO Auto-generated method stub
		return null;
	}


}
