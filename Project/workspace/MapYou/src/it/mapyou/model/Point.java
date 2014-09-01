/**
 * 
 */
package it.mapyou.model;

import java.io.Serializable;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Point extends SubjectModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5942029095481830419L;
	private double latitude, longitude;
	private String location;

	public String getLocation() {
		return location;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		// TODO Auto-generated method stub
		return latitude;
	}

	public double getLongitude() {
		// TODO Auto-generated method stub
		return longitude;
	}
}
