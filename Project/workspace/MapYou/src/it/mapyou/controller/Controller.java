 
package it.mapyou.controller;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface Controller {
	
	public void init(Object...parameters) throws Exception;

	public boolean isInitialized() throws Exception;
}
