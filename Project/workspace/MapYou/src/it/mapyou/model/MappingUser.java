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
public class MappingUser implements Parcelable{

	private User user;
	private MapMe mapme;
	private Point point;
	private GregorianCalendar detectionDate;
	
	public static final Parcelable.Creator<MappingUser> CREATOR = new Creator<MappingUser>() {
		
		@Override
		public MappingUser[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public MappingUser createFromParcel(Parcel source) {
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
	 * @return the segment
	 */
	public Point getPoint() {
		return point;
	}
	/**
	 * @param segment the segment to set
	 */
	public void setPoint(Point point) {
		this.point = point;
	}
	/**
	 * @return the detectionDate
	 */
	public GregorianCalendar getDetectionDate() {
		return detectionDate;
	}
	/**
	 * @param detectionDate the detectionDate to set
	 */
	public void setDetectionDate(GregorianCalendar detectionDate) {
		this.detectionDate = detectionDate;
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
