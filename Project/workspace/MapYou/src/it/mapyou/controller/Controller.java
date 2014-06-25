 
package it.mapyou.controller;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface Controller {
	
	
	public boolean disconnet(boolean applyCommit);
	
	public void partecipate();
	
	public void init(Object...parameters) throws Exception;

}
