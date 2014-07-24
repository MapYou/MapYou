/**
 * 
 */
package it.mapyou.core;

import java.util.GregorianCalendar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class RegistrationMapMe implements Parcelable{

	private User user;
	private MapMe mapme;
	private GregorianCalendar registrationDate;
	
	public static final Parcelable.Creator<RegistrationMapMe> CREATOR = new Creator<RegistrationMapMe>() {
		
		@Override
		public RegistrationMapMe[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public RegistrationMapMe createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the mapme
	 */
	public MapMe getMapme() {
		return mapme;
	}
	/**
	 * @param mapme the mapme to set
	 */
	public void setMapme(MapMe mapme) {
		this.mapme = mapme;
	}
	/**
	 * @return the registrationDate
	 */
	public GregorianCalendar getRegistrationDate() {
		return registrationDate;
	}
	/**
	 * @param registrationDate the registrationDate to set
	 */
	public void setRegistrationDate(GregorianCalendar registrationDate) {
		this.registrationDate = registrationDate;
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
		
	}
	
	
}
