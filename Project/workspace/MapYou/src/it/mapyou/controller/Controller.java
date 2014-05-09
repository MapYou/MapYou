/**
 * 
 */
package it.mapyou.controller;

import it.mapyou.model.Point;
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
	
	public boolean deleteUser(User user);
	
	public Point getPosition(GeoController geo);
	
	public boolean notifyUser(User user);

}
