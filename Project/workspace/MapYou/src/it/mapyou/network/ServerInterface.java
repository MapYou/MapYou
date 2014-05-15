/**
 * 
 */
package it.mapyou.network;



/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface ServerInterface<T extends Resource> {


	public boolean close();
	
	public boolean open();
	
	public Response<T> request(Request<T> req);
}
