/**
 * 
 */
package it.mapyou.model;

import java.util.GregorianCalendar;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Mapping implements Parcelable{

	private User user;
	private double latitude, longitude;
	private GregorianCalendar date;
	public static final Parcelable.Creator<Mapping> CREATOR = new Creator<Mapping>() {
		
		@Override
		public Mapping[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Mapping[size];
		}
		
		@Override
		public Mapping createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Mapping m = new Mapping();
			m.setUser((User) source.readSerializable());
			m.setLatitude(source.readDouble());
			m.setLongitude(source.readDouble());
			GregorianCalendar g = new GregorianCalendar();
			g.setTimeInMillis(source.readLong());
			m.setDate(g);
			return m;
		}
	};
	
	/**
	 * 
	 */
	public Mapping() {
		date = new GregorianCalendar();
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * @return the date
	 */
	public GregorianCalendar getDate() {
		return date;
	}
	
	/**
	 * @param date the date to set
	 */
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeSerializable(user);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeLong(date.getTimeInMillis());
	}

}
