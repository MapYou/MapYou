 
package it.mapyou.controller;

import it.mapyou.model.User;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface Controller {
	
	public boolean login(User user);
	
	public boolean registration(User user);
	
	public String forgotPassword(User user);
	
	public boolean disconnet(boolean applyCommit);
	
	public void partecipate();
	
	public void init(Object...parameters) throws Exception;

}
