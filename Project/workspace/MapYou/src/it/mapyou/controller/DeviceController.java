/**
 * 
 */
package it.mapyou.controller;

import it.mapyou.model.Point;
import it.mapyou.model.User;
import it.mapyou.persistence.DAOManager;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class DeviceController implements Controller{

	private DAOManager dao;
	
	public DeviceController(DAOManager dao){
		this.dao = dao;
	}
	
	public DAOManager getDao() {
		return dao;
	}
	
	public ModelCreator getCreator() {
		return ModelCreator.getInstance();
	}

	@Override
	public boolean login(User user) {
		
		try {
			return dao.getUserDAO().selectByNickname(user.getNickname()).equals(user);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean registration(User user) {
	
		return false;
	}

	@Override
	public boolean forgotPassword(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disconnet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Point getPosition(GeoController geo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean notifyUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
