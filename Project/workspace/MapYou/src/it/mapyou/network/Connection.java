/**
 * 
 */
package it.mapyou.network;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface Connection {

	public boolean close();
	
	public boolean open();
	
	public Response<? extends Resource> request(Request<? extends Resource> req);
}
