/**
 * 
 */
package it.mapyou.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Notification extends AbstractNotification<User, User, MapMe>{

	public static final Parcelable.Creator<Notification> CREATOR = new Creator<Notification>() {
		
		@Override
		public Notification[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Notification createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
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
