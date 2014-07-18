/**
 * 
 */
package it.mapyou.model;

import java.io.Serializable;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class AbstractPoint extends SubjectModel implements Point, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5942029095481830419L;
	protected double latitude, longitude;
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
 
	@Override
	public double getLatitude() {
		// TODO Auto-generated method stub
		return latitude;
	}
	 
	@Override
	public double getLongitude() {
		// TODO Auto-generated method stub
		return longitude;
	}
}
