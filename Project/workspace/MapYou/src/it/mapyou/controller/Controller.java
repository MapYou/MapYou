<<<<<<< HEAD
/**
 * 
 */
package it.mapyou.controller;

import it.mapyou.model.User;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface Controller {
	
	public boolean login(User user);
	
	public boolean registration(User user);
	
	public boolean forgotPassword(User user);
	
	public boolean disconnet();
	
	public void partecipate();

}
=======
/**
 * 
 */
package it.mapyou.controller;

import it.mapyou.model.User;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface Controller {
	
	public boolean login(User user);
	
	public boolean registration(User user);
	
	public boolean forgotPassword(User user);
	
	public boolean disconnet();
	
	public void partecipate();
	
	public void init();

}
>>>>>>> 7232d1ffd4ea3a6209aeabb3fcdc9e6214100f78
